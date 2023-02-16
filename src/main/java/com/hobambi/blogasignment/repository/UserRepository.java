package com.hobambi.blogasignment.repository;

import com.hobambi.blogasignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 사용자에 대한 원하는 정보를 가져옵니다.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
