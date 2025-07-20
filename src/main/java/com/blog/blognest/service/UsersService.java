package com.blog.blognest.service;

import com.blog.blognest.entity.Users;

public interface UsersService {

    public Users saveUser(Users users);

    public Users getUserById(Long id);

    public Users updateUserDetails( Users users, Long id);

     String deleteUser(Long id);
}
