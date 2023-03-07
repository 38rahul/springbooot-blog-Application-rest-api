package com.springbooot.blog.controller;

import com.springbooot.blog.payload.PostDto;
import com.springbooot.blog.payload.PostResponse;
import com.springbooot.blog.service.PostService;
import com.springbooot.blog.utils.Appconstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    // create blog post Rest Api
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")  // It means only admin user can able to access this create post API
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get All posts rest Api
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = Appconstants.DEFAULT_PAGE_NUMBER ,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = Appconstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Appconstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "",defaultValue =Appconstants.DEFAULT_SORT_DIRECTION ,required = false) String sortDir
    ){
        return postService.getAllPost(pageNo, pageSize,sortBy,sortDir);
    }
    // get post by id
    @GetMapping("/{id}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable Long id){
        System.out.println("get by id method");
        return ResponseEntity.ok(postService.getPostById(id)) ;

    }
    //update post by id rest Api
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long id){
        PostDto postResponse =  postService.updatePost(postDto,id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // delete post rest Api
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Entity Deleted Successfully!!", HttpStatus.OK);
    }



}
