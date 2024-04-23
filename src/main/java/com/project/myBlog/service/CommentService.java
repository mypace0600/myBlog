package com.project.myBlog.service;

import com.project.myBlog.dto.CommentDto;
import com.project.myBlog.entity.Comment;
import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.User;
import com.project.myBlog.repository.CommentRepository;
import com.project.myBlog.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void save(CommentDto commentDto, User user) {
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(EntityNotFoundException::new);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(commentDto.getCommentContent());
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdateAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

}
