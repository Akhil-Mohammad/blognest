package com.blog.blognest.controller;

import com.blog.blognest.entity.Comments;
import com.blog.blognest.service.CommentsSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentsController {

    @Autowired
    private CommentsSevice commentsSevice;

    @PostMapping
    public ResponseEntity<Comments> createComment(@RequestBody Comments comments){
        return ResponseEntity.ok().body(commentsSevice.createComment(comments));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comments> updateComment(@RequestBody Comments comments,@PathVariable Long id){
        return ResponseEntity.ok().body(commentsSevice.editComment(comments, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id){
        return ResponseEntity.ok().body(commentsSevice.deleteComment(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Comments>> getCommentsByBlogId(@PathVariable Long id){
        return ResponseEntity.ok().body(commentsSevice.findByBlogId(id));
    }
}

