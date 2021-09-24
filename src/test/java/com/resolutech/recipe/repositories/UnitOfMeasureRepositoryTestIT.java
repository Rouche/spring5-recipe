package com.resolutech.recipe.repositories;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.resolutech.recipe.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @Important section 127 Integration test with full spring context, convention is to use IT at the end of class name.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UnitOfMeasureRepositoryTestIT {

    @Autowired
    UnitOfMeasureRepository uomRepository;

    @BeforeEach
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

    }
}