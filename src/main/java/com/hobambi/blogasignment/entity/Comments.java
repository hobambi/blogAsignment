package com.hobambi.blogasignment.entity;

import com.hobambi.blogasignment.dto.CommentRequestDto;
import com.hobambi.blogasignment.repository.CommentRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

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

    //작성자
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
}
