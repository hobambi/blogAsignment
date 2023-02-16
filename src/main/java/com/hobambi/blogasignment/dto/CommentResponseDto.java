package com.hobambi.blogasignment.dto;

import com.hobambi.blogasignment.entity.Comments;
import lombok.Getter;

import java.time.LocalDateTime;

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
