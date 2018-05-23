package com.resolutech.recipe.services;

import com.resolutech.recipe.converters.RecipeCommandToRecipe;
import com.resolutech.recipe.converters.RecipeToRecipeCommand;
import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.exceptions.NotFoundException;
import com.resolutech.recipe.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = Recipe.builder().build();
        recipe.setId("1L");

        Mono<Recipe> recipeMono = Mono.just(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(recipeMono);

        Recipe recipeReturned = recipeService.findById("1L").block();

        assertNotNull("Null recipe returned", recipeReturned);

        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() throws Exception {

        Recipe recipe = Recipe.builder().build();

        when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(recipe));

        List<Recipe> recipes = recipeService.getRecipes().collectList().block();

        assertEquals(recipes.size(), 1);

        verify(recipeReactiveRepository, times(1)).findAll();
        verify(recipeReactiveRepository, never()).findById(anyString());
    }

    @Test
    public void testDeleteById() throws Exception {
        //Given
        String id = "2L";
        when(recipeReactiveRepository.deleteById(id)).thenReturn(Mono.empty());

        //When
        recipeService.deleteById(id);

        //then
        verify(recipeReactiveRepository, times(1)).deleteById(anyString());
    }
}