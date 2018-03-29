package com.resolutech.recipe.services;

import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();

        log.debug("slf4j getRecipee()");
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }
}
