package com.hobambi.blogasignment.repository;

import com.hobambi.blogasignment.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 댓글에 대한 원하는 정보를 가져옵니다.
@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {

    List<Comments> findByBlog_Id(Long id);
}
