package com.msdemoarch.blog.persistence.repository;

import com.msdemoarch.blog.persistence.entity.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
interface PostDataRepository extends ReactiveMongoRepository<Post, String> {
}
