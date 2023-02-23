package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.dto.UserRequestDto;
import com.hobambi.blogasignment.dto.UserResponseDto;
import com.hobambi.blogasignment.entity.User;
import com.hobambi.blogasignment.entity.UserRoleEnum;
import com.hobambi.blogasignment.exception.CustomException;
import com.hobambi.blogasignment.jwt.JwtUtil;
import com.hobambi.blogasignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

import static com.hobambi.blogasignment.exception.ErrorCode.*;

// 회원가입, 로그인 서비스(관리자는 따로 없고 모두 일반회원입니다)
@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    public UserResponseDto signup(@Valid UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword()); //비밀번호 암호화

       Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new CustomException(DUPLICATE_MEMBER);
        }
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDto.isAdmin()) { //관리자 계정으로 가입하려고 하면
            if (!userRequestDto.getAdminToken().equals(ADMIN_TOKEN)) { // 관리자 토큰 검사
                throw new CustomException(INVALID_AUTH_TOKEN); // 다르면 익셥션
            }
            role = UserRoleEnum.ADMIN; // 같으면 role = 관리자
        }

        User user = new User(userRequestDto, role);
        user.setPassword(password);
        userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return userResponseDto;
    }

    // 로그인
    public UserResponseDto login(UserRequestDto userRequestDto, HttpServletResponse response) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        //username 확인
        User user = (userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(EMPTY_CLIENT)
        ));

        //password 확인
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new CustomException(INVALIDATION_PASSWORD);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(user.getUsername(),user.getRole()));
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return userResponseDto;
    }
}
