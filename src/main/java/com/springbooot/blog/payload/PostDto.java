package com.springbooot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    // post title should not be empty or null
    // post title should have atleast 2 character
    @NotEmpty
    @Size(min = 2 , message = "Post title should have atleast 2 characters")
    private String title;

    // description should not nulll or empty
    // description should have atleast 2 character
    @NotEmpty
    @Size(min=10, message = "Post description should have atleast 10 characters")
    private String description;

    // post content should not be empty or null
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
