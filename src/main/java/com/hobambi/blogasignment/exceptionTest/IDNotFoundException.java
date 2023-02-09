package com.hobambi.blogasignment.exceptionTest;

import lombok.Getter;

// 아이디가 없을 때 예외처리입니다.
@Getter
public class IDNotFoundException extends RuntimeException{
    private String message;
    private String trace;
    public IDNotFoundException() {
        this.message = "없는 아이디 입니다.";
    }

}
