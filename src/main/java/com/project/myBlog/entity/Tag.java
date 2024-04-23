package com.project.myBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "tb_tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tagName")
    private String tagName;

    @JsonIgnoreProperties({"tag"})
    @OneToMany(mappedBy="tag",fetch = FetchType.LAZY)
    private List<PostTag> postTagList = new ArrayList<>();

}
