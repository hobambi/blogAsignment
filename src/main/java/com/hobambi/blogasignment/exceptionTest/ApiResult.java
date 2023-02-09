package com.hobambi.blogasignment.exceptionTest;

import lombok.Getter;
import lombok.Setter;

// 결과를 포맷에 넣어줍니다
@Getter
@Setter
public class ApiResult<T>{
    private final T result;
    private final String message;

    public ApiResult(T result, String message) {
        this.result = result;
        this.message = message;
    }

}
