package com.resolutech.recipe.services;

import com.resolutech.recipe.commands.UnitOfMeasureCommand;
import com.resolutech.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.resolutech.recipe.repositories.UnitOfMeasureRepository;
import com.resolutech.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand;

    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand) {
        this.uomToUomCommand = uomToUomCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    //@Transactional
    public Flux<UnitOfMeasureCommand> listAllUoms() {
        return unitOfMeasureRepository.findAll()
            .map(uomToUomCommand::convert);

//        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
//            .map(uomToUomCommand::convert)
//            .collect(Collectors.toSet());
    }
}
