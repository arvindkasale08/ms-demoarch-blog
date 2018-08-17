package com.msdemoarch.blog.api.mapper;

import com.msdemoarch.blog.api.dto.PostDTO;
import com.msdemoarch.blog.domain.bo.PostBO;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class DTOMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    public Function<PostBO, PostDTO> boToDtoMapper = (bo) ->
            mapperFactory.getMapperFacade(PostBO.class, PostDTO.class).map(bo);

    public Function<PostDTO, PostBO> dtoToBoMapper = (entity) ->
            mapperFactory.getMapperFacade(PostDTO.class, PostBO.class).map(entity);
}
