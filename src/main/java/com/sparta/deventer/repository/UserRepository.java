package com.sparta.deventer.repository;

import com.sparta.deventer.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);
}