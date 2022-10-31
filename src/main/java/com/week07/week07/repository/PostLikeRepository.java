package com.week07.week07.repository;

import com.week07.week07.domain.Member;
import com.week07.week07.domain.Post;
import com.week07.week07.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByMemberAndPost(Member member, Post post);

    List<PostLike> findByPost(Post post);
}
