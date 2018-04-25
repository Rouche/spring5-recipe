package com.resolutech.recipe.services;

import com.resolutech.recipe.commands.UnitOfMeasureCommand;
import com.resolutech.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.resolutech.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand;

    private UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand) {
        this.uomToUomCommand = uomToUomCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
            .map(uomToUomCommand::convert)
            .collect(Collectors.toSet());
    }
}
