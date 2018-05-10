package com.resolutech.recipe.services;

import com.resolutech.recipe.commands.IngredientCommand;
import com.resolutech.recipe.converters.IngredientCommandToIngredient;
import com.resolutech.recipe.converters.IngredientToIngredientCommand;
import com.resolutech.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.resolutech.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.resolutech.recipe.domain.Ingredient;
import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.repositories.IngredientRepository;
import com.resolutech.recipe.repositories.RecipeRepository;
import com.resolutech.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    IngredientRepository ingredientRepository;

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
                ingredientRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() throws Exception {
    }

    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() throws Exception {
        //given
        Recipe recipe = Recipe.builder().build();
        recipe.setId("1L");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1L");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2L");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3L");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1L", "3L");

        //when
        assertEquals("3L", ingredientCommand.getId());
        //assertEquals("1L", ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyString());
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3L");
        command.setRecipeId("2L");

        Optional<Recipe> recipeOptional = Optional.of(Recipe.builder().build());

        Recipe savedRecipe = Recipe.builder().build();
        savedRecipe.addIngredient(Ingredient.builder().id("3L").build());

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals("3L", savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void deleteById() {
        //Given
        String id = "2L";

        //When
        ingredientService.deleteById(id);

        //Then
        verify(ingredientRepository, times(1)).deleteById(id);
    }
}