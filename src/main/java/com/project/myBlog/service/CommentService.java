package com.project.myBlog.service;

import com.project.myBlog.dto.CommentDto;
import com.project.myBlog.entity.Comment;
import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.User;
import com.project.myBlog.repository.CommentRepository;
import com.project.myBlog.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        commentRepository.save(comment);
    }

    @Transactional
    public void update(CommentDto commentDto, User user) throws Exception {
        Comment savedComment = commentRepository.findById(commentDto.getCommentId()).orElseThrow(EntityNotFoundException::new);
        if(savedComment.getUser().getId() != user.getId()){
            throw new Exception("사용자가 일치하지 않습니다.");
        }
        savedComment.setContent(commentDto.getCommentContent());
        commentRepository.save(savedComment);
    }

    @Transactional
    public void delete(Integer commentId, User user) throws Exception {
        Comment savedComment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        if(savedComment.getUser().getId() != user.getId()){
            throw new Exception("사용자가 일치하지 않습니다.");
        }
        commentRepository.delete(savedComment);
    }

    @Transactional(readOnly = true)
    public Page<Comment> getCommentPageByPostId(int postId, Pageable pageable) {
        return commentRepository.getCommentPageByPostId(postId,pageable);
    }
}
