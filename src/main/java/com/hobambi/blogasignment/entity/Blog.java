package com.hobambi.blogasignment.entity;

import com.hobambi.blogasignment.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 게시글 테이블 생성
@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 생성해주세요
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    // 게시글 삭제시 해당 게시글에 담긴 댓글도 같이 삭제 해주기 위해 Casecade를 걸어줌
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "blog")
    private List<Comments> comments = new ArrayList<>();

    public Blog(BlogRequestDto blogRequestDto, User user) {
        this.title = blogRequestDto.getTitle();
        this.contents = blogRequestDto.getContents();
        this.user = user;
    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}
