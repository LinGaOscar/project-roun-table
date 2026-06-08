package com.javaclass.roundtable.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(HttpServletRequest request, HttpServletResponse response,
                                                BusinessException ex) {
        log.warn("Business exception at {}: {}", request.getRequestURL(), ex.getMessage());
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", ex.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFoundException(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", "Page not found.");
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response,
                                        Exception ex) {
        log.error("Unhandled exception at " + request.getRequestURL(), ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", "An unexpected error occurred.");
        mav.addObject("url", request.getRequestURL());
        return mav;
    }
}
