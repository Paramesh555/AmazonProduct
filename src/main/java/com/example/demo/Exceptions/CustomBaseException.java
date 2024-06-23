package com.example.demo.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class CustomBaseException extends RuntimeException{
    private HttpStatus httpStatus;
    private SimpleResponse simpleResponse;
}
