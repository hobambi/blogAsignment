package com.hobambi.blogasignment.dto;

import com.hobambi.blogasignment.entity.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String username;
    private String password;
    private String title;
    private String contents;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.username = blog.getUsername();
        this.password = blog.getPassword();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
    }


}
