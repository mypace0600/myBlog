package com.project.myBlog.repository;

import com.project.myBlog.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findByUuid(String uuid);

}
