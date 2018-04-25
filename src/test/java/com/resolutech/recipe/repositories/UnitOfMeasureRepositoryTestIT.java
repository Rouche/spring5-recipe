package com.resolutech.recipe.repositories;

import com.resolutech.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @Important section 127 Integration test with full spring context, convention is to use IT at the end of class name.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryTestIT {

    @Autowired
    UnitOfMeasureRepository uomRepository;

    @Before
    public void setUp() throws Exception {
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