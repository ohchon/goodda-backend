package com.finalproject.gooddabackend.exception;

import com.finalproject.gooddabackend.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 클라이언트 요청의 모든 예외처리를 찾음
@RestControllerAdvice
public class RestApiExceptionHandler {
    // 익셉션 핸들러의 밸류 값은 CustomErrorException의 클래스
    @ExceptionHandler(value = {CustomErrorException.class})
    //  ResponeseEntity의 오브젝트 빈에 RuntimeException 예외를 건다.
    public ResponseEntity<Object> handleApiRequestException(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto("failed",ex.getMessage());

        return new ResponseEntity<>(
                restApiException,
                HttpStatus.OK
        );
    }
}