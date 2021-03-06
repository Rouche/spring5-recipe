package com.resolutech.recipe.commands;

import lombok.*;

import java.math.BigDecimal;

/**
 * @Author Jean-Francois Larouche
 */
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
}
