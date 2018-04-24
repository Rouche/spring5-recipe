package com.resolutech.recipe.services;

import com.resolutech.recipe.commands.RecipeCommand;
import com.resolutech.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
