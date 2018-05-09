package com.resolutech.recipe.services;

import com.resolutech.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(String id);
}
