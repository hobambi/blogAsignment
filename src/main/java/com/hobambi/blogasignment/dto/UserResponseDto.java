package com.hobambi.blogasignment.dto;

import com.hobambi.blogasignment.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 회원가입과 로그인한 응답을 보내주는 Dto
@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
