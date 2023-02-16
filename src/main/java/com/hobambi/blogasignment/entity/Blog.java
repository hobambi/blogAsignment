package com.hobambi.blogasignment.entity;

import com.hobambi.blogasignment.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// db테이블을 만들어요
@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped {
    @Id // 당신은 pk입니다
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 생성해주세요
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

//    @Column(nullable = false)
//    private String username;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comments> comments = new ArrayList<>();

//    public Blog(BlogRequestDto requestDto) {
//        this.title = requestDto.getTitle();
////        this.username = requestDto.getUsername();
//
//        this.contents = requestDto.getContents();
//    }

    public Blog(BlogRequestDto blogRequestDto, User user) {
        this.title = blogRequestDto.getTitle();
        this.contents = blogRequestDto.getContents();
        this.user = user;
//        this.username = user.getUsername();
    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
//        this.username = requestDto.getUsername();
//        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
    }

//    public String getUsername() {
//        return user.getUsername();
//    }
}
