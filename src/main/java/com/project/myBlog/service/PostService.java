package com.project.myBlog.service;

import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.RoleType;
import com.project.myBlog.entity.User;
import com.project.myBlog.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    @Transactional(readOnly = true)
    public Page<Post> getList(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    @Transactional
    public void save(Post post, User user) {
        post.setUser(user);
        post.setCount(0);
        postRepository.save(post);
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
