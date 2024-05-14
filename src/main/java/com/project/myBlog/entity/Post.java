package com.project.myBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tb_post")
public class Post extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition="TEXT")
    private String content;

    @Column(columnDefinition="TEXT")
    private String textOnlyContent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "hidden", nullable = false)
    private boolean hidden;

    private int count;

    private String uuid;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id desc ")
    private List<Image> imageList;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id desc ")
    private List<Comment> commentList;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy="post",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PostTag> postTagList = new ArrayList<>();

}
