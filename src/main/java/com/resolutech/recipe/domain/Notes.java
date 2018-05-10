package com.resolutech.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * @Author Jean-Francois Larouche
 */
@Data
@EqualsAndHashCode(of={"id"})
@ToString(exclude={"recipe"})
public class Notes {

    @Id
    private String id;

    //private Recipe recipe;

    private String recipeNotes;

}