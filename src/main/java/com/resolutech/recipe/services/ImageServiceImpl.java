package com.resolutech.recipe.services;

import com.resolutech.recipe.domain.Recipe;
import com.resolutech.recipe.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    //@Transactional
    public Mono<Recipe> saveImageFile(String id, MultipartFile file) {

        log.debug("Image file received :" + file.getName());

        return recipeReactiveRepository.findById(id).map( recipe -> {
            try {
                byte[] bytesPrim = file.getBytes();
                Byte[] content = new Byte[bytesPrim.length];
                Arrays.setAll(content, i -> bytesPrim[i]);
                recipe.setImage(content);

            } catch (IOException e) {
                log.error("Error saving image", e);
                throw new RuntimeException(e);
            }
            return recipeReactiveRepository.save(recipe).block();
        });
    }
}
