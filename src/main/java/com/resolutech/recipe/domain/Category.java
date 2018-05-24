package com.resolutech.recipe.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Important section 221 mongo
 * Always exclude Lazy collection with Lombok automatic generation
 */
@Data
//@EqualsAndHashCode(exclude={"recipes"})
//@ToString(exclude={"recipes"})
@Document
public class Category {

    @Id
    private String id;

    private String description;

// https://jira.spring.io/browse/DATAMONGO-1584
//    @DBRef
//    private Set<Recipe> recipes;

}
