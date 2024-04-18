package com.project.myBlog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {



    @GetMapping("/")
    public String main() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.debug("@@@@@@@@@@@@@@ User '{}' has been successfully authenticated", auth.getName());
            log.debug("@@@@@@@@@@@@@@ User authorities: {}", auth.getAuthorities());
        } else {
            log.error("User authentication failed");
        }
        return "index";
    }
}
