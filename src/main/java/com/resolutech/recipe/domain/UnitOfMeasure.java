package com.resolutech.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"id"})
public class UnitOfMeasure {

    private String id;
    private String description;
}
