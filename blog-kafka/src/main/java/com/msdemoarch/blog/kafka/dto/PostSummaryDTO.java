package com.msdemoarch.blog.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostSummaryDTO {

  private String id;
  private String title;
  private String summary;
}
