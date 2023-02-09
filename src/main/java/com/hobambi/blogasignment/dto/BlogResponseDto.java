package com.hobambi.blogasignment.dto;

import com.hobambi.blogasignment.entity.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;

// db로부터 받은 응답을 넣을 dto
@Getter
@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.username = blog.getUsername();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
    }

}
