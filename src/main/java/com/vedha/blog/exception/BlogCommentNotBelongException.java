package com.vedha.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlogCommentNotBelongException extends RuntimeException{

    public BlogCommentNotBelongException(String message) {
        super(message);
    }
}
