package com.resolutech.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(of={"id"})
@Document
public class UnitOfMeasure {

    @Id
    private String id;
    private String description;
}
