package com.resolutech.recipe.repositories;

import com.resolutech.recipe.domain.Category;
import com.resolutech.recipe.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String s);
}
