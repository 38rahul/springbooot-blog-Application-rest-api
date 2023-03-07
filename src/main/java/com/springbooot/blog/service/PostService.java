package com.springbooot.blog.service;

import com.springbooot.blog.entity.Post;
import com.springbooot.blog.payload.PostDto;
import com.springbooot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);  // POST

    PostResponse getAllPost(int pageNo , int pageSize, String sortBy, String sortDir);             // GET

    PostDto   getPostById(long id);          //POST

    PostDto updatePost(PostDto postDto , long id);   // PUT

    void deletePostById(long id);                   // Delete



}


