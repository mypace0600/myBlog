package com.project.myBlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex){
        if (ex instanceof org.springframework.web.bind.MissingServletRequestParameterException) {
            return "common/error/error400";
        } else if (ex instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            return "common/error/error405";
        } else {
            System.out.println(ex.getMessage());
            return "common/error/error500";
        }
    }
}
