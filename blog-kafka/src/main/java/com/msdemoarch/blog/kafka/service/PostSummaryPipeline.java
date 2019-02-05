package com.msdemoarch.blog.kafka.service;

import com.msdemoarch.blog.domain.port.topology.ExportTopology;
import com.msdemoarch.blog.kafka.dto.PostDTO;
import com.msdemoarch.blog.kafka.dto.PostSummaryDTO;
import com.msdemoarch.blog.kafka.serde.PostSummarySerde;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

@Component
public class PostSummaryPipeline {

  private ExportTopology topology;
  private static final String bootstrapServers = "localhost:9092";
  private static final String source = "post_id_src";
  private static final String sink = "post_summary_sink";
  private KafkaSender sender;

  @Autowired
  public PostSummaryPipeline(ExportTopology topology) {
    this.topology = topology;
    this.sender = KafkaSender.create(senderOptions());
  }

  public void start() {
    System.err.println("Streaming...");
    flux().blockLast();
    if (this.sender != null) {
      this.sender.close();
    }
  }

  public Flux<?> flux() {
    return KafkaReceiver
        .create(receiverOptions().subscription(Collections.singleton(source)))
        .receive()
        .publishOn(Schedulers.parallel())
        .flatMap(record -> topology.getOriginalPost(record.value()))
        .map(postBO -> PostDTO.boToDtoMapper(postBO))
        .map(post -> {
          String summary = topology.summarize(post.getContent());
          return new PostSummaryDTO(post.getId(), post.getTitle(), summary);
        })
        .map(summary -> SenderRecord
            .create(new ProducerRecord<>(sink, summary.getId(), summary), summary.getId())
        )
        .as(sender::send)
        .doOnCancel(() -> {
          if (this.sender != null) {
            this.sender.close();
          }
        });
  }

  private SenderOptions<String, PostSummaryDTO> senderOptions() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "blog-app");
    props.put(ProducerConfig.ACKS_CONFIG, "all");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PostSummarySerde.class);
    return SenderOptions.create(props);
  }

  private ReceiverOptions<String, String> receiverOptions() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "blog-app-grp");
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "blog-app");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return ReceiverOptions.create(props);
  }
}
