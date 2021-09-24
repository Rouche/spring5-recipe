package com.resolutech.recipe.domain;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        this.category = new Category();
    }

    @Test
    public void getId() {
        Long id = 4L;
        this.category.setId(id);

        assertEquals(id, category.getId());
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}