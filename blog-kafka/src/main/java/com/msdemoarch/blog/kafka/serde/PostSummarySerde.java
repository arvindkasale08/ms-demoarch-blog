package com.msdemoarch.blog.kafka.serde;

import com.msdemoarch.blog.kafka.dto.PostSummaryDTO;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

public class PostSummarySerde implements Serializer<PostSummaryDTO>, Deserializer<PostSummaryDTO> {

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
  }

  @Override
  public byte[] serialize(String topic, PostSummaryDTO post) {
    byte[] id = post.getId().getBytes(StandardCharsets.UTF_8);
    byte[] title = post.getTitle().getBytes(StandardCharsets.UTF_8);
    byte[] summary = post.getSummary().getBytes(StandardCharsets.UTF_8);

    ByteBuffer buffer = ByteBuffer.allocate(id.length + 4 + title.length + 4 + summary.length);
    buffer.putInt(id.length);
    buffer.put(id);
    buffer.putInt(title.length);
    buffer.put(title);
    buffer.putInt(summary.length);
    buffer.put(summary);
    return buffer.array();
  }

  @Override
  public PostSummaryDTO deserialize(String topic, byte[] data) {
    ByteBuffer buffer = ByteBuffer.wrap(data);

    byte[] idArr = new byte[buffer.getInt()];
    buffer.get(idArr);
    String id = new String(idArr, StandardCharsets.UTF_8);

    byte[] titleArr = new byte[buffer.getInt()];
    buffer.get(titleArr);
    String title = new String(titleArr, StandardCharsets.UTF_8);

    byte[] summaryArr = new byte[buffer.getInt()];
    buffer.get(summaryArr);
    String summary = new String(summaryArr, StandardCharsets.UTF_8);

    return new PostSummaryDTO(id, title, summary);
  }

  @Override
  public void close() {
  }
}
