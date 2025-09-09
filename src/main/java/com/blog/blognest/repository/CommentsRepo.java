package com.blog.blognest.repository;

import com.blog.blognest.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepo extends JpaRepository<Comments, Long> {

    List<Comments> findByBlogId(Long blogId);
}
