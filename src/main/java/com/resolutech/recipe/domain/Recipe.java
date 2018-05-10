package com.resolutech.recipe.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document
public class Recipe {

    @Id
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

    @Builder.Default
    private Set<Ingredient> ingredients = new HashSet<>();

    @DBRef
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        log.trace("custom setNotes() called");
        if(notes != null) {
            this.notes = notes;
            //notes.setRecipe(this);
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {
        log.trace("custom addIngredient() called");
        //ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}