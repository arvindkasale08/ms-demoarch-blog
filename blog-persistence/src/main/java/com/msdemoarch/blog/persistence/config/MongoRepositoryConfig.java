package com.msdemoarch.blog.persistence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@Configuration
@EnableReactiveMongoRepositories("com.msdemoarch.blog.persistence.repository")
public class MongoRepositoryConfig { }
