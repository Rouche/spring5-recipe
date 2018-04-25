package com.resolutech.recipe.controllers;

import com.resolutech.recipe.commands.IngredientCommand;
import com.resolutech.recipe.commands.RecipeCommand;
import com.resolutech.recipe.commands.UnitOfMeasureCommand;
import com.resolutech.recipe.services.IngredientService;
import com.resolutech.recipe.services.RecipeService;
import com.resolutech.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredients(@PathVariable String recipeId, @PathVariable String id,  Model model){
        log.debug("Getting ingredient for recipe id: " + recipeId + " id: " + id);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){
        log.debug("New ingredient for recipe id: " + recipeId);

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeCommand.getId()));
        model.addAttribute("ingredient", ingredientCommand);

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String update(@PathVariable String recipeId, @PathVariable String id,  Model model){
        log.debug("Getting ingredient for recipe id: " + recipeId + " id: " + id);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@PathVariable String recipeId, @ModelAttribute IngredientCommand ingredientCommand) {
        log.debug("saveOrUpdate ingredient id:" + ingredientCommand.getId());

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteById(@PathVariable String recipeId, @PathVariable String id,  Model model){
        log.debug("Delete ingredient for recipe id: " + recipeId + " id: " + id);

        ingredientService.deleteById(Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/show";
    }
}

