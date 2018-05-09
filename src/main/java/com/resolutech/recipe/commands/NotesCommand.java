package com.resolutech.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author Jean-Francois Larouche
 */
@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    private String id;
    private String recipeNotes;

}
