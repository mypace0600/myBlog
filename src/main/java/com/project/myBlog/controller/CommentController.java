package com.project.myBlog.controller;

import com.project.myBlog.common.ResponseDto;
import com.project.myBlog.config.PrincipalDetail;
import com.project.myBlog.dto.CommentDto;
import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.User;
import com.project.myBlog.service.CommentService;
import com.project.myBlog.service.PostService;
import com.project.myBlog.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/comment")
    @ResponseBody
    public ResponseDto<Integer> commentAdd(@RequestBody CommentDto commentDto, @AuthenticationPrincipal PrincipalDetail principal, Authentication authentication){
        log.debug("@@@@@@@@@@@@@@ principal :{}",principal.getUser().getEmail());
        User user  = principal.getUser();
        commentService.save(commentDto, user);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
