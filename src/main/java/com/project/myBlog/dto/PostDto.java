package com.project.myBlog.dto;

import lombok.Data;

@Data
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private boolean hidden;
    private String tagString;
}
