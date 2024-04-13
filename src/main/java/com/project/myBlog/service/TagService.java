package com.project.myBlog.service;

import com.project.myBlog.entity.Post;
import com.project.myBlog.entity.PostTag;
import com.project.myBlog.entity.Tag;
import com.project.myBlog.repository.PostTagRepository;
import com.project.myBlog.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final PostTagRepository postTagRepository;

    public boolean duplicatedTagNameCheck(String tagName){
        Optional<Tag> tag = tagRepository.findByTagName(tagName);
        return tag.isPresent();
    }

   public void save(Post savedPost, String tagString){
       String[] tagArr = tagString.split("#");
       for(String t : tagArr){
           String tagName = t.trim().replaceAll(" ","_");
           if(tagName.isEmpty()){
               continue;
           }
           Tag tempTag = tagRepository.findByTagName(tagName).orElseGet(Tag::new);
           tempTag.setTagName(tagName);
           tagRepository.save(tempTag);

           PostTag tempPostTag = postTagRepository.findByPostIdAndTagId(savedPost.getId(),tempTag.getId()).orElseGet(PostTag::new);
           tempPostTag.setPost(savedPost);
           tempPostTag.setTag(tempTag);
           postTagRepository.save(tempPostTag);
       }
   }
}
