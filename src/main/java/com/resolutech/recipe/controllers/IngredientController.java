package com.resolutech.recipe.controllers;

import com.resolutech.recipe.commands.IngredientCommand;
import com.resolutech.recipe.commands.RecipeCommand;
import com.resolutech.recipe.commands.UnitOfMeasureCommand;
import com.resolutech.recipe.exceptions.NotFoundException;
import com.resolutech.recipe.services.IngredientService;
import com.resolutech.recipe.services.RecipeService;
import com.resolutech.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class IngredientController {

    private static final String INGREDIENT_FORM = "recipe/ingredient/ingredientform";

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;
    private WebDataBinder webDataBinder;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    // @Important multiple data binder on post. Have to be specific.
    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        // use command object to avoid lazy load errors in Thymeleaf.
        Mono<RecipeCommand> recipe = recipeService.findCommandById(recipeId);
        model.addAttribute("recipe", recipe);

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredients(@PathVariable String recipeId, @PathVariable String id,  Model model){
        log.debug("Getting ingredient for recipe id: " + recipeId + " id: " + id);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){
        log.debug("New ingredient for recipe id: " + recipeId);

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = IngredientCommand.builder().build();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        return INGREDIENT_FORM;
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String update(@PathVariable String recipeId, @PathVariable String id,  Model model){
        log.debug("Getting ingredient for recipe id: " + recipeId + " id: " + id);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id).block());

        return INGREDIENT_FORM;
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute("ingredient") IngredientCommand ingredientCommand, @PathVariable String recipeId,  Model model) {
        log.debug("saveOrUpdate ingredient id:" + ingredientCommand.getId());

        webDataBinder.validate();
        BindingResult bindingResult =  webDataBinder.getBindingResult();

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach( error -> {
                log.debug(error.toString());
            });

            return INGREDIENT_FORM;
        }

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand).block();

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteById(@PathVariable String recipeId, @PathVariable String id,  Model model){
        log.debug("Delete ingredient for recipe id: " + recipeId + " id: " + id);

        ingredientService.deleteById(recipeId, id).block();

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    // @Important always populated on every requests
    @ModelAttribute("uomList")
    public Flux<UnitOfMeasureCommand> populateUomList() {
        return unitOfMeasureService.listAllUoms();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model) {
        return ErrorUtils.getErrorView(model, exception, "404 Not found");
    }
}

