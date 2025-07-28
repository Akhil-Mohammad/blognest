package com.blog.blognest.service;

import com.blog.blognest.entity.Blog;
import com.blog.blognest.entity.Comments;
import com.blog.blognest.entity.Likes;

import java.util.List;

public interface BlogService {

    Blog createBlog(Blog blog);

    List<Blog> getAll();

    Blog updateBlog(Blog blog , Long id);

    String deleteBlog(long id);

}
