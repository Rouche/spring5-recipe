package com.resolutech.recipe.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @Important Lombok 1.16.22 : Private no-args constructor for @Data and @Value to enable deserialization frameworks (like Jackson)
 * No need to add {@link lombok.NoArgsConstructor} or {@link lombok.AllArgsConstructor}
 */
@Data
@EqualsAndHashCode(of={"id"})
@Slf4j
@Entity
@Builder
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
    @Builder.Default
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "RECIPE_CATEGORY",
        joinColumns = @JoinColumn(name = "RECIPE_ID"), inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    @Builder.Default
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