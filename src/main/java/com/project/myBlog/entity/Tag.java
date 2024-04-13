package com.project.myBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "tb_tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tagName")
    private String tagName;

    @JsonIgnoreProperties({"tag"})
    @OneToMany(mappedBy="tag",fetch = FetchType.EAGER)
    private List<PostTag> postTagList = new ArrayList<>();

}
