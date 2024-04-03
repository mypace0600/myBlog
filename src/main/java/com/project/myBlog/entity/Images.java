package com.project.myBlog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "postid", nullable = false)
    private Integer postId;
    @Column(name = "imgurl", nullable = false)
    private String imgUrl;


}
