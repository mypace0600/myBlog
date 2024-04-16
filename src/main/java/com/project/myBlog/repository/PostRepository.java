package com.project.myBlog.repository;

import com.project.myBlog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface PostRepository extends JpaRepository<Post, Integer>{
}
