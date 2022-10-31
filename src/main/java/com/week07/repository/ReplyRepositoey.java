package com.week07.repository;

import com.week07.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepositoey extends JpaRepository <Reply, Long> {
}
