package com.project.myBlog.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "tb_post_tag")
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="tagId")
    private Tag tag;
}
