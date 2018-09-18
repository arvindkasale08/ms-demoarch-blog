package com.msdemoarch.blog.persistence.repository;

import com.msdemoarch.blog.domain.bo.PostBO;
import com.msdemoarch.blog.domain.exception.PostNotFoundException;
import com.msdemoarch.blog.domain.port.repository.PostRepository;
import com.msdemoarch.blog.persistence.entity.Post;
import com.msdemoarch.blog.persistence.mapper.EntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class PostRepositoryImpl implements PostRepository {
    
    private PostDataRepository dataRepository;
    private EntityMapper mapper;
    
    @Autowired
    public PostRepositoryImpl(PostDataRepository dataRepository, EntityMapper mapper) {
        this.dataRepository = dataRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<PostBO> addPost(PostBO post) {
        Post entity = this.mapper.mapBoToEntity(post);
        return this.dataRepository.save(entity)
                .map(this.mapper::mapEntityToBo);
    }

    @Override
    public Mono<PostBO> findPostById(String id) {
        return this.dataRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable()))
                .doOnError(e -> { throw new PostNotFoundException("Not found"); })
                .map(this.mapper::mapEntityToBo);
    }

    @Override
    public Mono<Void> deletePostById(String id) {
        return this.dataRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable()))
                .doOnError(e -> { throw new PostNotFoundException("Not found"); })
                .flatMap(post -> this.dataRepository.delete(post));
    }

    @Override
    public Flux<PostBO> findAllPosts() {
        return this.dataRepository.findAll()
                .map(this.mapper::mapEntityToBo);
    }
}
