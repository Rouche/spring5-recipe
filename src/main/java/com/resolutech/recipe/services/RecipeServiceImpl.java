package com.resolutech.recipe.services;

import com.resolutech.recipe.commands.RecipeCommand;
import com.resolutech.recipe.converters.RecipeCommandToRecipe;
import com.resolutech.recipe.converters.RecipeToRecipeCommand;
import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.exceptions.NotFoundException;
import com.resolutech.recipe.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("slf4j getRecipee()");
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(id);

        return recipeMono;
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {

        return findById(id).map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {

        return recipeReactiveRepository.save(recipeCommandToRecipe.convert(command))
            .map(recipeToRecipeCommand::convert);
    }

    @Override
    //@Transactional
    public Mono<Void> deleteById(String id) {
        return recipeReactiveRepository.deleteById(id);
    }
}
