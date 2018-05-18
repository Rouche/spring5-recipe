package com.resolutech.recipe.repositories.reactive;

import com.resolutech.recipe.domain.Ingredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IngredientReactiveRepository extends ReactiveMongoRepository<Ingredient, String> {


}
