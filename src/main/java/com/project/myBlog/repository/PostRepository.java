package com.project.myBlog.repository;

import com.project.myBlog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Integer>{
}
