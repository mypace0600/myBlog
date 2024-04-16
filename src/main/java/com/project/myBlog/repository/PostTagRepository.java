package com.project.myBlog.repository;

import com.project.myBlog.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;


public interface PostTagRepository extends JpaRepository<PostTag, Integer>, QuerydslPredicateExecutor<PostTag>, PostTagRepositoryCustom {
    List<PostTag> findByPostId(Integer id);

    Optional<PostTag> findByPostIdAndTagId(Integer id, Integer id1);

    void deleteAllByPostId(Integer id);

}
