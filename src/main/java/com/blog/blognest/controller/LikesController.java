package com.blog.blognest.controller;

import com.blog.blognest.entity.Likes;
import com.blog.blognest.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/like")
public class LikesController {

    @Autowired
    private LikesService likesService;


    @PostMapping
    public ResponseEntity<Likes> createLike(@RequestBody Likes likes){
       return ResponseEntity.ok().body(likesService.createLike(likes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLike(@PathVariable Long id ){
        return ResponseEntity.ok().body(likesService.deleteLike(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Long> likeCountByBlog(@PathVariable Long id){
        return ResponseEntity.ok().body(likesService.getLikesCountByBlogId(id));
    }
}
