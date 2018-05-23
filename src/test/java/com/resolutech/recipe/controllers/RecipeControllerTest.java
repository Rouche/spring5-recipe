package com.resolutech.recipe.controllers;

import com.resolutech.recipe.commands.RecipeCommand;
import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.exceptions.NotFoundException;
import com.resolutech.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);

        // @Important set the controller advice in the mock
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = Recipe.builder().build();
        recipe.setId("1");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeService.findById(anyString())).thenReturn(Mono.just(recipe));

        mockMvc.perform(get("/recipe/1/show"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/show"))
            .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testGetRecipeNotFound() throws Exception {

        when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);

        //Then
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("errors/4xxerror"));

    }

    @Test
    @Ignore
    public void testGetNumberFormat() throws Exception {

        //Then
        mockMvc.perform(get("/recipe/wefwef/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("errors/4xxerror"));

    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        mockMvc.perform(get("/recipe/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/recipeform"))
            .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        when(recipeService.saveRecipeCommand(any())).thenReturn(Mono.just(command));

        mockMvc.perform(post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("description", "some string")
            .param("directions", "some string direction")
    )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testPostNewRecipeFormValidationFail() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        when(recipeService.saveRecipeCommand(any())).thenReturn(Mono.just(command));

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(command));

        mockMvc.perform(get("/recipe/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/recipeform"))
            .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDelete() throws Exception {

        //Given
        Long id = 1L;

        //Then
        mockMvc.perform(get("/recipe/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }
}