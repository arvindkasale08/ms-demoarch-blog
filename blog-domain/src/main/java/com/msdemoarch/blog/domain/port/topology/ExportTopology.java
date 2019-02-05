package com.msdemoarch.blog.domain.port.topology;

import com.msdemoarch.blog.domain.bo.PostBO;
import reactor.core.publisher.Mono;

public interface ExportTopology {

  Mono<PostBO> getOriginalPost(String postId);

  String summarize(String content);
}
