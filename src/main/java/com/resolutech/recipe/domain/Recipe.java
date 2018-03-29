package com.resolutech.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
@Data
@EqualsAndHashCode(of={"id"})
@Slf4j
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    private String directions;

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "RECIPE_CATEGORY",
        joinColumns = @JoinColumn(name = "RECIPE_ID"), inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        log.debug("custom setNotes() called");
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        log.debug("custom addIngredient() called");
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}