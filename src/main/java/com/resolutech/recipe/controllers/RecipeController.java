package com.resolutech.recipe.controllers;

import com.resolutech.recipe.commands.RecipeCommand;
import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.exceptions.NotFoundException;
import com.resolutech.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    private static final String RECIPE_RECIPEFORM = "recipe/recipeform";

    private RecipeService recipeService;

    private WebDataBinder webDataBinder;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        log.debug("showRecipe requested");

        Mono<Recipe> recipe = recipeService.findById(id);

        if(recipe == null) {
            throw new NotFoundException("Recipe not found for ID: [" + id + "]");
        }

        model.addAttribute("recipe", recipe);

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

        RecipeCommand recipeCommand = recipeService.findCommandById(id).block();
        model.addAttribute("recipe", recipeCommand);

        return RECIPE_RECIPEFORM;
    }

    // @Important bindingResult when going back in the form will be bound to the attibute "fields" in Thymeleaf
    @PostMapping("/recipe")
    //public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {
    public String saveOrUpdate(@ModelAttribute("recipe") RecipeCommand recipeCommand) {

        log.debug("saveOrUpdate");
        webDataBinder.validate();
        BindingResult bindingResult =  webDataBinder.getBindingResult();

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach( error -> {
                log.debug(error.toString());
            });

            return RECIPE_RECIPEFORM;
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand).block();

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/delete")
    public String delete(@PathVariable String id) {
        log.debug("delete");

        recipeService.deleteById(id).block();

        return "redirect:/";
    }

    // @Important single Exception handling that are thrown inside this Controller
    @ResponseStatus(HttpStatus.NOT_FOUND)
    // So so exception handling... TemplateInputException is imo too generic
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model) {
        return ErrorUtils.getErrorView(model, exception, "404 Not found");
    }
}
