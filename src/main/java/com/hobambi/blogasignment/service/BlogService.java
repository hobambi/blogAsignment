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
        User user = null;
        String token = jwtUtil.resolveToken(request);
        Claims claims;
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
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog, user.getUsername());
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
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return new ApiResult<>(blogResponseDto, "조회 성공");
    }

    // 게시글 수정
    @Transactional
    public ApiResult<BlogResponseDto> update(Long id, BlogRequestDto blogRequestDto, HttpServletRequest request) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        User user = null;
        String message = "";
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("유효한 토큰이 아닙니다. headers Authorization 확인해보세요");
            }
            user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("유효한 토큰은 있는데 username이 없네요?")
            );
        } else {
            new IllegalArgumentException("당신은 토큰이 없네요ㅠ 로그인하세요");
        }

        User find = blog.getUser();

        if (user.getUsername().equals(find.getUsername())) {
            blog.update(blogRequestDto);
            message = "수정 성공";
        }else {
            message = "원래는 익셉션이 되어야하는데 모르겠네";
            new Exception("자신의 글만 수정할 수 있습니다");

        }
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return new ApiResult<>(blogResponseDto, message);

    }


    // 게시글 삭제
    @Transactional
    public ApiResult<BlogResponseDto> deleteBlog(Long id, BlogRequestDto requestDto,HttpServletRequest request) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IDNotFoundException());
        User user = null;
        String message = "";
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("유효한 토큰이 아닙니다. headers Authorization 확인해보세요");
            }
            user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("유효한 토큰은 있는데 username이 없네요?")
            );
        } else {
            new IllegalArgumentException("당신은 토큰이 없네요ㅠ 로그인하세요");
        }

        User find = blog.getUser();

        if (user.getUsername().equals(find.getUsername())){
            blogRepository.deleteById(id);
            message ="삭제 성공";
        }else {
            message = "원래는 익셉션이 되어야하는데 모르겠네";
            new Exception("자신의 글만 삭제할 수 있습니다");
        }
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return new ApiResult<>(blogResponseDto, message);
    }

/*
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
 */

}
