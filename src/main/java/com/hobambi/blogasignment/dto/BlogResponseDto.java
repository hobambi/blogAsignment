package com.hobambi.blogasignment.dto;

import com.hobambi.blogasignment.entity.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// 게시글 작성, 수정시 응답을 보내주는 Dto
@Getter
@NoArgsConstructor
public class BlogResponseDto {
//    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private List<String> comments;


    public BlogResponseDto(Blog blog, List<String> comments) {
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.username = blog.getUser().getUsername();
        this.createAt = blog.getCreateAt();
        this.comments = comments;

    }

    public BlogResponseDto(Blog blog, String username) {
        this.username =username;
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.createAt = blog.getCreateAt();
    }
}
