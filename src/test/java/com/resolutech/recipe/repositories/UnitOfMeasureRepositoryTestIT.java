package com.resolutech.recipe.repositories;

import com.resolutech.recipe.bootstrap.RecipeBootstrap;
import com.resolutech.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @Important section 127 Integration test with full spring context, convention is to use IT at the end of class name.
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryTestIT {

    @Autowired
    UnitOfMeasureRepository uomRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    RecipeBootstrap recipeBootstrap;

    @Before
    public void setUp() throws Exception {

        // @Important since mongo don't have Transaction, there's no rollback after each tests like with JPA
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
        uomRepository.deleteAll();


        recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, uomRepository);
        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
    @DirtiesContext
    public void findByDescription() {
        Optional<UnitOfMeasure> uomOfMeasure = uomRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", uomOfMeasure.get().getDescription());

    }

    @Test
    public void findByDescriptionCup() {
        Optional<UnitOfMeasure> uomOfMeasure = uomRepository.findByDescription("Cup");

        assertEquals("Cup", uomOfMeasure.get().getDescription());

    }}