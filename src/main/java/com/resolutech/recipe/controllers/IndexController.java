package com.resolutech.recipe.controllers;

import com.resolutech.recipe.domain.Category;
import com.resolutech.recipe.domain.UnitOfMeasure;
import com.resolutech.recipe.repositories.CategoryRepository;
import com.resolutech.recipe.repositories.UnitOfMeasureRepository;
import com.resolutech.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository uomRepository;
    private RecipeService recipeService;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository uomRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.uomRepository = uomRepository;
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "index"})
    public String getIndexPage(Model model) {
        log.debug("Index requested");

        Optional<Category> category = categoryRepository.findByDescription("Italian");
        Optional<UnitOfMeasure> uom = uomRepository.findByDescription("Teaspoon");

        model.addAttribute("category", category.get());
        model.addAttribute("uom", uom.get());

        model.addAttribute("recipes", recipeService.getRecipes());

        log.debug("Cat ID: " + category.get().getId());
        log.debug("Uom ID: " + uom.get().getId());

        return "index";
    }
}
