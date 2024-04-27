package com.project.myBlog.service;

import com.project.myBlog.config.PrincipalDetail;

import com.project.myBlog.dto.PostDto;
import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.PostTag;
import com.project.myBlog.entity.enums.RoleType;
import com.project.myBlog.entity.User;
import com.project.myBlog.repository.PostRepository;
import com.project.myBlog.repository.PostTagRepository;
import com.project.myBlog.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;

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
    public PostDto findByIdAndUser(int id, Optional<PrincipalDetail> principal) {

        PostDto postDto = new PostDto();
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setHidden(post.isHidden());
        postDto.setCommentList(post.getCommentList());

        if (post.isHidden() && principal.isPresent()
                && (post.getUser().getId().equals(principal.get().getUser().getId())
                        || principal.get().getUser().getRoleType().equals(RoleType.ADMIN))){
            throw new SecurityException("비밀글 조회 권한이 없습니다.");
        }

        StringBuilder tagStringBuilder = new StringBuilder();
        List<PostTag> postTagList = postTagRepository.findByPostId(post.getId());
        for(PostTag postTag:postTagList){
            String tagName = tagRepository.findById(postTag.getTag().getId()).orElseThrow(EntityNotFoundException::new).getTagName();
            tagStringBuilder.append("#");
            tagStringBuilder.append(tagName);
            tagStringBuilder.append(" ");
        }
        String tagString = tagStringBuilder.toString();
        postDto.setTagString(tagString);
        return postDto;
    }

    @Transactional
    public void updateViewCount(int id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        int count = post.getCount()+1;
        post.setCount(count);
    }

    @Transactional(readOnly = true)
    public PostDto findById(int id) {
        Post savedPost = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        PostDto postDto = new PostDto();
        postDto.setId(savedPost.getId());
        postDto.setTitle(savedPost.getTitle());
        postDto.setContent(savedPost.getContent());
        postDto.setHidden(savedPost.isHidden());

        StringBuilder tagStringBuilder = new StringBuilder();
        List<PostTag> postTagList = savedPost.getPostTagList();
        for(PostTag pt:postTagList){
            String tag = pt.getTag().getTagName();
            tagStringBuilder.append("#");
            tagStringBuilder.append(tag);
            tagStringBuilder.append(" ");
        }
        String tagString = tagStringBuilder.toString();
        postDto.setTagString(tagString);

        return postDto;
    }

    public Post edit(PostDto postDto, User user) throws Exception {
        if (user.getRoleType().equals(RoleType.ADMIN)) {
        Post post = postRepository.findById(postDto.getId()).orElseThrow(EntityNotFoundException::new);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setHidden(postDto.isHidden());
        post.setUpdateAt(LocalDateTime.now());
        return postRepository.save(post);
        }else {
        throw new Exception("권한이 없음");
        }
    }

    @Transactional
    public void deleteById(Integer id) {
//        postTagRepository.deleteAllByPostId(id);
        postRepository.deleteById(id);
    }
}
