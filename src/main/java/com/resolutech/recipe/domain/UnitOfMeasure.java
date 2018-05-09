package com.resolutech.recipe.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(of={"id"})
public class UnitOfMeasure {

    private String id;
    private String description;
}
