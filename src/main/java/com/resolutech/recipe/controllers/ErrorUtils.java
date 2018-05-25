package com.resolutech.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;

@Slf4j
public class ErrorUtils {

    static String getErrorView(Model model, Exception exception, String customMsg) {
        log.error(customMsg);
        log.error(exception.getMessage());

        model.addAttribute("exception", exception);
        model.addAttribute("customMsg", customMsg);

        return "errors/4xxerror";
    }
}
