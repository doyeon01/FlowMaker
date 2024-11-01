package com.ssafy.flowstudio.domain.user.repository;

import com.ssafy.flowstudio.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByNickname(String nickname);
}
