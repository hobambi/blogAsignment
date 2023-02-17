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

// 댓글 서비스인데 댓글 작성 밖에 없음..
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final  CheckToken checkToken;

    // 댓글 작성
    public ApiResult<CommentResponseDto> createComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        User user = checkToken.checkToken(request);
        Blog blog = blogRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 잘 못 되었습니다.")
        );

        Comments comments = commentRepository.saveAndFlush(new Comments(requestDto,blog));
        commentRepository.save(comments);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comments,user.getUsername());
        return new ApiResult<>(commentResponseDto,"댓글 생성");
    }

    public ApiResult<CommentResponseDto> updateComment(Long blogId,Long commentId, CommentRequestDto requestDto, HttpServletRequest request) {
        User user = checkToken.checkToken(request);
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                ()-> new IllegalArgumentException("잘못된 접근입니다 : 게시글")
        );
        Comments comments = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("잘못된 접근입니다 : 댓글")
        );

        String message ="";
        if(user.getUsername().equals(comments.getUsername())){
            comments.update(requestDto);
            message = "수정 성공";
        }else {
            message = "자신의 댓글만 수정할 수 있습니다.";
        }
        CommentResponseDto commentResponseDto = new CommentResponseDto(comments,user.getUsername());
        return new ApiResult<>(commentResponseDto,message);
    }

    public ApiResult<CommentResponseDto> deleteComment(Long blogId,Long commentId,HttpServletRequest request) {
        User user = checkToken.checkToken(request);
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                ()-> new IllegalArgumentException("잘못된 접근입니다 : 게시글")
        );
        Comments comments = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("잘못된 접근입니다 : 댓글")
        );

        String message ="";
        if(user.getUsername().equals(comments.getUsername())){
            commentRepository.deleteById(commentId);
            message = "삭제 성공";
        }else {
            message = "자신의 댓글만 삭제할 수 있습니다.";
        }
        return new ApiResult<>(message);
    }



}
