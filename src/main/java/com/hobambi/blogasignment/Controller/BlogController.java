package com.hobambi.blogasignment.Controller;

import com.hobambi.blogasignment.dto.BlogRequestDto;
import com.hobambi.blogasignment.dto.BlogResponseDto;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.security.UserDetailsImpl;
import com.hobambi.blogasignment.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    // 게시글 작성
    @PostMapping("/api/blogs")
    public ApiResult<BlogResponseDto> createBlog(@RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.createBlog(requestDto,userDetails.getUser());
    }

    // 전체 게시글 목록 조회
    @GetMapping("/api/blogs")
    public ApiResult<List<BlogResponseDto>> getBlogs() {
        return blogService.getBlogs();
    }

    // 선택한 게시글 조회
    @GetMapping("/api/blog/{id}")
    public ApiResult<BlogResponseDto> getBlog(@PathVariable Long id) {
        return blogService.getBlog(id);
    }

    // 선택한 게시글 수정
    @PutMapping("/api/blog/{id}")
    public ApiResult<BlogResponseDto> updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.update(id, requestDto, request);
    }

    // 선택한 게시글 삭제
    @DeleteMapping("/api/blog/{id}")
    public ApiResult<BlogResponseDto> deleteBlog(@PathVariable Long id,HttpServletRequest request) {
        return blogService.deleteBlog(id, request);
    }

}
