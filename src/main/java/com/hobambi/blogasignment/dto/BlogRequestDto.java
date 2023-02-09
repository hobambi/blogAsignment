package com.hobambi.blogasignment.dto;

import lombok.Getter;

// db로 정보를 요청하는 dto
@Getter
public class BlogRequestDto {
   private String username;
   private String password;
   private String title;
   private String contents;
}

