//package com.project.myBlog.common;
//
//
//import com.project.myBlog.entity.Tag;
//import com.project.myBlog.repository.TagRepository;
//import com.project.myBlog.service.TagService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class TagListModel {
//
//
//
//    private List<Tag> tags = new ArrayList<>();
//
//    private final TagRepository tagRepository;
//    @Autowired
//    public TagListModel(TagRepository tagRepository) {
//        this.tagRepository = tagRepository;
//    }
//
//    public void loadTagList() {
//        this.tags = tagRepository.findAll();
//    }
//
//
//    public List<Tag> getTags() {
//        return this.tags;
//    }
//}
//
//
//
