package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.dto.BlogRequestDto;
import com.hobambi.blogasignment.dto.BlogResponseDto;
import com.hobambi.blogasignment.dto.CommentResponseDto;
import com.hobambi.blogasignment.entity.Blog;


import com.hobambi.blogasignment.entity.Comments;
import com.hobambi.blogasignment.entity.User;
import com.hobambi.blogasignment.exception.CustomException;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.exceptionTest.IDNotFoundException;
import com.hobambi.blogasignment.repository.BlogRepository;
import com.hobambi.blogasignment.repository.CommentRepository;
import com.hobambi.blogasignment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.hobambi.blogasignment.entity.UserRoleEnum.USER;
import static com.hobambi.blogasignment.exception.ErrorCode.FORBIDDEN_DATA;
import static com.hobambi.blogasignment.exception.ErrorCode.NOT_FOUND_DATA;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;


    // 게시글 작성
    @Transactional
    public ApiResult<BlogResponseDto> createBlog(BlogRequestDto blogRequestDto, User user) {

        Blog blog = blogRepository.saveAndFlush(new Blog(blogRequestDto, user));
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog, user.getUsername());
        return new ApiResult<>(blogResponseDto, "게시글 성공");
    }

    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public ApiResult<List<BlogResponseDto>> getBlogs() {
        List<Blog> blogList = blogRepository.findAllByOrderByModifiedAtDesc();
        List<BlogResponseDto> blogResponseDtos = new ArrayList<>();
        for (Blog blog : blogList) {

            List<CommentResponseDto> commentString = getCommentDto(blog);
            blogResponseDtos.add(new BlogResponseDto(blog, commentString));
        }
        return new ApiResult<>(blogResponseDtos, "조회 성공");
    }

    // 선택한 게시글 조회
    @Transactional(readOnly = true)
    public ApiResult<BlogResponseDto> getBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_DATA));

        List<CommentResponseDto> commentString = getCommentDto(blog);
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog, commentString);
        return new ApiResult<>(blogResponseDto, "조회 성공");
    }

    // 게시글 수정
    @Transactional
    public ApiResult<BlogResponseDto> update(Long id, BlogRequestDto blogRequestDto, User user) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_DATA));
        String message = "";
        User find = blog.getUser();
        if (user.getRole() == USER) {
            if (user.getUsername().equals(find.getUsername())) {
                blog.update(blogRequestDto);
                message = "수정 성공";
            } else {
                throw new CustomException(FORBIDDEN_DATA);
            }
        } else {
            blog.update(blogRequestDto);
        }

        List<CommentResponseDto> commentString = getCommentDto(blog);
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog, commentString);
        return new ApiResult<>(blogResponseDto, message);
    }

    // 게시글 삭제
    @Transactional
    public ApiResult<BlogResponseDto> deleteBlog(Long id, User user) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_DATA));

        User find = blog.getUser();

        if (user.getRole() == USER) { // USER일때
            if (user.getUsername().equals(find.getUsername())) {
                blogRepository.deleteById(id);
                return new ApiResult<>("삭제 성공", false);
            } else {
                throw new CustomException(FORBIDDEN_DATA);
            }
        } else { //ADMIN 일때
            blogRepository.deleteById(id);
            return new ApiResult<>("삭제 성공", false);
        }
    }

    // 해당 게시글에 있는 댓글 가져오기
    private List<CommentResponseDto> getCommentDto(Blog blog) {
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<Comments> commentsList = commentRepository.findByBlog_Id(blog.getId());
        for (Comments c : commentsList) {
            CommentResponseDto temp = new CommentResponseDto(c);
            commentResponseDtoList.add(temp);
        }
        return commentResponseDtoList;
    }

}
