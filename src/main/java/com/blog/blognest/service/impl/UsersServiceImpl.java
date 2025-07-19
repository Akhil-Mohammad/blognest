package com.blog.blognest.service.impl;

import com.blog.blognest.entity.Users;
import com.blog.blognest.repository.UsersRepo;
import com.blog.blognest.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public Users saveUser(Users users) {
        LocalDateTime time = LocalDateTime.now();
        users.setCreated_at(time);
        return usersRepo.save(users);
    }
}
