package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.dto.BlogRequestDto;
import com.hobambi.blogasignment.entity.Blog;

import com.hobambi.blogasignment.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;


    @Transactional
    public Blog createBlog(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        return blog;
    }

    @Transactional
    public List<Blog> getBlogs() {
        return blogRepository.findAllByOrderByModifiedAtDesc();
    }


}
