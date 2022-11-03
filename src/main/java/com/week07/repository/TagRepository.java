package com.week07.repository;

import com.week07.domain.Member;
import com.week07.domain.Post;
import com.week07.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {

    List<Tag> findAllByMemberAndPost(Member member, Post post);

    List<Tag> findAllByMemberAndTagName(Member member, String tag);

    List<Tag> findAllByTagName(String tag);
}
