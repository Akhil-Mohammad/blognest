package com.blog.blognest.controller;

import com.blog.blognest.entity.Users;
import com.blog.blognest.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    Logger log = LoggerFactory.getLogger(UsersController.class);

    @PostMapping
    public ResponseEntity<Users> createUser (@RequestBody Users  users){
        log.info("Controller :: user creation input {}", users);
        return ResponseEntity.accepted().body(usersService.saveUser(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getById (@PathVariable  Long id){
        log.info("Controller :: got user details for id {}", id);
        return ResponseEntity.ok().body(usersService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser (@PathVariable Long id, @RequestBody Users users){
        log.info("Controller :: user update data {}", users);
        return ResponseEntity.ok().body(usersService.updateUserDetails(users, id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        log.info("Controller :: user deleted with id {}", id);
        return ResponseEntity.ok().body(usersService.deleteUser(id));
    }
}
