package com.project.myBlog.repository;

import com.project.myBlog.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostTagRepository extends JpaRepository<PostTag, Integer> {
    List<PostTag> findByPostId(Integer id);
}
