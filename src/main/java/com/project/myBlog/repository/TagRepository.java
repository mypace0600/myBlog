package com.project.myBlog.repository;

import com.project.myBlog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;


public interface TagRepository extends JpaRepository<Tag, Integer>,
        QuerydslPredicateExecutor<Tag>,
        TagRepositoryCustom {

    Optional<Tag> findByTagName(String tagName);

    @Query("SELECT t FROM Tag t JOIN t.postTagList pt GROUP BY t.id ORDER BY COUNT(pt.post) DESC")
    List<Tag> findAllOrderByPostCount(Pageable pageable);
}
