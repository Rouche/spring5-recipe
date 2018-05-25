package com.resolutech.recipe.controllers;

import com.resolutech.recipe.domain.Category;
import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.domain.UnitOfMeasure;
import com.resolutech.recipe.repositories.CategoryRepository;
import com.resolutech.recipe.repositories.UnitOfMeasureRepository;
import com.resolutech.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Important section 122-125 tests with Mockito
 */
@WebFluxTest
@Ignore
public class IndexControllerTest {

    WebTestClient webTestClient;

    @Autowired
    ApplicationContext applicationContext;

    @MockBean
    RecipeService recipeService;

    @Mock
    Model model;

    @Mock
    CategoryRepository categoryRepository;
    @Mock
    UnitOfMeasureRepository uomRepository;

    @Autowired
    IndexController indexController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(categoryRepository, uomRepository, recipeService);

        when(categoryRepository.findByDescription(any(String.class))).thenReturn(Optional.of(new Category()));
        when(uomRepository.findByDescription(any(String.class))).thenReturn(Optional.of(new UnitOfMeasure()));

    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMVC = MockMvcBuilders.standaloneSetup(indexController).build();

        when(recipeService.getRecipes()).thenReturn(Flux.empty());

        mockMVC.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void getIndexPage() {

        //Given
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(Recipe.builder().id("1").build());
        recipes.add(Recipe.builder().id("2").build());

        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        //When
        String viewName = indexController.getIndexPage(model);

        //Then
        assertEquals("index", viewName);

        // @Important Always attributes matchers in parameters.
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        verify(model, times(3)).addAttribute(any(String.class), any(Object.class));

        verify(recipeService, times(1)).getRecipes();

        List<Recipe> recipeList = argumentCaptor.getValue();

        assertEquals(2, recipeList.size());
    }
}