package com.resolutech.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

/**
 * @Important section 111-117
 * Always exclude Lazy collection with Lombok automatic generation
 */
@Data
@EqualsAndHashCode(exclude={"recipes"})
@ToString(exclude={"recipes"})
public class Category {

    private String id;

    private String description;

    private Set<Recipe> recipes;

}
