package com.lucas.credentials.repository;

import com.lucas.credentials.models.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Long> {
    UserDao findByEmail(String email);
}