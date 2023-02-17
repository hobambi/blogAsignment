package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.dto.BlogRequestDto;
import com.hobambi.blogasignment.dto.BlogResponseDto;
import com.hobambi.blogasignment.entity.Blog;


import com.hobambi.blogasignment.entity.Comments;
import com.hobambi.blogasignment.entity.User;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.exceptionTest.IDNotFoundException;
import com.hobambi.blogasignment.repository.BlogRepository;
import com.hobambi.blogasignment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.hobambi.blogasignment.entity.UserRoleEnum.USER;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final CheckToken checkToken;

    // 게시글 작성
    @Transactional
    public ApiResult<BlogResponseDto> createBlog(BlogRequestDto blogRequestDto, HttpServletRequest request) {

        User user = checkToken.checkToken(request);

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
            List<String> commentString = getCommentString(blog);

            blogResponseDtos.add(new BlogResponseDto(blog, commentString));
        }
        return new ApiResult<>(blogResponseDtos, "조회 성공");
    }

    // 선택한 게시글 조회
    @Transactional(readOnly = true)
    public ApiResult<BlogResponseDto> getOne(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        List<String> commentString = getCommentString(blog);
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog, commentString);
        return new ApiResult<>(blogResponseDto, "조회 성공");
    }

    // 게시글 수정
    @Transactional
    public ApiResult<BlogResponseDto> update(Long id, BlogRequestDto blogRequestDto, HttpServletRequest request) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        String message = "";
        User user = checkToken.checkToken(request);
        User find = blog.getUser();
        if (user.getRole()==USER) {
            if (user.getUsername().equals(find.getUsername())) {
                blog.update(blogRequestDto);
                message = "수정 성공";
            } else {
                new Exception("아무일도 안하는 익셥션 ㅠㅠ");
                return new ApiResult<>("자신의 글만 수정할 수 있습니다",true);
            }
        } else {
            blog.update(blogRequestDto);
        }

        List<String> commentString = getCommentString(blog);
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog, commentString);
        return new ApiResult<>(blogResponseDto, message);
    }

    // 게시글 삭제
    @Transactional
    public ApiResult<BlogResponseDto> deleteBlog(Long id, HttpServletRequest request) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IDNotFoundException());

        User user = checkToken.checkToken(request);
        User find = blog.getUser();

        if (user.getUsername().equals(find.getUsername())) {
            blogRepository.deleteById(id);
            return new ApiResult<>("삭제 성공",false);
        } else {

            new Exception("자신의 글만 삭제할 수 있습니다");
            return new ApiResult<>("삭제 실패",true);
        }

    }

    // 해당 게시글에 있는 댓글 가져오기
    private List<String> getCommentString(Blog blog) {
        List<String> commentString = new ArrayList<>();
        List<Comments> commentsList = commentRepository.findByBlog_Id(blog.getId());
        for (Comments c : commentsList) {
            commentString.add(c.getText());
        }
        return commentString;
    }
}
