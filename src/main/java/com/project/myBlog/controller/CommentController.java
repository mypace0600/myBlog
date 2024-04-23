package com.project.myBlog.controller;

import com.project.myBlog.common.ResponseDto;
import com.project.myBlog.config.PrincipalDetail;
import com.project.myBlog.dto.CommentDto;
import com.project.myBlog.entity.Post;
import com.project.myBlog.service.CommentService;
import com.project.myBlog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public ResponseDto<Integer> commentAdd(@RequestBody CommentDto commentDto, @AuthenticationPrincipal PrincipalDetail principal){
        log.debug("@@@@@@@@@@@@@@@@@@ commentDto :{}",commentDto.toString());
        commentService.save(commentDto, principal.getUser());
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}