package com.resolutech.recipe.services;

import com.resolutech.recipe.commands.IngredientCommand;
import com.resolutech.recipe.commands.UnitOfMeasureCommand;
import com.resolutech.recipe.converters.IngredientCommandToIngredient;
import com.resolutech.recipe.converters.IngredientToIngredientCommand;
import com.resolutech.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.resolutech.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.resolutech.recipe.domain.Ingredient;
import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.domain.UnitOfMeasure;
import com.resolutech.recipe.repositories.IngredientRepository;
import com.resolutech.recipe.repositories.RecipeRepository;
import com.resolutech.recipe.repositories.UnitOfMeasureRepository;
import com.resolutech.recipe.repositories.reactive.IngredientReactiveRepository;
import com.resolutech.recipe.repositories.reactive.RecipeReactiveRepository;
import com.resolutech.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    //UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    IngredientReactiveRepository ingredientReactiveRepository;
    //IngredientRepository ingredientRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository,
                recipeReactiveRepository, ingredientReactiveRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() throws Exception {
    }

    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() throws Exception {
        //given
        Recipe recipe = Recipe.builder().build();
        recipe.setId("1L");

        Ingredient ingredient1 = Ingredient.builder().build();
        ingredient1.setId("1L");

        Ingredient ingredient2 = Ingredient.builder().build();
        ingredient2.setId("2L");

        Ingredient ingredient3 = Ingredient.builder().build();
        ingredient3.setId("3L");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Mono<Recipe> recipeMono = Mono.just(recipe);

        when(recipeReactiveRepository.findById("1L")).thenReturn(recipeMono);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1L", "3L").block();

        //when
        assertEquals("3L", ingredientCommand.getId());
        //assertEquals("1L", ingredientCommand.getRecipeId());
        verify(recipeReactiveRepository, times(1)).findById("1L");
        assertNotNull(ingredientCommand);
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = IngredientCommand.builder().build();
        command.setId("3L");
        command.setRecipeId("2L");
        command.setUom(new UnitOfMeasureCommand());
        command.getUom().setId("1234");


        Optional<Recipe> recipeOptional = Optional.of(Recipe.builder().build());

        Recipe savedRecipe = Recipe.builder().build();
        savedRecipe.addIngredient(Ingredient.builder().id("3L").build());

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //then
        assertEquals("3L", savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void deleteById() {
        //given
        Recipe recipe = Recipe.builder().build();
        Ingredient ingredient = Ingredient.builder().build();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById("1", "3");

        //then
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}