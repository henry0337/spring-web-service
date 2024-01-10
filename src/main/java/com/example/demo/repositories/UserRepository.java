package com.example.demo.repositories;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "select * from users where display_name = :name", nativeQuery = true)
    Optional<User> findByName(@Param("name") String name);

    @Query(value = "select * from users where email = :username", nativeQuery = true)
    Optional<User> findByEmail(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "delete from users where display_name = :name", nativeQuery = true)
    void deleteByName(@Param("name") String name);

    User findByRole(Role role);
}
