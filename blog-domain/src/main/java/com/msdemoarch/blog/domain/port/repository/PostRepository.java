package com.msdemoarch.blog.domain.port.repository;

import com.msdemoarch.blog.domain.bo.PostBO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository {
    
    Mono<PostBO> addPost(PostBO post);
    
    Mono<PostBO> findPostById(String id);
    
    Mono<Void> deletePostById(String id);
    
    Flux<PostBO> findAllPosts();
}
