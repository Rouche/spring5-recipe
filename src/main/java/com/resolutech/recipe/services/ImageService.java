package com.resolutech.recipe.services;

import com.resolutech.recipe.domain.Recipe;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {

    Mono<Recipe> saveImageFile(String id, MultipartFile file);
}
