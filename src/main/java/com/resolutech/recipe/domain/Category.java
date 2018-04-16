package com.resolutech.recipe.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @Important section 111-117
 * Always exclude Lazy collection with Lombok automatic generation
 */
@Data
@EqualsAndHashCode(exclude={"recipes"})
@ToString(exclude={"recipes"})
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
