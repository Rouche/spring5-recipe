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

    // @Important No circular with Mongo
    // private Recipe recipe;

    // @Important Because of the @Builder.Default, the id do not get initialized in this constructor.
    // Once you use Lombok Builder, you have no choice to use the Builder everywhere.
//    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
//        this.description = description;
//        this.amount = amount;
//        this.uom = uom;
//    }
//
//    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
//        this.description = description;
//        this.amount = amount;
//        this.uom = uom;
//        //this.recipe = recipe;
//    }

}
