package com.hobambi.blogasignment.entity;

import com.hobambi.blogasignment.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 회원 테이블 생성
@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(UserRequestDto userRequestDto, UserRoleEnum role) {
        this.username = userRequestDto.getUsername();
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
