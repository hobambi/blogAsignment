package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.dto.BlogRequestDto;
import com.hobambi.blogasignment.dto.BlogResponseDto;
import com.hobambi.blogasignment.entity.Blog;


import com.hobambi.blogasignment.entity.User;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.exceptionTest.IDNotFoundException;
import com.hobambi.blogasignment.jwt.JwtUtil;
import com.hobambi.blogasignment.repository.BlogRepository;
import com.hobambi.blogasignment.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 게시글 작성
    @Transactional
    public ApiResult<BlogResponseDto> createBlog(BlogRequestDto blogRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        User user = null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("유효한 토큰이 아닙니다. 당신은 해커입니까???");
            }
            user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("당신은 어떻게 유효한 토큰은 있는데 username이 없죠??")
            );

        } else {
            new IllegalArgumentException("당신은 토큰이 없네요ㅠ 로그인하세요");
        }

        Blog blog = blogRepository.saveAndFlush(new Blog(blogRequestDto, user));
        blogRepository.save(blog);
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog,user.getUsername());
        return new ApiResult<>(blogResponseDto, "게시글 성공");
    }

    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public ApiResult<List<BlogResponseDto>> getBlogs() {
        List<Blog> blogList = blogRepository.findAllByOrderByModifiedAtDesc();
        List<BlogResponseDto> blogResponseDtos = new ArrayList<>();
        for (Blog blog : blogList) {
            blogResponseDtos.add(new BlogResponseDto(blog));
        }
        return new ApiResult<>(blogResponseDtos, "조회 성공");
    }

    // 선택한 게시글 조회
    @Transactional(readOnly = true)
    public ApiResult<BlogResponseDto> getOne(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return new ApiResult<>(blogResponseDto, "조회 성공");
    }

    // 게시글 수정
    @Transactional
    public ApiResult<BlogResponseDto> update(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        String message = checkPassword(requestDto, blog, "수정");
        if (message.equals("수정 성공")) {
            blog.update(requestDto);
        }
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return new ApiResult<>(blogResponseDto, message);
    }

    // 게시글 삭제
    @Transactional
    public ApiResult<BlogResponseDto> deleteBlog(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IDNotFoundException());
        String message = checkPassword(requestDto, blog, "삭제");
        if (message.equals("삭제 성공")) {
            blogRepository.deleteById(id);
        }
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return new ApiResult<>(blogResponseDto, message);
    }

    // 비밀번호 확인 메서드
    String checkPassword(BlogRequestDto blogRequestDto, Blog blog, String what) {
        String message = "";
//        if (blogRequestDto.getPassword().equals(blog.getPassword())) {
//            message = what + " 성공";
//        } else {
//            message = "비밀번호가 틀렸습니다.";
//        }

        return message;
    }

}
