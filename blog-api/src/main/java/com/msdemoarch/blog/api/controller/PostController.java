package com.msdemoarch.blog.api.controller;

import com.msdemoarch.blog.api.dto.PostDTO;
import com.msdemoarch.blog.domain.port.service.PostService;
import com.msdemoarch.blog.api.mapper.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    
    private PostService postService;
    private DTOMapper mapper;
    
    @Autowired
    public PostController(PostService postService, DTOMapper mapper) {
        this.postService = postService;
        this.mapper = mapper;
    }

    @GetMapping
    public Flux<PostDTO> all() {
        return this.postService.findAllPosts().map(this.mapper.boToDtoMapper);
    }

    @PostMapping
    public Mono<PostDTO> create(@RequestBody PostDTO post) {
        return this.postService
                .addPost(this.mapper.dtoToBoMapper.apply(post))
                .map(this.mapper.boToDtoMapper);
    }

    @GetMapping("/{id}")
    public Mono<PostDTO> get(@PathVariable("id") String id) {
        return this.postService.findPostById(id).map(this.mapper.boToDtoMapper);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return this.postService.deletePostById(id);
    }
}
