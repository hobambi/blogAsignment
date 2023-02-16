package com.hobambi.blogasignment.repository;

import com.hobambi.blogasignment.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
//        List<Comments>findAllByBlogIdOrderByModifiedAtDesc();
}
