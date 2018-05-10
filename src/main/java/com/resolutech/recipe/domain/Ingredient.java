package com.resolutech.recipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(of={"id", "description"})
//@ToString(exclude={"recipe"})
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Ingredient {

    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String description;

    private BigDecimal amount;

    @DBRef
    private UnitOfMeasure uom;

    //No circular with Mongo
    //private Recipe recipe;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        //this.recipe = recipe;
    }

}
