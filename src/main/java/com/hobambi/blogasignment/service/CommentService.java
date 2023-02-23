package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.dto.CommentRequestDto;
import com.hobambi.blogasignment.dto.CommentResponseDto;
import com.hobambi.blogasignment.entity.Blog;
import com.hobambi.blogasignment.entity.Comments;
import com.hobambi.blogasignment.entity.User;
import com.hobambi.blogasignment.exception.CustomException;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.repository.BlogRepository;
import com.hobambi.blogasignment.repository.CommentRepository;
import com.hobambi.blogasignment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static com.hobambi.blogasignment.entity.UserRoleEnum.USER;
import static com.hobambi.blogasignment.exception.ErrorCode.*;

// 댓글 서비스
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    // 댓글 작성
    public ApiResult<CommentResponseDto> createComment(Long id, CommentRequestDto requestDto, User user) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_DATA)
        );

        Comments comments = commentRepository.saveAndFlush(new Comments(requestDto, blog));
        commentRepository.save(comments);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comments, user.getUsername());
        return new ApiResult<>(commentResponseDto, "댓글 생성");
    }

    // 댓글 수정
    public ApiResult<CommentResponseDto> updateComment(Long blogId, Long commentId, CommentRequestDto requestDto, User user) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                () -> new CustomException(NOT_FOUND_DATA)
        );
        Comments comments = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );

        String message = "";
        if (user.getRole() == USER) {
            if (user.getUsername().equals(comments.getUsername())) {
                comments.update(requestDto);
                message = "수정 성공";
            } else {
                throw new CustomException(FORBIDDEN_DATA);
            }
        } else {
            comments.update(requestDto);
            message = "수정 성공";
        }
        CommentResponseDto commentResponseDto = new CommentResponseDto(comments, user.getUsername());

        return new ApiResult<>(commentResponseDto, message);
    }

    //댓글 삭제
    public ApiResult<CommentResponseDto> deleteComment(Long blogId, Long commentId, User user) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                () -> new CustomException(NOT_FOUND_DATA)
        );
        Comments comments = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );

        if (user.getRole() == USER) {
            if (user.getUsername().equals(comments.getUsername())) {
                commentRepository.deleteById(commentId);
                return new ApiResult<>("삭제 성공", false);
            } else {
                throw new CustomException(FORBIDDEN_DATA);
            }
        } else {
            commentRepository.deleteById(commentId);
            return new ApiResult<>("삭제 성공", false);
        }
    }
}
