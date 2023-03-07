package com.springbooot.blog.repository;

import com.springbooot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //we need to create one custom query method that is we need to retrieve a list of comments by postId.

    List<Comment> findByPostId(long postId);
}
