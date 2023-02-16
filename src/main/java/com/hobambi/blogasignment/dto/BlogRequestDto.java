package com.hobambi.blogasignment.dto;

import lombok.Getter;

// 게시글 작성하거나 수정할 때 요청하는 Dto
@Getter
public class BlogRequestDto {
   private String username;
   private String password;
   private String title;
   private String contents;
}

