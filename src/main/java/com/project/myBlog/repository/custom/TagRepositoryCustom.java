package com.project.myBlog.repository.custom;

import com.project.myBlog.entity.Tag;

import java.util.List;

public interface TagRepositoryCustom {
    List<Tag> findAllOrderByPostCountTest();
}
