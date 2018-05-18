package com.resolutech.recipe.services;

import com.resolutech.recipe.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAllUoms();

    //Set<UnitOfMeasureCommand> listAllUoms();
}
