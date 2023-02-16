package com.hobambi.blogasignment.Controller;

import com.hobambi.blogasignment.dto.UserRequestDto;
import com.hobambi.blogasignment.dto.UserResponseDto;
import com.hobambi.blogasignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public UserResponseDto signup(@RequestBody UserRequestDto userRequestDto){
        return userService.signup(userRequestDto);
    }

    //로그인
    @PostMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        return userService.login(userRequestDto,response);
    }
}
