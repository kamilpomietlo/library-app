package com.kamilpomietlo.libraryapp.controllers;

import com.kamilpomietlo.libraryapp.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/404error");

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/errors/400error");

        return modelAndView;
    }
}
