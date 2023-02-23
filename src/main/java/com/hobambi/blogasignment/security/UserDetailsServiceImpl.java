package com.hobambi.blogasignment.security;

import com.hobambi.blogasignment.entity.User;
import com.hobambi.blogasignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        //postman에 아무것도 안 뜨고 인텔리제이에만 뜸 >> 클라이언트로 어떻게 보내줘야하나? custom으로 바꾸면 아예 안됨

        return new UserDetailsImpl(user, user.getUsername(),user.getRole());
    }

}