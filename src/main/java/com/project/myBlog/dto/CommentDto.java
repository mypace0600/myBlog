package com.project.myBlog.dto;

import lombok.Data;

@Data
public class CommentDto {

    private int postId;
    private Integer commentId;
    private String commentContent;
}
