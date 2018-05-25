package com.resolutech.recipe.commands;

import lombok.*;

import javax.swing.*;
import javax.validation.constraints.*;
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

    private String id;
    private String recipeId;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    @NotNull
    private BigDecimal amount;

    @NotNull
    private UnitOfMeasureCommand uom;
}
