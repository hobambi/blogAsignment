package com.hobambi.blogasignment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UserRequestDto {

    @Size(min=4,max=10)
    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull(message = "이름을 입력해주세요.")
    private String username;

    @Size(min=8,max=15)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;
}
