package com.hobambi.blogasignment.entity;

import com.hobambi.blogasignment.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 댓글 테이블 생성
@Getter
@Entity
@NoArgsConstructor
public class Comments extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //댓글 내용
    @Column(nullable = false)
    private String comment;

    //작성자 - User과의 연관관계를 걸까하다가, 복잡하기도 하고, token에서 바로 username을 가져올 수 있어서 안함
    @Column(nullable = false)
    private String username;

    //게시글 id
    @ManyToOne
    @JoinColumn(name="blogId")
    private Blog blog;

    public String getText(){
        return comment;
    }

    public Comments(CommentRequestDto commentRequestDto, Blog blog) {
        this.comment = commentRequestDto.getComment();
        this.username = blog.getUser().getUsername();
        this.blog = blog;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }
}
