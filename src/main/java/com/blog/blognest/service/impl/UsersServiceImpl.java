package com.blog.blognest.service.impl;

import com.blog.blognest.entity.Users;
import com.blog.blognest.exception.NotFoundException;
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

    @Override
    public Users getUserById(Long id) {
        return usersRepo.findById(id).orElseThrow(()-> new NotFoundException("User Not found with that id :"+ id));
    }

    @Override
    public Users updateUserDetails(Users users, Long id) {
        return usersRepo.findById(id).map(existingUser -> {
                    existingUser.setUserName(users.getUserName());

                    return usersRepo.save(existingUser);
                })
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    @Override
    public String deleteUser(Long id) {
        if(usersRepo.existsById(id)){
            usersRepo.deleteById(id);
            return "User Deleted sucessfully for id "+ id;
        }else {
            return "User Not found with Id: "+ id;
        }

    }
}
