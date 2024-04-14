package com.project.myBlog.repository;

import com.project.myBlog.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostTagRepository extends JpaRepository<PostTag, Integer> {
    List<PostTag> findByPostId(Integer id);

    Optional<PostTag> findByPostIdAndTagId(Integer id, Integer id1);

    void deleteAllByPostId(Integer id);
}
