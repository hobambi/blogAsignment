package com.hobambi.blogasignment.exceptionTest;

// 상태 결과에 띄울 에러 메세지 입니다.
public class ApiErrorResponse {
    private String message;
    public ApiErrorResponse( String message) {
        super();
        this.message=message;
    }
}
