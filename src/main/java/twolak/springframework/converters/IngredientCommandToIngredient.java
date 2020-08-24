/**
 * 
 */
package twolak.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import twolak.springframework.commands.IngredientCommand;
import twolak.springframework.domain.Ingredient;

/**
 * @author twolak
 *
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
	
	private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure; 

	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
		this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
	}

	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null) {
			return null;
		}
		final Ingredient ingredient = new Ingredient();
		ingredient.setId(source.getId());
		ingredient.setAmount(source.getAmount());
		ingredient.setDescription(source.getDescription());
		ingredient.setUnitOfMeasure(this.unitOfMeasureCommandToUnitOfMeasure.convert(source.getUnitOfMeasureCommand()));
		return ingredient;
	}

}
