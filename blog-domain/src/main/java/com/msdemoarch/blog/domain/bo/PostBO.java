package com.msdemoarch.blog.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostBO {

    private String id;
    private String title;
    private String content;
}
