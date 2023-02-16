package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.dto.CommentRequestDto;
import com.hobambi.blogasignment.dto.CommentResponseDto;
import com.hobambi.blogasignment.entity.Blog;
import com.hobambi.blogasignment.entity.Comments;
import com.hobambi.blogasignment.entity.User;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.jwt.JwtUtil;
import com.hobambi.blogasignment.repository.BlogRepository;
import com.hobambi.blogasignment.repository.CommentRepository;
import com.hobambi.blogasignment.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ApiResult<CommentResponseDto> createComment(CommentRequestDto requestDto, HttpServletRequest request) {
        User user = checkToken(request);

        Blog blog = blogRepository.findById(requestDto.getBlogid()).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 잘 못 되었습니다.")
        );

        Comments comments = commentRepository.saveAndFlush(new Comments(requestDto,blog));
        commentRepository.save(comments);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comments,user.getUsername());
        return new ApiResult<>(commentResponseDto,"댓글 생성");
    }

    private User checkToken(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        User user = null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("유효한 토큰이 아닙니다. headers Authorization 확인해보세요");
            }
            user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("감사합니다!")
            );
        } else {
            new IllegalArgumentException("당신은 토큰이 없네요ㅠ 로그인하세요");
        }
        return user;
    }

}
