package com.project.myBlog.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
     ADMIN("ROLE_ADMIN","관리자"), USER("ROLE_USER","사용자");

     private final String key;
     private final String title;

}
