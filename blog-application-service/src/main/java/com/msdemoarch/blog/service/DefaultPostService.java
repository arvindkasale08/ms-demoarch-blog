package com.msdemoarch.blog.service;

import com.msdemoarch.blog.domain.bo.PostBO;
import com.msdemoarch.blog.domain.port.service.PostService;
import com.msdemoarch.blog.domain.port.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class DefaultPostService implements PostService {
    
    private PostRepository postRepository;
    
    @Autowired
    public DefaultPostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Mono<PostBO> addPost(PostBO post) {
        return this.postRepository.addPost(post);
    }

    @Override
    public Mono<PostBO> findPostById(String id) {
        return this.postRepository.findPostById(id);
    }

    @Override
    public Mono<Void> deletePostById(String id) {
        return this.postRepository.deletePostById(id);
    }

    @Override
    public Flux<PostBO> findAllPosts() {
        return this.postRepository.findAllPosts();
    }
}
