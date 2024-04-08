package com.project.myBlog.controller;

import com.project.myBlog.entity.User;
import com.project.myBlog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/auth/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "user/loginForm";
    }

    @GetMapping("/auth/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("user",new User());
        return "user/joinForm";
    }

    @PostMapping("/auth/joinProc")
    public String register(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) throws BadRequestException {
        if(bindingResult.hasErrors()){
            return "auth/joinForm";
        }
        try {
            User createdUser = User.createUser(user, passwordEncoder);
            userService.register(createdUser);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "auth/joinForm";
        }
        return "redirect:/";
    }
}
