package com.project.myBlog.controller;

import com.project.myBlog.common.ResponseDto;
import com.project.myBlog.config.PrincipalDetail;
import com.project.myBlog.entity.Post;
import com.project.myBlog.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @GetMapping("/post")
    public String postList(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("postList",postService.getList(pageable));
        return "index";
    }

    @GetMapping("/post/write")
    public String postWriteForm(){
        return "post/writeForm";
    }

    @PostMapping("/post/write")
    @ResponseBody
    public ResponseDto<Integer> postWrite(@RequestBody Post post, @AuthenticationPrincipal PrincipalDetail principal){
        log.debug("@@@@@@@@@@@@@@@@@@@ post : {}",post.toString());
        postService.save(post,principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @GetMapping("/post/{id}")
    public String findById(@PathVariable int id, Model model, HttpServletRequest request, @AuthenticationPrincipal  PrincipalDetail principal){
        Post post = null;
        try {
            post = postService.findByIdAndUser(id,principal.getUser());
        } catch (Exception e) {
            return "post/error";
        }
        postService.updateViewCount(id);
        model.addAttribute("post",post);

        return "post/detail";
    }

}
