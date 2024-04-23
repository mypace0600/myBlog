package com.project.myBlog.repository;

import com.project.myBlog.entity.QTag;
import com.project.myBlog.entity.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TagRepositoryCustomImpl implements TagRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public TagRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Tag> findAllOrderByPostCountTest(){
        return queryFactory
                .select(QTag.tag)
                .from(QTag.tag)
                .leftJoin(QTag.tag.postTagList).fetchJoin()
                .groupBy(QTag.tag.id)
                .orderBy(QTag.tag.postTagList.size().desc())
                .fetch();

    }


}
