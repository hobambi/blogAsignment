package com.hobambi.blogasignment.Controller;

import com.hobambi.blogasignment.dto.BlogRequestDto;
import com.hobambi.blogasignment.dto.BlogResponseDto;
import com.hobambi.blogasignment.entity.Blog;
import com.hobambi.blogasignment.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/")
    public ModelAndView home(){return new ModelAndView("index");}

    @PostMapping("/api/blogs")
    public Blog createBlog(@RequestBody BlogRequestDto requestDto){
        return blogService.createBlog(requestDto);
    }

    @GetMapping("/api/blogs")
    public List<Blog> getBlogs(){
        return blogService.getBlogs();
    }
    
    @GetMapping("/api/getone/{id}")
    public BlogResponseDto getOne(@PathVariable Long id){
        return blogService.getOne(id);
    }

    @PutMapping("/api/blogs/{id}")
    public BlogResponseDto updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto){
        return blogService.update(id,requestDto);
    }

    @DeleteMapping("/api/blogs/{id}")
    public int deleteBlog(@PathVariable Long id,@RequestBody BlogRequestDto requestDto){
        return blogService.deleteBlog(id,requestDto);
    }

}
