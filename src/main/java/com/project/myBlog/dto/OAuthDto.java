package com.project.myBlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OAuthDto {

    @JsonProperty("access_token")
    private String accessToken;
}
