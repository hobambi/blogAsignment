package com.hobambi.blogasignment.ErrorTest;

import com.hobambi.blogasignment.dto.BlogResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorResponese {

    @ExceptionHandler(JsonException.class)
    public BlogResponseDto jsonException(JsonException jsonException){
        return BlogResponseDto.errorDto(jsonException.getMessage());
    }
}
