package com.hobambi.blogasignment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//회원가입과 로그인할 때 요청 하는 Dto 여기서 이름과 비밀번호 유효성 검사를 한다.
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
