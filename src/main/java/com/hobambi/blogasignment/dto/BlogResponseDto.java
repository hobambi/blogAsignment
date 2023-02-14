package com.hobambi.blogasignment.dto;

import com.hobambi.blogasignment.entity.Blog;
import com.hobambi.blogasignment.entity.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// db로부터 받은 응답을 넣을 dto
@Getter
@NoArgsConstructor
public class BlogResponseDto {
//    private Long id;
    private String username;

    private String title;
    private String contents;

    private LocalDateTime createAt;


    public BlogResponseDto(Blog blog) {
//        this.id = blog.getId();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
//        this.username = blog.getUsername();
        this.createAt = blog.getCreateAt();
    }

    public BlogResponseDto(Blog blog, String username) {
        this.username =username;
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.createAt = blog.getCreateAt();
    }
}
