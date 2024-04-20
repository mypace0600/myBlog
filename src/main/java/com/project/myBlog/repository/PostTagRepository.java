package com.project.myBlog.repository;

import com.project.myBlog.dto.PostDto;
import com.project.myBlog.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostTagRepository extends JpaRepository<PostTag, Integer> {
    List<PostTag> findByPostId(Integer id);
    Optional<PostTag> findByPostIdAndTagId(Integer id, Integer id1);
    List<PostTag> findAllByTagId(Integer id);
    // 방법1.DTO 만들어서 하기
    // 방법2.쿼리DSL 직접 만들어서 적용하기
}
