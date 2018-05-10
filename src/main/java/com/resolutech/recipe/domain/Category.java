package com.resolutech.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * @Important section 221 mongo
 * Always exclude Lazy collection with Lombok automatic generation
 */
@Data
@EqualsAndHashCode(exclude={"recipes"})
@ToString(exclude={"recipes"})
@Document
public class Category {

    @Id
    private String id;

    private String description;

    @DBRef
    private Set<Recipe> recipes;

}
