package com.hobambi.blogasignment.Controller;

import com.hobambi.blogasignment.dto.UserRequestDto;
import com.hobambi.blogasignment.dto.UserResponseDto;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public UserResponseDto signup(@RequestBody UserRequestDto userRequestDto){
        return userService.signup(userRequestDto);
    }

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        return userService.login(userRequestDto,response);
    }
}
