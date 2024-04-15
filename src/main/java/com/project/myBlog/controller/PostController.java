package com.project.myBlog.controller;

import com.project.myBlog.common.ResponseDto;
import com.project.myBlog.config.PrincipalDetail;
import com.project.myBlog.dto.PostDto;
import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.Tag;
import com.project.myBlog.service.PostService;
import com.project.myBlog.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final TagService tagService;

    @GetMapping("/post")
    public String postList(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("postList",postService.getList(pageable));
        return "post/post";
    }

    @GetMapping("/post/write")
    public String postWriteForm(){
        return "post/write_form";
    }

    @PostMapping("/post/write")
    @ResponseBody
    public ResponseDto<Integer> postWrite(@RequestBody PostDto postDto, @AuthenticationPrincipal PrincipalDetail principal){
        Post savedPost = postService.save(postDto,principal.getUser());
        tagService.save(savedPost, postDto.getTagString());
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @GetMapping("/post/{id}")
    public String findById(@PathVariable int id, Model model, @AuthenticationPrincipal Optional<PrincipalDetail> principal){
        PostDto postDto = postService.findByIdAndUser(id,principal);
        postService.updateViewCount(id);
        model.addAttribute("post",postDto);
        return "post/detail";
    }

    @GetMapping("/post/edit/{id}")
    public String postEditForm(@PathVariable int id, Model model) {
        PostDto post = postService.findById(id);
        model.addAttribute("post",post);
        return "post/edit_form";
    }

    @PostMapping("/post/edit")
    @ResponseBody
    public ResponseDto<Integer> postEdit(@RequestBody PostDto postDto, @AuthenticationPrincipal PrincipalDetail principal) throws Exception {
        Post savedPost = postService.edit(postDto,principal.getUser());
        tagService.edit(savedPost, postDto.getTagString());
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @Deprecated
    @PostMapping("/post/delete")
    @ResponseBody
    public ResponseDto<Integer> postDelete(@RequestBody Post post){
        postService.deleteById(post.getId());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Integer id){
        postService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/post/tag/{id}")
    public String postListByTagId(@PathVariable int id, Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws Exception{
        Page<Post> postList = postService.findAllByTagId(id,pageable);
        model.addAttribute("postList",postList);
        Tag tag = tagService.findById(id);
        model.addAttribute("tag",tag);
        return "post/post_list";
    }
}
