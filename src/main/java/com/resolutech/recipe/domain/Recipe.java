package com.resolutech.recipe.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @Important When using Builder we have to tell Lombok to generate both No Args and All Args
 */
@Data
@EqualsAndHashCode(of={"id"})
@Slf4j
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
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