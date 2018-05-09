package com.resolutech.recipe.controllers;

import com.resolutech.recipe.commands.RecipeCommand;
import com.resolutech.recipe.exceptions.NotFoundException;
import com.resolutech.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    private static final String RECIPE_RECIPEFORM = "recipe/recipeform";

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        log.debug("showRecipe requested");

        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        log.debug("newRecipe");

        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/update")
    public String update(@PathVariable String id, Model model) {
        log.debug("update");

        RecipeCommand recipeCommand = recipeService.findCommandById(id);
        model.addAttribute("recipe", recipeCommand);

        return RECIPE_RECIPEFORM;
    }

    // @Important bindingResult when going back in the form will be bound to the attibute "fields" in Thymeleaf
    @PostMapping("/recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {
        log.debug("saveOrUpdate");

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach( error -> {
                log.debug(error.toString());
            });

            return RECIPE_RECIPEFORM;
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/delete")
    public String delete(@PathVariable String id) {
        log.debug("delete");

        recipeService.deleteById(id);

        return "redirect:/";
    }

    // @Important single Exception handling
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        return ErrorUtils.getErrorView(exception, "404 Not found");
    }
}
