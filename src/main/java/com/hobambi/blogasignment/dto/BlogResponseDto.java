package com.hobambi.blogasignment.dto;

import com.hobambi.blogasignment.entity.Blog;
import com.hobambi.blogasignment.entity.Comments;
import com.hobambi.blogasignment.entity.Timestamped;
import com.hobambi.blogasignment.repository.CommentRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// db로부터 받은 응답을 넣을 dto
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
//        this.id = blog.getId();
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
