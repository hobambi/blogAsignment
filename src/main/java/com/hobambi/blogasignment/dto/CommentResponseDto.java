package com.hobambi.blogasignment.dto;

import com.hobambi.blogasignment.entity.Comments;
import lombok.Getter;

import java.time.LocalDateTime;

// 댓글 작성 후 응답을 받아오는 Dto (request에서 작성자 이름이 추가 되었다)
@Getter
public class CommentResponseDto {
    private String username;
    private String comments;
    private LocalDateTime createAt;

    public CommentResponseDto(Comments comments, String username) {
        this.username = username;
        this.comments = comments.getComment();
        this.createAt = comments.getCreateAt();
    }
}
