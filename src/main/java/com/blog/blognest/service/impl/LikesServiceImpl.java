package com.blog.blognest.service.impl;

import com.blog.blognest.entity.Blog;
import com.blog.blognest.entity.Likes;
import com.blog.blognest.entity.Users;
import com.blog.blognest.exception.NotFoundException;
import com.blog.blognest.repository.BlogRepo;
import com.blog.blognest.repository.LikesRepo;
import com.blog.blognest.repository.UsersRepo;
import com.blog.blognest.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    private LikesRepo likesRepo;

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private UsersRepo usersRepo;


    @Override
    public Likes createLike(Likes likes) {
        if (likes.getBlog() == null || likes.getBlog().getId() == null) {
            throw new NotFoundException("Blog ID is required");
        }
        if (likes.getUsers() == null || likes.getUsers().getId() == null) {
            throw new NotFoundException("User ID is required");
        }
        Long blogId = likes.getBlog().getId();
        Long userId = likes.getUsers().getId();

        Blog blog = blogRepo.findById(blogId)
                .orElseThrow(() -> new NotFoundException("Blog not found with id: " + blogId));

        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        likes.setBlog(blog);
        likes.setUsers(user);
         return likesRepo.save(likes);
    }

    @Override
    public String deleteLike(Long id) {
        if(likesRepo.existsById(id)){
            likesRepo.deleteById(id);
            return "like deleted Successfully for id : "+ id;
        }else{
            return "No like found for id : " + id;
        }
    }

    @Override
    public Long getLikesCountByBlogId(Long blogId) {
        return likesRepo.countByBlogId(blogId);
    }


}
