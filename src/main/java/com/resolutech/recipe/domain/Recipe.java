package com.resolutech.recipe.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @Important When using Builder we have to tell Lombok to generate both No Args and All Args
 */
@Data
@EqualsAndHashCode(of={"id"})
@Slf4j
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Recipe {

    private String id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    private Difficulty difficulty;

    private String directions;

    private Byte[] image;

    private Notes notes;

    private Set<Ingredient> ingredients = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        log.trace("custom setNotes() called");
        if(notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {
        log.trace("custom addIngredient() called");
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}