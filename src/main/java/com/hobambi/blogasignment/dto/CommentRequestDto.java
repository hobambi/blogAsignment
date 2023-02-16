package com.hobambi.blogasignment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long blogid;
    private String comment;
}
