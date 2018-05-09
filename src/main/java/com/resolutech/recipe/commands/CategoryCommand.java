package com.resolutech.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author Jean-Francois Larouche
 */
@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
    private String id;
    private String description;
}
