package com.blogapp.controllers;

import com.blogapp.impl.CommentServiceImpl;
import com.blogapp.payload.APIResponse;
import com.blogapp.payload.CommentDto;
import com.blogapp.repo.CommentRespo;
import com.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

//    comment-save handler
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> saveComment(
            @RequestBody CommentDto commentDto,
            @PathVariable("postId") Integer postId
    ){
        CommentDto commentDto1 = this.commentService.saveComment(commentDto, postId);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

//    delete-comment handler
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<APIResponse> deleteComment(
            @PathVariable("commentId") Integer commentId
    ){
        this.commentService.deleteComment(commentId);
        return  new ResponseEntity<APIResponse>(new APIResponse("commentId deleted",HttpStatus.OK,true), HttpStatus.OK);
    }
}
