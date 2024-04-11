package com.project.myBlog.dto;

import lombok.Data;

@Data
public class PostWriteDto {

    private String title;

    private String content;

    private String tagString;

    private boolean hidden;

}
