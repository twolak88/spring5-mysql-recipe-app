/**
 * 
 */
package twolak.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import twolak.springframework.commands.UnitOfMeasureCommand;
import twolak.springframework.domain.UnitOfMeasure;

/**
 * @author twolak
 *
 */
@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if (source == null) {
			return null;
		}
		final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
		unitOfMeasureCommand.setId(source.getId());
		unitOfMeasureCommand.setDescription(source.getDescription());
		return unitOfMeasureCommand;
	}

}
