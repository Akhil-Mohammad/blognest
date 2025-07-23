package com.blog.blognest.repository;

import com.blog.blognest.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Long> {



}
