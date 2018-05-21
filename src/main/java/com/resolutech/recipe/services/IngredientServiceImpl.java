package com.resolutech.recipe.services;

import com.resolutech.recipe.commands.IngredientCommand;
import com.resolutech.recipe.converters.IngredientCommandToIngredient;
import com.resolutech.recipe.converters.IngredientToIngredientCommand;
import com.resolutech.recipe.domain.Ingredient;
import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.repositories.reactive.IngredientReactiveRepository;
import com.resolutech.recipe.repositories.reactive.RecipeReactiveRepository;
import com.resolutech.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @Author Jean-Francois Larouche
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    //private final RecipeRepository recipeRepository;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final IngredientReactiveRepository ingredientReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient,
            RecipeReactiveRepository recipeReactiveRepository, IngredientReactiveRepository ingredientReactiveRepository,
            UnitOfMeasureReactiveRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        //this.recipeRepository = recipeRepository;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.ingredientReactiveRepository = ingredientReactiveRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId);

        return recipeMono.flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .single()
                .map( ingredient -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
                    command.setRecipeId(recipeId);
                    return command;
                });

        //REFACTORED THIS a 2nd time
//        return recipeMono.map( recipe -> recipe.getIngredients().stream()
//                .filter(ingredient -> ingredient.getId().equals(ingredientId))
//                .findFirst())
//                .filter(Optional::isPresent)
//                .map( ingredient -> {
//                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient.get());
//                    return command;
//                } );


//        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
//
//        if (!recipeOptional.isPresent()){
//            throw new RuntimeException("recipe id not found. Id: " + recipeId);
//        }
//
//        Recipe recipe = recipeOptional.get();
//
//        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
//            .filter(ingredient -> ingredient.getId().equals(ingredientId))
//            .map( ingredient -> ingredientToIngredientCommand.convert(ingredient))
//            .findFirst();
//
//        if(!ingredientCommandOptional.isPresent()){
//            throw new RuntimeException("Ingredient id not found: " + ingredientId);
//        }
//
//        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
//        ingredientCommand.setRecipeId(recipeId);
//
//        return Mono.just(ingredientCommand);
    }

    @Override
    @Transactional
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        Recipe recipe = recipeReactiveRepository.findById(command.getRecipeId()).block();

        if(recipe == null){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return Mono.just(IngredientCommand.builder().build());
        } else {
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId()).block());

                if(ingredientFound.getUom() == null) {
                    throw new RuntimeException("UOM NOT FOUND");
                }
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                //ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
        }
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String id) {
        log.debug("Deleting ingredient: " + recipeId + ":" + id);

        //Ugly way to delete because its not a @DBRef
        Recipe recipe = recipeReactiveRepository.findById(recipeId).block();

        if(recipe != null){

            log.debug("found recipe");

            Optional<Ingredient> ingredient = recipe.getIngredients().stream()
                    .filter(i -> i.getId().equals(id))
                    .findFirst();

            if(ingredient.isPresent()){
                log.debug("found Ingredient");

                recipe.getIngredients().remove(ingredient.get());
                recipeReactiveRepository.save(recipe).block();
            }
        } else {
            log.debug("Recipe Id Not found. Id: " + recipeId);
        }
        return Mono.empty();
    }
}
