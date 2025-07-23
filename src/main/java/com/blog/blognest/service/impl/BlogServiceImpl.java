package com.blog.blognest.service.impl;

import com.blog.blognest.entity.Blog;
import com.blog.blognest.entity.Users;
import com.blog.blognest.exception.NotFoundException;
import com.blog.blognest.repository.BlogRepo;
import com.blog.blognest.repository.UsersRepo;
import com.blog.blognest.service.BlogService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private UsersRepo usersRepo;


    @Override
    public Blog createBlog(Blog blog) {

        Long userId = blog.getUser().getId();

        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id : " + userId));
        blog.setUser(user);

        blog.setCreated_at(LocalDateTime.now());
        return blogRepo.save(blog) ;
    }

    @Override
    public List<Blog> getAll() {
        return blogRepo.findAll();
    }

    @Override
    public Blog updateBlog(Blog blog, Long id) {
        return blogRepo.findById(id).map(
                existingBlog ->{
                    existingBlog.setContent(blog.getContent());
                    existingBlog.setTitle(blog.getTitle());
                    return blogRepo.save(existingBlog);
                }).orElseThrow( () ->
                new NotFoundException("Blog not found with id ; "+ id));

    }

    @Override
    public String deleteBlog(long id) {
        if(blogRepo.existsById(id)){
            blogRepo.deleteById(id);
            return "Blog Deleted Sucessfully";
        }else {
            return "blog not found with id : "+ id;
        }
    }


}
