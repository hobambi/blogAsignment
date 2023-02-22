package com.hobambi.blogasignment.Controller;

import com.hobambi.blogasignment.dto.CommentRequestDto;
import com.hobambi.blogasignment.dto.CommentResponseDto;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.security.UserDetailsImpl;
import com.hobambi.blogasignment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blog")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{id}/comment")
    public ApiResult<CommentResponseDto> createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id, requestDto, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/{blogId}/comment/{commentId}")
    public ApiResult<CommentResponseDto> updateComment(@PathVariable Long blogId,@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(blogId,commentId, requestDto,  userDetails.getUser());
    }

    @DeleteMapping("/{blogId}/comment/{commentId}")
    public ApiResult<CommentResponseDto> deleteComment(@PathVariable Long blogId,@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(blogId,commentId, userDetails.getUser());
    }

}
