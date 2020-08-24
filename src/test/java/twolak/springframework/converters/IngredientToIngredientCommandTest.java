/**
 * 
 */
package twolak.springframework.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import twolak.springframework.commands.IngredientCommand;
import twolak.springframework.domain.Ingredient;
import twolak.springframework.domain.UnitOfMeasure;

/**
 * @author twolak
 *
 */
class IngredientToIngredientCommandTest {

	private static final BigDecimal AMOUNT = new BigDecimal("1");
	private static final String DESCRIPTION = "Cheeseburger";
	private static final Long ID_VALUE = 1L;
	private static final Long UOM_ID = 2L;

	private IngredientToIngredientCommand ingredientToIngredientCommand;

	@BeforeEach
	void setUp() throws Exception {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
				new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Test
	public void testNullParameter() throws Exception {
		assertNull(this.ingredientToIngredientCommand.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(this.ingredientToIngredientCommand.convert(new Ingredient()));
	}

	@Test
	void testConvert() throws Exception {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setAmount(AMOUNT);
		ingredient.setDescription(DESCRIPTION);
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(UOM_ID);
		ingredient.setUnitOfMeasure(unitOfMeasure);

		IngredientCommand ingredientCommand = this.ingredientToIngredientCommand.convert(ingredient);

		assertNotNull(ingredientCommand);
		assertNotNull(ingredientCommand.getUnitOfMeasureCommand());
		assertEquals(ID_VALUE, ingredientCommand.getId());
		assertEquals(AMOUNT, ingredientCommand.getAmount());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
		assertEquals(UOM_ID, ingredientCommand.getUnitOfMeasureCommand().getId());
	}

	@Test
	void testConvertWithNullUom() throws Exception {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setAmount(AMOUNT);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setUnitOfMeasure(null);

		IngredientCommand ingredientCommand = this.ingredientToIngredientCommand.convert(ingredient);

		assertNotNull(ingredientCommand);
		assertNull(ingredientCommand.getUnitOfMeasureCommand());
		assertEquals(ID_VALUE, ingredientCommand.getId());
		assertEquals(AMOUNT, ingredientCommand.getAmount());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
	}
}
