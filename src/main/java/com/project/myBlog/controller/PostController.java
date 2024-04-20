package com.project.myBlog.controller;

import com.project.myBlog.common.ResponseDto;
import com.project.myBlog.config.PrincipalDetail;
import com.project.myBlog.dto.PostDto;
import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.PostTag;
import com.project.myBlog.repository.PostTagRepository;
import com.project.myBlog.repository.TagRepository;
import com.project.myBlog.service.PostService;
import com.project.myBlog.service.TagService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final TagService tagService;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    @GetMapping("/post")
    public String postList(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("postList",postService.getList(pageable));
        model.addAttribute("tags", tagRepository.findAll());
        return "post/post";
    }




    @GetMapping("/post/write")
    public String postWriteForm(){
        return "post/writeForm";
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
    @GetMapping("/post/tag/{id}")//post에서 특정 tag가 포함된  List 전체 조회
    public String findAllByTagId(@PathVariable Integer id){
        List<PostTag> postTagList = postTagRepository.findAllByTagId(id);

        List<Post> posts = new ArrayList<>();

        for(PostTag postTag : postTagList){
            posts.add(postTag.getPost());
        }

        System.out.println(posts);
        log.debug("postTagList: " + postTagList);
        return "post/post";
    }
}

