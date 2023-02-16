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

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final  CheckToken checkToken;

    @Transactional
    public ApiResult<CommentResponseDto> createComment(CommentRequestDto requestDto, HttpServletRequest request) {
        User user = checkToken.checkToken(request);
        Blog blog = blogRepository.findById(requestDto.getBlogid()).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 잘 못 되었습니다.")
        );

        Comments comments = commentRepository.saveAndFlush(new Comments(requestDto,blog));
        commentRepository.save(comments);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comments,user.getUsername());
        return new ApiResult<>(commentResponseDto,"댓글 생성");
    }

}
