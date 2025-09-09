package com.blog.blognest.controller;

import com.blog.blognest.entity.Blog;
import com.blog.blognest.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/blog")
@CrossOrigin(origins = "http://localhost:3000")
public class BlogController {

    @Autowired
    private BlogService blogService;


    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Blog> createBlog( @RequestPart("blog") Blog blog,
                                            @RequestPart(value = "file", required = false)
                                            MultipartFile file){
        return ResponseEntity.ok().body(blogService.createBlog(blog));
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs(){
        return ResponseEntity.ok().body(blogService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@RequestBody Blog blog, @PathVariable Long id){
        return ResponseEntity.ok().body(blogService.updateBlog(blog, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id){
        return ResponseEntity.ok().body(blogService.deleteBlog(id));
    }
}
