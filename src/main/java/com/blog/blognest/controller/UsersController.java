package com.blog.blognest.controller;

import com.blog.blognest.entity.Users;
import com.blog.blognest.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<Users> createUser (@RequestBody Users  users){
        return ResponseEntity.accepted().body(usersService.saveUser(users));
    }
}
