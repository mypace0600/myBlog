package com.project.myBlog.service;

import com.project.myBlog.config.PrincipalDetail;

import com.project.myBlog.dto.PostDto;
import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.RoleType;
import com.project.myBlog.entity.User;
import com.project.myBlog.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    @Transactional(readOnly = true)
    public Page<Post> getList(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    @Transactional
    public Post save(PostDto postDto, User user) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setHidden(postDto.isHidden());
        post.setUser(user);
        post.setCount(0);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdateAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post findByIdAndUser(int id, Optional<PrincipalDetail> principal) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if(post.isHidden()){
            if(principal.isPresent() && (post.getUser().getId().equals(principal.get().getUser().getId()) || principal.get().getUser().getRoleType().equals(RoleType.ADMIN))){
                return post;
            } else {
                throw new SecurityException("비밀글 조회 권한이 없습니다.");
            }
        } else {
            return post;
        }
    }

    @Transactional
    public void updateViewCount(int id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        int count = post.getCount()+1;
        post.setCount(count);
    }

}
