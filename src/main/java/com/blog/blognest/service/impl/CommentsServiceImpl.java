package com.blog.blognest.service.impl;

import com.blog.blognest.entity.Blog;
import com.blog.blognest.entity.Comments;
import com.blog.blognest.entity.Users;
import com.blog.blognest.exception.NotFoundException;
import com.blog.blognest.repository.BlogRepo;
import com.blog.blognest.repository.CommentsRepo;
import com.blog.blognest.repository.UsersRepo;
import com.blog.blognest.service.CommentsSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsSevice {

    @Autowired
    private CommentsRepo commentsRepo;

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private UsersRepo  usersRepo;

    @Override
    public Comments createComment(Comments comment) {
        if (comment.getBlog() == null || comment.getBlog().getId() == null) {
            throw new NotFoundException("Blog ID is required");
        }
        if (comment.getUser() == null || comment.getUser().getId() == null) {
            throw new NotFoundException("User ID is required");
        }
        Long blogId = comment.getBlog().getId();
        Long userId = comment.getUser().getId();

        Blog blog = blogRepo.findById(blogId)
                .orElseThrow(() -> new NotFoundException("Blog not found with id: " + blogId));

        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        comment.setBlog(blog);
        comment.setUser(user);
        comment.setCreated_at(LocalDateTime.now());
        return commentsRepo.save(comment) ;
    }

    @Override
    public Comments editComment(Comments comments , Long id) {
        return commentsRepo.findById(id).map(
                existingComment ->{
                    existingComment.setContent(comments.getContent());
                    return commentsRepo.save(existingComment);
                }).orElseThrow(() -> new NotFoundException("Comment not fopund for id : "+ id));
    }

    @Override
    public String deleteComment(Long id) {
        if(commentsRepo.existsById(id)){
            commentsRepo.deleteById(id);
            return "Comment Deleted Successfully";
        }else{
            return "Comment Not found for id" + id;
        }
    }

    @Override
    public List<Comments> findByBlogId(Long blogId) {
        if (blogRepo.existsById(blogId)) {
            return commentsRepo.findByBlogId(blogId);
        }else {
            throw  new NotFoundException("No Comments");
        }
    }


}
