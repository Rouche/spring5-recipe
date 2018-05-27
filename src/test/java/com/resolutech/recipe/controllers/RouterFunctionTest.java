package com.resolutech.recipe.controllers;

import com.resolutech.recipe.config.WebConfig;
import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

public class RouterFunctionTest {

    WebTestClient webTestClient;

    @Mock
    RecipeService recipeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        WebConfig webConfig = new WebConfig();

        RouterFunction<?> routerFunction = webConfig.routes(recipeService);

        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    public void testGetRecipes() {

        when(recipeService.getRecipes()).thenReturn(Flux.just(
                Recipe.builder().id("1L").build(), Recipe.builder().id("2L").build()));

        webTestClient.get()
            .uri("/api/recipes")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Recipe.class).hasSize(2);
        //.expectBody().jsonPath("$[0].id").equals("1L");
    }
}
