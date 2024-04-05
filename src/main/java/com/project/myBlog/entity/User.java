package com.project.myBlog.entity;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @OneToOne(mappedBy = "user")
    private Comment comment;

    public static User createUser(UserRegisterDto userRegisterDto, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .roleType(RoleType.ADMIN)
                .createdAt(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }
}

