package com.blog.blognest.repository;

import com.blog.blognest.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepo extends JpaRepository<Blog, Long> {

}
