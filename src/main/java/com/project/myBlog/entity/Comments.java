package com.project.myBlog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",nullable = false)
    private Integer id;
    @Column(name = "postid",nullable = false)
    private Integer postId;
    @Column(name = "userid",nullable = false)
    private Integer userId;
    @Column(name = "content",nullable = false)
    private Integer content;


}
