package com.springbooot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // So, we are not going to create getter, setter, toString, equals() And HashCode() for CommentDto class

public class CommentDto {
    private long id;

    // name should not be null or empty
    @NotEmpty(message = "name should not be null or empty")
    private String name;

    //  email should not be null or empty
    // email field validation
    @NotEmpty(message = "email should not be null or empty")
    @Email
    private String email;

    // Comment body  should not be null or empty
    // Comment body must be minimum 10 characters
    @NotEmpty
    @Size(min=10, message = "Comment body must be minimum 10 characters")
    private String body;
}
