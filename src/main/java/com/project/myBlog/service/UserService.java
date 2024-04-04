package com.project.myBlog.service;

import com.project.myBlog.entity.User;
import com.project.myBlog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void register(User user) {
        userRepository.save(user);
    }
}
