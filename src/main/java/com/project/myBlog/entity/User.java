package com.project.myBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.myBlog.dto.UserRegisterDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Post> postList;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    public static User createUser(UserRegisterDto userRegisterDto, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .roleType(RoleType.ADMIN)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
}

