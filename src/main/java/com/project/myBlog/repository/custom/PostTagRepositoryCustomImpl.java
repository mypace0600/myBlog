package com.project.myBlog.repository.custom;

import com.project.myBlog.entity.PostTag;
import com.project.myBlog.entity.QPostTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PostTagRepositoryCustomImpl implements PostTagRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public PostTagRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<PostTag> postTagsByTagId(int tagId, Pageable pageable) {
        List<PostTag> content = queryFactory
                .select(QPostTag.postTag)
                .from(QPostTag.postTag)
                .innerJoin(QPostTag.postTag.tag).fetchJoin()
                .where(QPostTag.postTag.tag.id.eq(tagId))
                .orderBy(QPostTag.postTag.post.updateAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count =  queryFactory
                .select(QPostTag.postTag)
                .from(QPostTag.postTag)
                .innerJoin(QPostTag.postTag.tag).fetchJoin()
                .where(QPostTag.postTag.tag.id.eq(tagId))
                .orderBy(QPostTag.postTag.post.updateAt.desc())
                .stream().count();

        return new PageImpl<>(content,pageable,count);
    }
}
