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

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "temp_id") // 임시 식별자
    private String tempId;

    public static Image createImg(String fileRoot, String savedFileName,String uuid) {
        Image image = new Image();
        image.setFilePath(fileRoot);
        image.setFileName(savedFileName);
        image.setTempId(uuid);
        return image;
    }
}
