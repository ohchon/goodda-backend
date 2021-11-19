package com.finalproject.gooddabackend.repository;

import com.finalproject.gooddabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserEmail(String userEmail);
    void deleteAllByStatusAndModifiedAtBefore(boolean status, LocalDateTime nowMinus30day);
    List<User> findAllByStatusAndModifiedAtBefore(boolean status, LocalDateTime nowMinus30day);
}
