package com.bilgeadam.repository;

import com.bilgeadam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
    List<User> findAllByOrderByName(); //1.Soru
    Boolean existsByNameContainsIgnoreCase(String name); //2.Soru
    List<User> findAllByNameContainingIgnoreCase(String value); //3.Soru
    Optional<User> findOptionalByEmail(String email); //4.Soru








}
