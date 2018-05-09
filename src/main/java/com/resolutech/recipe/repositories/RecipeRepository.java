package com.resolutech.recipe.repositories;

import com.resolutech.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {

}
