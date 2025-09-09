package com.blog.blognest.repository;

import com.blog.blognest.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepo extends JpaRepository<Likes, Long> {
    Long countByBlogId(Long blogId);
}
