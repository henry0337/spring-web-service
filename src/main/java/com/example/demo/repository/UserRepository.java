package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "select * from users where username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "delete from users where username = :username", nativeQuery = true)
    void deleteByUsername(@Param("username") String username);
}
