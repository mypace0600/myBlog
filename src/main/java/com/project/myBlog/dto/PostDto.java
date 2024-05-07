package com.project.myBlog.dto;

import com.project.myBlog.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private boolean hidden;
    private String tagString;
    private String uuid;

    private List<Comment> commentList;
}
