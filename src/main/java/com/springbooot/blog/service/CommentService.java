package com.springbooot.blog.service;

import com.springbooot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById (Long postId, long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentRequest);

    void  deleteComment(Long postId, Long commentId);

}
