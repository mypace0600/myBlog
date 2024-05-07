package com.project.myBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.myBlog.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    private String roleType;

    private String oauth;


    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Post> postList;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    public static User createUser(User user, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roleType(RoleType.ADMIN.getKey())
                .build();
    }

    @Builder
    public User(String email, RoleType roleType){
        this.email = email;
        this.roleType = roleType.getKey();
    }

    public User update(String oauthProvideCompany){
        this.oauth = oauthProvideCompany;
        this.roleType = RoleType.OAUTH.getKey();
        return this;
    }

    public String getRoleType(){
        return this.roleType;
    }
}

