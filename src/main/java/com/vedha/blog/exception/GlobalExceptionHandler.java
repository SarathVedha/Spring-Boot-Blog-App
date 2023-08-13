package com.vedha.blog.exception;

import com.vedha.blog.dto.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFound(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest) {

        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), "ERR01", resourceNotFoundException.getMessage(), webRequest.getDescription(true));

        return new ResponseEntity<>(errorDetailsDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogCommentNotBelongException.class)
    public ResponseEntity<ErrorDetailsDto> handleBlogCommentNotBelong (BlogCommentNotBelongException blogCommentNotBelongException, WebRequest webRequest) {

        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), "ERR02", blogCommentNotBelongException.getMessage(), webRequest.getDescription(true));

        return new ResponseEntity<>(errorDetailsDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleRequestNotValidException(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(objectError -> {
            String field = ((FieldError) objectError).getField();
            String errorMessage = objectError.getDefaultMessage();

            errors.put(field, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetailsDto> handleAccessDeniedExceptions(AccessDeniedException exception, WebRequest webRequest) {

        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), "ERR03", exception.getMessage(), webRequest.getDescription(true));

        return new ResponseEntity<>(errorDetailsDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetailsDto> handleBlogApiExceptions(BlogApiException blogApiExceptions, WebRequest webRequest) {

        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), "ERR04", blogApiExceptions.getMessage(), webRequest.getDescription(true));

        return new ResponseEntity<>(errorDetailsDto, HttpStatus.BAD_REQUEST);
    }

    //Global Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleGlobalExceptions(Exception exception, WebRequest webRequest) {

        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), "ERR00", exception.getMessage(), webRequest.getDescription(true));

        return new ResponseEntity<>(errorDetailsDto, HttpStatus.BAD_REQUEST);
    }

}
