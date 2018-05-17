package com.resolutech.recipe.repositories.reactive;

import com.resolutech.recipe.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @Important section 127 Integration test with full spring context, convention is to use IT at the end of class name.
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTestIT {

    public static final String EVERYTHING = "Everything";

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;


    @Before
    public void setUp() throws Exception {

        // @Important since mongo don't have Transaction, there's no rollback after each tests like with JPA
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSaverecipe() {

        //Given
        Recipe recipe = new Recipe();
        recipe.setDescription(EVERYTHING);

        //When
        Recipe recipeOfMeasure = recipeReactiveRepository.save(recipe).block();

        //Then
        Recipe recipeOfMeasureFind = recipeReactiveRepository.findAll().blockFirst();
        assertEquals(recipeOfMeasureFind.getDescription(), recipeOfMeasure.getDescription());
        assertNotNull(recipeOfMeasureFind.getId());
    }

}