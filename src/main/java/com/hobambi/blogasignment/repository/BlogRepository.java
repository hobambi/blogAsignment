package com.hobambi.blogasignment.repository;

import com.hobambi.blogasignment.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// db에서 원하는 정보를 가져옵니다.
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByModifiedAtDesc();
}

