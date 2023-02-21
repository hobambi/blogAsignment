package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.dto.CommentRequestDto;
import com.hobambi.blogasignment.dto.CommentResponseDto;
import com.hobambi.blogasignment.entity.Blog;
import com.hobambi.blogasignment.entity.Comments;
import com.hobambi.blogasignment.entity.User;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.repository.BlogRepository;
import com.hobambi.blogasignment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static com.hobambi.blogasignment.entity.UserRoleEnum.USER;

// 댓글 서비스인데 댓글 작성 밖에 없음..
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final CheckToken checkToken;

    // 댓글 작성
    public ApiResult<CommentResponseDto> createComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        User user = checkToken.checkToken(request);
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );

        Comments comments = commentRepository.saveAndFlush(new Comments(requestDto, blog));
        commentRepository.save(comments);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comments, user.getUsername());
        return new ApiResult<>(commentResponseDto, "댓글 생성");
    }

    // 댓글 수정
    public ApiResult<CommentResponseDto> updateComment(Long blogId, Long commentId, CommentRequestDto requestDto, HttpServletRequest request) {
        User user = checkToken.checkToken(request);
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );
        Comments comments = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("없는 댓글입니다")
        );

        String message = "";
        if (user.getRole() == USER) {
            if (user.getUsername().equals(comments.getUsername())) {
                comments.update(requestDto);
                message = "수정 성공";
            } else {
                message = "자신의 댓글만 수정할 수 있습니다.";
            }
        } else {
            comments.update(requestDto);
            message = "수정 성공";
        }
        CommentResponseDto commentResponseDto = new CommentResponseDto(comments, user.getUsername());

        return new ApiResult<>(commentResponseDto, message);
    }

    //댓글 삭제
    public ApiResult<CommentResponseDto> deleteComment(Long blogId, Long commentId, HttpServletRequest request) {
        User user = checkToken.checkToken(request);
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );
        Comments comments = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("없는 댓글입니다.")
        );

        if (user.getRole() == USER) {
            if (user.getUsername().equals(comments.getUsername())) {
                commentRepository.deleteById(commentId);
                return new ApiResult<>("삭제 성공", false);
            } else {
                return new ApiResult<>("삭제 실패", true);
            }
        } else {
            commentRepository.deleteById(commentId);
            return new ApiResult<>("삭제 성공", false);
        }
    }
}
