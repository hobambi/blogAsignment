package com.hobambi.blogasignment.repository;

import com.hobambi.blogasignment.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByModifiedAtDesc();
    Blog findByIdAndPassword(Long id, Long password);

}

