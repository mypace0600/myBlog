package com.project.myBlog.service;

import com.project.myBlog.entity.RoleType;
import com.project.myBlog.entity.User;
import com.project.myBlog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    public boolean checkEmailDuplicated(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public void register(User user) throws BadRequestException {
        if(!checkEmailDuplicated(user.getEmail())){
            userRepository.save(user);
        } else {
            throw new BadRequestException("이미 사용중인 이메일 입니다.");
        }
    }

    @Transactional
    public User loginByGithubUserSave(String githubEmail, String githubPassword) throws BadRequestException {
        Optional<User> checkEmailUser = userRepository.findByEmail(githubEmail);
        log.debug("@@@@@@@@@@@@@@ checkEmail : {}",githubEmail);
        if(!checkEmailUser.isPresent()){
            User githubUser = new User();
            githubUser.setEmail(githubEmail);
            githubUser.setPassword(encoder.encode(githubPassword));
            githubUser.setRoleType(RoleType.USER);
            githubUser.setOauth("github");
            githubUser.setCreatedAt(LocalDateTime.now());
            githubUser.setUpdateAt(LocalDateTime.now());
            userRepository.save(githubUser);
            return githubUser;
        } else {
            return checkEmailUser.get();
        }
    }

    public Optional<User> findOauthUser(DefaultOAuth2User oauthPrincipal) {

        String email = (String) oauthPrincipal.getAttributes().get("email");
        if(null == email){
            email = oauthPrincipal.getAttributes().get("name")+"@tempgithub.com";
        }
        return userRepository.findByEmail(email);
    }
}
