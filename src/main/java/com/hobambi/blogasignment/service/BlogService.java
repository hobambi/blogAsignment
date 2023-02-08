package com.hobambi.blogasignment.service;

import com.hobambi.blogasignment.ErrorTest.JsonException;
import com.hobambi.blogasignment.dto.BlogRequestDto;
import com.hobambi.blogasignment.dto.BlogResponseDto;
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
    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return blogResponseDto;
    }

        @Transactional(readOnly = true)
    public List<Blog> getBlogs() {
        return blogRepository.findAllByOrderByModifiedAtDesc();
    }


//    @Transactional(readOnly = true)
//    public List<BlogResponseDto> getBlogs() {
//       List<BlogResponseDto> blogResponseDtos= blogRepository.findAllByOrderByModifiedAtDesc();
//
//        return blogResponseDtos;
//    }

    @Transactional(readOnly = true)
    public BlogResponseDto getOne(Long id){
        Blog blog = blogRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return blogResponseDto;
    }

    @Transactional
    public BlogResponseDto update(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (requestDto.getPassword().equals(blog.getPassword())) {
            blog.update(requestDto);
        }
        else {
            System.out.println("비밀번호 틀렸지롱");
        }
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return blogResponseDto;
    }



    //삭제 성공시 1반환, 비밀번호 틀리면 -1 반환
    @Transactional
    public int deleteBlog(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new JsonException("아이디가 존재하지 않습니다.")
        );
        if (requestDto.getPassword().equals(blog.getPassword()))
            blogRepository.deleteById(id);
        else {
            System.out.println("비밀번호 틀렸지롱");
            return -1;
        }
        return 1;
    }

    /*
        @Transactional
    public int deleteBlog(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (requestDto.getPassword().equals(blog.getPassword()))
            blogRepository.deleteById(id);
        else {
            System.out.println("비밀번호 틀렸지롱");
            return -1;
        }
        return 1;
    }
     */

}
