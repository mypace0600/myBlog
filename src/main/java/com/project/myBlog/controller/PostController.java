package com.project.myBlog.controller;

import com.project.myBlog.common.ResponseDto;
import com.project.myBlog.config.PrincipalDetail;
import com.project.myBlog.dto.PostDto;
import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.PostTag;
import com.project.myBlog.entity.Tag;
import com.project.myBlog.entity.enums.RoleType;
import com.project.myBlog.service.ImageService;
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
import java.util.UUID;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final TagService tagService;
    private final PostTagService postTagService;
    private final ImageService imageService;


    @GetMapping()
    public String postList(Model model, @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("postList",postService.getList(pageable));
        model.addAttribute("isActive", "post");
        int noImgId = imageService.findNoImgId();
        model.addAttribute("noImgId",noImgId);
        return "post/post";
    }

    @GetMapping("/write")
    public String postWriteForm(Model model){
        model.addAttribute("isActive", "post");
        // 작성중인 Post 와 등록한 Image 간 매칭을 위해 만든 UUID
        UUID uuid = UUID.randomUUID();
        String tempPostAndImageMappingUuid = uuid.toString();
        model.addAttribute("uuid",tempPostAndImageMappingUuid);
        return "post/write_form";
    }

    @PostMapping("/write")
    @ResponseBody
    public ResponseDto<Integer> postWrite(@RequestBody PostDto postDto, @AuthenticationPrincipal PrincipalDetail principal) throws Exception {
        log.debug("@@@@@@@@@@@@@@@@@@ portFolio :{}",postDto.isPortFolio());
        if(!principal.getUser().getRoleType().equals(RoleType.ADMIN.toString())){
            throw new Exception("글쓰기 권한이 없습니다.");
        }
        try {
            Post savedPost = postService.save(postDto, principal.getUser());
            tagService.save(savedPost, postDto.getTagString());
            imageService.imageAndPostMappingUpdate(savedPost);
        } catch (Exception e){
            e.printStackTrace();
        }
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
        try {
            tagService.edit(savedPost, postDto.getTagString());
            imageService.imageAndPostMappingUpdate(savedPost);
        } catch (Exception e){
            e.printStackTrace();
        }
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
