package com.week07.repository;

import com.week07.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByTitleContainingOrContentContaining(String searchKeyword, String searchKeyword1);
}
