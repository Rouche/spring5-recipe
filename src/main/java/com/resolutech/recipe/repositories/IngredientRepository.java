package com.resolutech.recipe.repositories;

import com.resolutech.recipe.domain.Ingredient;
import com.resolutech.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
