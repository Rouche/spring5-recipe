package com.resolutech.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @Author Jean-Francois Larouche
 */
@Data
@EqualsAndHashCode(of={"id"})
@ToString(exclude={"recipe"})
public class Notes {

    private String id;

    private Recipe recipe;

    private String recipeNotes;

}