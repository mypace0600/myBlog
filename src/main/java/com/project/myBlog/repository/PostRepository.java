package com.project.myBlog.repository;

import com.project.myBlog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer>{
    @Query("SELECT p FROM Post p WHERE p.portFolio = true")
    List<Post> findByPortFolio();
}
