package com.springbooot.blog.controller;

import com.springbooot.blog.payload.CommentDto;
import com.springbooot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment( @PathVariable(value = "postId") long postId,
                                                   @Valid @RequestBody CommentDto commentDto){
        System.out.println("from controller: "+ commentDto);
        return  new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId){
        CommentDto commentDto = commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);

    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment( @PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") long commentId,
                                                   @Valid @RequestBody CommentDto commentRequest){
        CommentDto updatedComment = commentService.updateComment(postId,commentId,commentRequest);
        return new ResponseEntity<>(updatedComment,HttpStatus.OK);

    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String>  deleteComment(@PathVariable(value = "postId") Long postId,
                                                 @PathVariable(value = "id")  Long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment Deleted Successfully!",HttpStatus.OK);
    }
}
