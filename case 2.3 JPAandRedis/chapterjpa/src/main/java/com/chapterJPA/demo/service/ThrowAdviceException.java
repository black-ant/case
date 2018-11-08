package com.chapterJPA.demo.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ThrowAdviceException {

    @ExceptionHandler(value=MyException.class)
    public ResponseEntity<Object> responseService(MyException exc){
        System.out.print(exc.getStackTrace());
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

}
