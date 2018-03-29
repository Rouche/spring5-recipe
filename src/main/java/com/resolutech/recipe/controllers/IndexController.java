package com.resolutech.recipe.controllers;

import com.resolutech.recipe.domain.Category;
import com.resolutech.recipe.domain.UnitOfMeasure;
import com.resolutech.recipe.repositories.CategoryRepository;
import com.resolutech.recipe.repositories.UnitOfMeasureRepository;
import com.resolutech.recipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeService recipeService;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "index"})
    public String getIndexPage(Model model) {
        System.out.println("Index requested");

        Optional<Category> category = categoryRepository.findByDescription("Italian");
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");

        model.addAttribute("category", category.get());
        model.addAttribute("uom", unitOfMeasure.get());

        model.addAttribute("recipes", recipeService.getRecipes());

        System.out.println("Cat ID: " + category.get().getId());
        System.out.println("Uom ID: " + unitOfMeasure.get().getId());

        return "index";
    }
}
