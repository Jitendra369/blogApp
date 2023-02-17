package com.blogapp.repo;

import com.blogapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRespo extends JpaRepository<Comment, Integer> {

}
