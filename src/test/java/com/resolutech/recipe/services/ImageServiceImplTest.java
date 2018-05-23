package com.resolutech.recipe.services;

import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    ImageService imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeReactiveRepository);
    }

    // @Important test with multipart service
    @Test
    public void saveImageFile() throws IOException {
        //Given
        String id = "1L";
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt", "text/plain", "My mock".getBytes());

        Recipe recipe = Recipe.builder().build();
        recipe.setId(id);
        Mono<Recipe> recipeMono = Mono.just(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(recipeMono);
        when(recipeReactiveRepository.save(any(Recipe.class))).thenReturn(recipeMono);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //When
        imageService.saveImageFile(id, multipartFile).block();

        //Then
        verify(recipeReactiveRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}