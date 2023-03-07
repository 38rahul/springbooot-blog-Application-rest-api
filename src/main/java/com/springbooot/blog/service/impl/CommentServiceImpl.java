package com.springbooot.blog.service.impl;
import com.springbooot.blog.entity.Comment;
import com.springbooot.blog.entity.Post;
import com.springbooot.blog.exception.BlogApiException;
import com.springbooot.blog.exception.ResourceNotFoundException;
import com.springbooot.blog.payload.CommentDto;
import com.springbooot.blog.payload.PostDto;
import com.springbooot.blog.payload.PostResponse;
import com.springbooot.blog.repository.CommentRepository;
import com.springbooot.blog.repository.PostRepository;
import com.springbooot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private  ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,
                              ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        // convert DTO to entity
        Comment comment =  mapToEntity(commentDto);

        // Retrieve Post Entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                        ()->new ResourceNotFoundException("post","id",postId));
        System.out.println("comment impl: "+post);
        // set post to comment Entity
        comment.setPost(post);

        // Save Comment Entity to DB
        Comment newComment = commentRepository.save(comment);

        return  mapToDto(newComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        // retrieve list of comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
        System.out.println(comments);

        // convert List to comment Entities to list of comment sto's
        System.out.println( comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList()));
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, long commentId) {

        // Retrieve Post Entity by id
       Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","id",postId));

        // Retrieve comment Entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
        // Retrieve Post Entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        // Retrieve comment Entity by id
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(()->new ResourceNotFoundException("comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,
                    "comment does not belong to post");

        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updatedComment =  commentRepository.save(comment);

        return mapToDto(updatedComment);


    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // Retrieve Post Entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        // Retrieve comment Entity by id
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,
                    "comment does not belong to post");

        }
        commentRepository.delete(comment);

    }

    // convert Comment Entity into CommentDto
    private CommentDto mapToDto(Comment comment){

        CommentDto commentDto =mapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());

        return commentDto;
    }
    //Convert MapDto to Comment Entity
    private Comment mapToEntity(CommentDto commentDto){

        Comment comment = mapper.map(commentDto, Comment.class);

//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        return comment;
    }

}
