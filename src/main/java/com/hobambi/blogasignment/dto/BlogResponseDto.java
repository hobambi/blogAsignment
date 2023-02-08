package com.hobambi.blogasignment.dto;

import com.hobambi.blogasignment.entity.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
//@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String username;
    private String password;
    private String title;
    private String contents;

    private String message;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.username = blog.getUsername();
        this.password = blog.getPassword();
        this.title = blog.getTitle();
        this.contents = blog.getContents();

    }
    public BlogResponseDto(String message){
        this.message = message;
    }

    public static BlogResponseDto errorDto(String message) {
        return new BlogResponseDto(message);
    }

//    public BlogResponseDto(List<Blog>blogList){
////        this.id = blogList.getId();
////        this.username = blog.getUsername();
////        this.password = blog.getPassword();
////        this.title = blog.getTitle();
////        this.contents = blog.getContents();
//    }


}
