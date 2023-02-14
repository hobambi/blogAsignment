package com.hobambi.blogasignment.entity;

import com.hobambi.blogasignment.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// db테이블을 만들어요
@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped {
    @Id // 당신은 pk입니다
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동으로 생성해주세요
    private Long id;

//    @Column(nullable = false)
//    private String username;
//
//    @Column(nullable = false)
//    private String password;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name="username")
    private User user;

    public Blog(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
//        this.username = requestDto.getUsername();
//        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
    }

    public Blog(BlogRequestDto blogRequestDto, User userid) {
        this.title = blogRequestDto.getTitle();
        this.contents = blogRequestDto.getContents();
        this.user =userid;
    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
//        this.username = requestDto.getUsername();
//        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
    }

    public String getUsername() {
        return user.getUsername();
    }
}
