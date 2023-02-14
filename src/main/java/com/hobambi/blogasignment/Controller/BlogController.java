package com.hobambi.blogasignment.Controller;

import com.hobambi.blogasignment.dto.BlogRequestDto;
import com.hobambi.blogasignment.dto.BlogResponseDto;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
//
//    @GetMapping("/")
//    public ModelAndView home() {
//        return new ModelAndView("index");
//    }

    // 게시글 작성
    @PostMapping("/api/blogs")
    public ApiResult<BlogResponseDto> createBlog(@RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.createBlog(requestDto,request);
    }

    // 전체 게시글 목록 조회
    @GetMapping("/api/blogs")
    public ApiResult<List<BlogResponseDto>> getBlogs() {
        return blogService.getBlogs();
    }

    // 선택한 게시글 조회
    @GetMapping("/api/blog/{id}")
    public ApiResult<BlogResponseDto> getOne(@PathVariable Long id) {
        return blogService.getOne(id);
    }

    // 선택한 게시글 수정
    @PutMapping("/api/blogs/{id}")
    public ApiResult<BlogResponseDto> updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.update(id, requestDto);
    }

    // 선택한 게시글 삭제
    @DeleteMapping("/api/blogs/{id}")
    public ApiResult<BlogResponseDto> deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.deleteBlog(id, requestDto);
    }

}
