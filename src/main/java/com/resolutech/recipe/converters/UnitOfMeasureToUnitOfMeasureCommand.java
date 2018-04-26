package com.resolutech.recipe.converters;

import com.resolutech.recipe.commands.UnitOfMeasureCommand;
import com.resolutech.recipe.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @Author Jean-Francois Larouche
 */
@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure uom) {

        if (uom != null) {
            final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
            uomc.setId(uom.getId());
            uomc.setDescription(uom.getDescription());
            return uomc;
        }
        return null;
    }
}
