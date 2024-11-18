package com.slippery.videochatappbackend.repository;

import com.slippery.videochatappbackend.dto.UserDto;
import com.slippery.videochatappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u.username FROM User u WHERE u.status = 'inactive'")
    List<String> findAllUsernames();
    User findByUsername(String username);
    User findByEmail(String email);



}
