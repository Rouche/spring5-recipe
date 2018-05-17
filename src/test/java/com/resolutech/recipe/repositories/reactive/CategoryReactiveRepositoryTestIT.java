package com.resolutech.recipe.repositories.reactive;

import com.resolutech.recipe.domain.Category;
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
public class CategoryReactiveRepositoryTestIT {

    public static final String EVERYTHING = "Everything";

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;


    @Before
    public void setUp() throws Exception {

        // @Important since mongo don't have Transaction, there's no rollback after each tests like with JPA
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSavecat() {

        //Given
        Category cat = new Category();
        cat.setDescription(EVERYTHING);

        //When
        Category catOfMeasure = categoryReactiveRepository.save(cat).block();

        //Then
        Category catOfMeasureFind = categoryReactiveRepository.findAll().blockFirst();
        assertEquals(catOfMeasureFind.getDescription(), catOfMeasure.getDescription());
        assertNotNull(catOfMeasureFind.getId());
    }

    @Test
    public void testFindByDescription() throws Exception {
        Category cat = new Category();
        cat.setDescription(EVERYTHING);

        categoryReactiveRepository.save(cat).block();

        Category fetchedcat = categoryReactiveRepository.findByDescription(EVERYTHING).block();

        assertEquals(EVERYTHING, fetchedcat.getDescription());

    }
}