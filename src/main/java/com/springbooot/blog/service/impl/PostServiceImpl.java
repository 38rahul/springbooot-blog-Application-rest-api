package com.springbooot.blog.service.impl;

import com.springbooot.blog.entity.Post;
import com.springbooot.blog.exception.ResourceNotFoundException;
import com.springbooot.blog.payload.CommentDto;
import com.springbooot.blog.payload.PostDto;
import com.springbooot.blog.payload.PostResponse;
import com.springbooot.blog.repository.CommentRepository;
import com.springbooot.blog.repository.PostRepository;
import com.springbooot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import   org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper mapper;
    private final CommentRepository commentRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper,
                           CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.commentRepository = commentRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        // Convert ENTITY to DTO
        PostDto postResponse =  mapToDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo , int pageSize, String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Create pageable instance
        Pageable pageable = (Pageable) PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAll( pageable);

        //get content from page Object
        List<Post> listOfPosts = posts.getContent();
       // System.out.println(posts);
        //System.out.println(posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList()));
        List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get post entity by id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);
        return mapToDto(updatePost);

    }

    @Override
    public void deletePostById(long id) {
        // get post entity by id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));
        postRepository.delete(post);

    }

    // convert post Entity into PostDto
    private PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post,PostDto.class);

//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }
    //Convert Dto to Entity
    private Post mapToEntity(PostDto postDto){

        Post post = mapper.map(postDto,Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }

}

// best practice to use constructor based dependency injection Guys.
// From spring 4.3 onwards, if a class is configured as a spring bean, and it has only one constructor,
//then we can omit the @Autowired annotation  OK.
