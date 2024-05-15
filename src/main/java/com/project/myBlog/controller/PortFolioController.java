package com.project.myBlog.controller;

import com.project.myBlog.dto.PostDto;
import com.project.myBlog.entity.Post;
import com.project.myBlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PortFolioController {

    private final PostRepository postRepository;

    @GetMapping("/port")
    public String portFolio(Model model){
        List<Post> portFolio = postRepository.findByPortFolio();

        try {
            if (portFolio.size() > 0) {
                log.debug("@@@@@@@@@ portFolio.size :{}", portFolio.size());
                Post post = portFolio.get(0);
                PostDto postDto = new PostDto();
                postDto.setTitle(post.getTitle());
                postDto.setContent(post.getContent());
                model.addAttribute("post", postDto);
                model.addAttribute("isActive", "post");
            } else {
                return "common/error/errorPortFolio400";
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "port/port";
    }
}
