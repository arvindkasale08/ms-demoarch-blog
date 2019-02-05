package com.msdemoarch.blog.topology;

import com.msdemoarch.blog.domain.bo.PostBO;
import com.msdemoarch.blog.domain.port.service.PostService;
import com.msdemoarch.blog.domain.port.topology.ExportTopology;
import java.nio.charset.Charset;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class PostExportTopology implements ExportTopology {

  @Autowired
  private PostService postService;
  
  @Override
  public Mono<PostBO> getOriginalPost(String postId) {
    return this.postService.findPostById(postId);
  }

  @Override
  public String summarize(String content) {
    return generateRandomSummary(content);
  }
  
  private String generateRandomSummary(String content) {
    byte[] array = new byte[192];
    ThreadLocalRandom.current().nextBytes(array);
    return new String(array, Charset.forName("UTF-8"));
  }
}
