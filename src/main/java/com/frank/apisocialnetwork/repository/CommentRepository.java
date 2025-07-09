package com.frank.apisocialnetwork.repository;

import com.frank.apisocialnetwork.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
