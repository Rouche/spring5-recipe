package com.resolutech.recipe.services;

import com.resolutech.recipe.commands.RecipeCommand;
import com.resolutech.recipe.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RecipeService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);
    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
    Mono<RecipeCommand> findCommandById(String l);
    Mono<Void> deleteById(String id);
}
