package com.project.myBlog.controller;

import com.project.myBlog.common.ResponseDto;
import com.project.myBlog.config.PrincipalDetail;
import com.project.myBlog.dto.PostDto;
import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.PostTag;
import com.project.myBlog.entity.Tag;
import com.project.myBlog.service.PostService;
import com.project.myBlog.service.PostTagService;
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

import java.util.Optional;



@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final TagService tagService;
    private final PostTagService postTagService;



    @GetMapping()
    public String postList(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("postList",postService.getList(pageable));
        model.addAttribute("isActive", "post");
        return "post/post";
    }

    @GetMapping("/write")
    public String postWriteForm(Model model){
        model.addAttribute("isActive", "post");

        return "post/write_form";
    }

    @PostMapping("/write")
    @ResponseBody
    public ResponseDto<Integer> postWrite(@RequestBody PostDto postDto, @AuthenticationPrincipal PrincipalDetail principal){
        Post savedPost = postService.save(postDto,principal.getUser());
        tagService.save(savedPost, postDto.getTagString());
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable int id, Model model, @AuthenticationPrincipal Optional<PrincipalDetail> principal){
        PostDto postDto = postService.findByIdAndUser(id,principal);
        postService.updateViewCount(id);
        model.addAttribute("post",postDto);
        model.addAttribute("isActive", "post");

        return "post/detail";
    }

    @GetMapping("/edit/{id}")
    public String postEditForm(@PathVariable int id, Model model) {
        PostDto post = postService.findById(id);
        model.addAttribute("post",post);
        model.addAttribute("isActive", "post");
        return "post/edit_form";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseDto<Integer> postEdit(@RequestBody PostDto postDto, @AuthenticationPrincipal PrincipalDetail principal) throws Exception {
        Post savedPost = postService.edit(postDto,principal.getUser());
        tagService.edit(savedPost, postDto.getTagString());
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @Deprecated
    @PostMapping("/delete")
    @ResponseBody
    public ResponseDto<Integer> postDelete(@RequestBody Post post){
        postService.deleteById(post.getId());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Integer id){
        postService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/tag/{id}")
    public String postTagList(@PathVariable int id, Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws Exception{
        Page<PostTag> postTagList = postTagService.postTagsByTagId(id, pageable);
//        List<PostTag> postTagListTest = postTagService.findAllByTagId(id);
//        log.debug("@@@@@@@@@@@ postTagListTest :{}",postTagListTest.toString());
        model.addAttribute("postTagList",postTagList);
        Tag tag = tagService.findById(id);
        model.addAttribute("tag",tag);
        model.addAttribute("isActive", "post");

        return "post/post_list";
    }
}
