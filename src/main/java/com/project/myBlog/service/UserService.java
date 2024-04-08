package com.project.myBlog.service;

import com.project.myBlog.entity.User;
import com.project.myBlog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
}
