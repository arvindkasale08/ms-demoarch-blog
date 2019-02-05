package com.msdemoarch.blog.kafka.dto;

import com.msdemoarch.blog.domain.bo.PostBO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostDTO {

  private String id;
  private String title;
  private String content;
  
  public static PostDTO boToDtoMapper(PostBO bo) {
    return new PostDTO(bo.getId(), bo.getTitle(), bo.getContent());
  }
}
