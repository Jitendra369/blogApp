package com.blogapp.services;

import com.blogapp.payload.CommentDto;

public interface CommentService {
    CommentDto saveComment(CommentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);
    CommentDto updateComment(CommentDto commentDto, Integer postId); //todo : remain
}
