package com.project.myBlog.repository;

import com.project.myBlog.entity.PostTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostTagRepositoryCustom {
    Page<PostTag> postTagsByTagId(int id, Pageable pageable);
}
