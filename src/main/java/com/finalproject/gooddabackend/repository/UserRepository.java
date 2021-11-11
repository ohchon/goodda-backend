package com.finalproject.gooddabackend.repository;

import com.finalproject.gooddabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserEmail(String userEmail);
}
