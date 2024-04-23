package com.project.myBlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GithubProfileDto {
    private String email;
    private String name;

    @JsonProperty("avatar_url")
    private String imgUrl;
    private String blog;
}
