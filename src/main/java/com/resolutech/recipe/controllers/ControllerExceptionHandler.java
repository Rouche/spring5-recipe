package com.resolutech.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 *
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    // @Important web exchange
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public String handleNumberFormat(Exception exception, Model model) {

        log.error("Handling " + exception.getClass().getName());
        log.error(exception.getMessage());

        return ErrorUtils.getErrorView(model, exception, "400 Bad request");
    }
}