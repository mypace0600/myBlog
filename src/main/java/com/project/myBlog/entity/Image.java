package com.project.myBlog.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "tb_image")
public class Image extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @Column(name = "file_name")
    private String fileName;

    @Column
    private String saveFileName;

    @Column(name = "file_path")
    private String filePath;

    @Column
    private String contentType;

    private long size;


}
