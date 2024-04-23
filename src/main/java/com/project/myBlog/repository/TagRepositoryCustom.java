package com.project.myBlog.repository;

import com.project.myBlog.entity.Tag;

import java.util.List;

public interface TagRepositoryCustom {
    List<Tag> findAllOrderByPostCountTest();
}
