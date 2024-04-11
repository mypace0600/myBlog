package com.project.myBlog.service;

import com.project.myBlog.entity.Tag;
import com.project.myBlog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public boolean checkTagNameDuplicated(String tagName){
        Optional<Tag> tag = tagRepository.findByTagName(tagName);
        return tag.isPresent();
    }
    public void save(Tag tag){
        if(!checkTagNameDuplicated(tag.getTagName())){
            tagRepository.save(tag);
        }
    }
}
