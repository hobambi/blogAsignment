package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.entity.User;
import com.hobambi.blogasignment.jwt.JwtUtil;
import com.hobambi.blogasignment.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class CheckToken {
    // 올바른 이용자인지 확인하는 메서드가 BlogService와 CommentService, 두 군데 쓰여 따로 빼줌

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    User checkToken(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        User user1 = null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("유효한 토큰이 아닙니다. headers Authorization 확인해보세요");
            }
            user1 = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("감사합니다!")
            );
        } else {
            new IllegalArgumentException("당신은 토큰이 없네요ㅠ 로그인하세요");
        }
        return user1;
    }
}
