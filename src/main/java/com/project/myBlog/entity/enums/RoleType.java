package com.project.myBlog.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
     ADMIN("ADMIN","관리자"), USER("USER","사용자"), OAUTH("OAUTH","OAUTH사용자");

     private final String key;
     private final String title;

}
