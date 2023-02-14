package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.dto.UserRequestDto;
import com.hobambi.blogasignment.dto.UserResponseDto;
import com.hobambi.blogasignment.entity.User;
import com.hobambi.blogasignment.exceptionTest.ApiResult;
import com.hobambi.blogasignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;


    @Transactional
    public UserResponseDto signup(@Valid UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();

       Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        User user = new User(userRequestDto);
        userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return userResponseDto;
    }
}
