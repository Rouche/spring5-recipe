package com.resolutech.recipe.controllers;

import com.resolutech.recipe.domain.Category;
import com.resolutech.recipe.domain.UnitOfMeasure;
import com.resolutech.recipe.repositories.CategoryRepository;
import com.resolutech.recipe.repositories.UnitOfMeasureRepository;
import com.resolutech.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model) {
        log.debug("showRecipe requested");

        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));

        return "recipe/show";
    }
}
