package com.blog.blognest.repository;

import com.blog.blognest.entity.Blog;
import com.blog.blognest.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepo extends JpaRepository<Blog, Long> {




}
