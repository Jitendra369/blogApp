package com.blogapp.impl;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payload.CommentDto;
import com.blogapp.repo.CommentRespo;
import com.blogapp.repo.PostRepo;
import com.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRespo commentRespo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;


    @Override
    public CommentDto saveComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRespo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRespo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        this.commentRespo.delete(comment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer postId) {
        return null;
    }
}
