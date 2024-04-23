package com.project.myBlog.config.oauth;

import com.project.myBlog.entity.RoleType;
import com.project.myBlog.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String email){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        return ofGithub(userNameAttributeName,attributes);
    }

    public static OAuthAttributes ofGithub(String userNameAttributeName, Map<String, Object> attributes){
        String tempEmail = attributes.get("name")+"@tempgithub.com";
        return OAuthAttributes.builder()
                .email(null == (String) attributes.get("email") ? tempEmail :  (String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(String registrationId, @Value("${myBlog.password}") String githubPassword){
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(githubPassword));
        user.setRoleType(RoleType.OAUTH);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());
        user.setOauth(registrationId);
        return user;
    }
}
