package com.resolutech.recipe.services;

import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {

        log.debug("Image file received :" + file.getName());

        Recipe recipe = recipeRepository.findById(id).get();

        try {
            byte[] bytesPrim = file.getBytes();
            Byte[] content = new Byte[bytesPrim.length];
            Arrays.setAll(content, i -> bytesPrim[i]);
            recipe.setImage(content);

            Recipe savedRecipe = recipeRepository.save(recipe);


        } catch (IOException e) {
            log.error("Error saving image", e);
        }
    }
}
