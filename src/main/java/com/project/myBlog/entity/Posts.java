package com.project.myBlog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content",columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(name = "userId", nullable = false)
    private Integer userId;
    @Column(name = "status", nullable = false)
    private boolean status;
    @ElementCollection()
    private List<String> tags;
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

}
