package com.project.myBlog.config;

import com.project.myBlog.entity.User;
import com.project.myBlog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User principal = userRepository.findByEmail(email).orElseThrow(()->{
            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."+email);
        });
        return new PrincipalDetail(principal,new ArrayList<>(),new HashMap<>());
    }
}
