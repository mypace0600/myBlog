package com.project.myBlog.service;

import com.project.myBlog.entity.PostTag;
import com.project.myBlog.repository.PostTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostTagService {

    private final PostTagRepository postTagRepository;
    public Page<PostTag> postTagsByTagId(int id, Pageable pageable) {
        return postTagRepository.postTagsByTagId(id, pageable);
    }


    public List<PostTag> findAllByTagId(int id) {
        return postTagRepository.findAllByTagId(id);
    }
}
