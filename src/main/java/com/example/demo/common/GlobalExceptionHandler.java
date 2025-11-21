package com.example.demo.common;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        var errors = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        var errorMessage = new  ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(),errors);
        return ResponseEntity.unprocessableEntity().body(errorMessage);
    }


    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorMessage> httpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        var errorMessage = new  ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(),ex.getMessage());
        System.out.println(ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException ex) {
       var errorMessage = new  ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Internal Server Error");
       return ResponseEntity.internalServerError().body(errorMessage);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorMessage> handleResponseStatusException(ResponseStatusException ex) {
        int status = ex.getBody().getStatus();
        var errorMessage = new ErrorMessage(status,ex.getReason());
        return ResponseEntity.status(status).body(errorMessage);
    }

}
