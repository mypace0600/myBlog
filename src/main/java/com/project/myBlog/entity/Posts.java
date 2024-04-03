package com.project.myBlog.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.Mapping;

import java.time.LocalDateTime;

@Entity
@Data
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

}
