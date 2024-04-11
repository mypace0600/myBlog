package com.project.myBlog.service;

import com.project.myBlog.dto.PostWriteDto;
import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.RoleType;
import com.project.myBlog.entity.Tag;
import com.project.myBlog.entity.User;
import com.project.myBlog.repository.PostRepository;
import com.project.myBlog.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    @Transactional(readOnly = true)
    public Page<Post> getList(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    @Transactional
    public void save(PostWriteDto postWriteDto, User user) {

        Post post = new Post();
        post.setTitle(postWriteDto.getTitle());
        post.setContent(postWriteDto.getContent());
        post.setHidden(postWriteDto.isHidden());
        post.setTagString(postWriteDto.getTagString());
        post.setUser(user);
        post.setCount(0);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdateAt(LocalDateTime.now());
        postRepository.save(post);

        String tagString = postWriteDto.getTagString();
        String[] tagArray = tagString.split("#");
        for(String tag : tagArray){
            if(!tag.isEmpty() && !tag.isBlank()){
                log.debug("@@@@@@@@@@@@@@@@@@ tag :{}",tag);
                Tag tempTag = new Tag();
                tempTag.setTagName(tag.trim());
                tagService.save(tempTag);
            }
        }

    }

    @Transactional(readOnly = true)
    public Post findByIdAndUser(int id, User user) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if(post.isHidden()){
            if(post.getUser().getId().equals(user.getId()) || user.getRoleType().equals(RoleType.ADMIN)){
                return post;
            } else {
                throw new SecurityException("비밀글 조회 권한이 없습니다.");
            }
        }
        return post;
    }

    @Transactional
    public void updateViewCount(int id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        int count = post.getCount()+1;
        post.setCount(count);
    }


}
