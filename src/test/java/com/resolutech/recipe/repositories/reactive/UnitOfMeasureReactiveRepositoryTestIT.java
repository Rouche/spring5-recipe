package com.resolutech.recipe.repositories.reactive;

import com.resolutech.recipe.domain.UnitOfMeasure;
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
public class UnitOfMeasureReactiveRepositoryTestIT {

    public static final String EVERYTHING = "Everything";

    @Autowired
    UnitOfMeasureReactiveRepository uomReactiveRepository;

    @Before
    public void setUp() throws Exception {

        // @Important since mongo don't have Transaction, there's no rollback after each tests like with JPA
        uomReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSaveUOM() {

        //Given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(EVERYTHING);

        //When
        UnitOfMeasure uomOfMeasure = uomReactiveRepository.save(uom).block();

        //Then
        UnitOfMeasure uomOfMeasureFind = uomReactiveRepository.findAll().blockFirst();
        assertEquals(uomOfMeasureFind.getDescription(), uomOfMeasure.getDescription());
        assertNotNull(uomOfMeasureFind.getId());
    }

    @Test
    public void testFindByDescription() throws Exception {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(EVERYTHING);

        uomReactiveRepository.save(uom).block();

        UnitOfMeasure fetchedUOM = uomReactiveRepository.findByDescription(EVERYTHING).block();

        assertEquals(EVERYTHING, fetchedUOM.getDescription());

    }
}