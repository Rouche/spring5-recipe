package com.resolutech.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class ErrorUtils {

    static ModelAndView getErrorView(Exception exception, String customMsg) {
        log.error(customMsg);
        log.error(exception.getMessage());

        ModelAndView view = new ModelAndView();
        view.addObject("exception", exception);
        view.addObject("customMsg", customMsg);
        view.setViewName("errors/4xxerror");

        return view;
    }
}
