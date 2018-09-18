package com.msdemoarch.blog.persistence.mapper;

import com.msdemoarch.blog.domain.bo.PostBO;
import com.msdemoarch.blog.persistence.entity.Post;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class EntityMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    public Post mapBoToEntity(PostBO bo) {
        return mapperFactory.getMapperFacade(PostBO.class, Post.class).map(bo);
    }

    public PostBO mapEntityToBo(Post entity) {
        return mapperFactory.getMapperFacade(Post.class, PostBO.class).map(entity);
    }
}
