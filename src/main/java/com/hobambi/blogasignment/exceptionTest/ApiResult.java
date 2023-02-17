package com.hobambi.blogasignment.exceptionTest;

import lombok.Getter;
import lombok.Setter;

// 결과를 포맷에 넣어줍니다
@Getter
@Setter
public class ApiResult<T> {
    private final T result;
    private final String message;

    public ApiResult(T result, String message) {
        this.result = result;
        this.message = message;
    }

    // 게시글 삭제할 때 그냥 성공 여부만 보내면 돼서 사용
    public ApiResult(String message, boolean error) {
        this.message = message;
        if (error)
            this.result = (T) "status : 400";
        else
            this.result = (T) "status : 200";
    }

}
