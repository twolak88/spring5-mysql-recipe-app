/**
 * 
 */
package twolak.springframework.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import twolak.springframework.commands.CategoryCommand;
import twolak.springframework.commands.IngredientCommand;
import twolak.springframework.commands.NotesCommand;
import twolak.springframework.commands.RecipeCommand;
import twolak.springframework.domain.Difficulty;
import twolak.springframework.domain.Recipe;

/**
 * @author twolak
 *
 */
class RecipeCommandToRecipeTest {
	
	private static final Long RECIPE_ID = 1L;
	private static final Integer COOK_TIME = Integer.valueOf("5");
	private static final Integer PREP_TIME = Integer.valueOf("7");
	private static final String DESCRIPTION = "My Recipe";
	private static final String DIRECTIONS = "Directions";
	private static final Difficulty DIFFICULTY = Difficulty.EASY;
	private static final Integer SERVINGS = Integer.valueOf("3");
	private static final String SOURCE = "Source";
	private static final String URL = "Some URL";
	private static final Long CAT_ID_1 = 1L;
	private static final Long CAT_ID2 = 2L;
	private static final Long INGRED_ID_1 = 3L;
	private static final Long INGRED_ID_2 = 4L;
	private static final Long NOTES_ID = 9L;

	private RecipeCommandToRecipe recipeCommandToRecipe;

	@BeforeEach
	void setUp() throws Exception {
		this.recipeCommandToRecipe = new RecipeCommandToRecipe(
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
				new CategoryCommandToCategory());
	}

	@Test
	public void testNullParameter() throws Exception {
		assertNull(this.recipeCommandToRecipe.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(this.recipeCommandToRecipe.convert(new RecipeCommand()));
	}

	@Test
	void testConvert() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(RECIPE_ID);
		recipeCommand.setCookTime(COOK_TIME);
		recipeCommand.setPrepTime(PREP_TIME);
		recipeCommand.setDescription(DESCRIPTION);
		recipeCommand.setDifficulty(DIFFICULTY);
		recipeCommand.setDirections(DIRECTIONS);
		recipeCommand.setServings(SERVINGS);
		recipeCommand.setSource(SOURCE);
		recipeCommand.setUrl(URL);

		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(NOTES_ID);

		recipeCommand.setNotes(notesCommand);

		CategoryCommand category = new CategoryCommand();
		category.setId(CAT_ID_1);

		CategoryCommand category2 = new CategoryCommand();
		category2.setId(CAT_ID2);

		recipeCommand.getCategories().add(category);
		recipeCommand.getCategories().add(category2);

		IngredientCommand ingredient = new IngredientCommand();
		ingredient.setId(INGRED_ID_1);

		IngredientCommand ingredient2 = new IngredientCommand();
		ingredient2.setId(INGRED_ID_2);

		recipeCommand.getIngredients().add(ingredient);
		recipeCommand.getIngredients().add(ingredient2);

		Recipe recipe = this.recipeCommandToRecipe.convert(recipeCommand);

		assertNotNull(recipe);
		assertEquals(RECIPE_ID, recipe.getId());
		assertEquals(COOK_TIME, recipe.getCookTime());
		assertEquals(PREP_TIME, recipe.getPrepTime());
		assertEquals(DESCRIPTION, recipe.getDescription());
		assertEquals(DIFFICULTY, recipe.getDifficulty());
		assertEquals(DIRECTIONS, recipe.getDirections());
		assertEquals(SERVINGS, recipe.getServings());
		assertEquals(SOURCE, recipe.getSource());
		assertEquals(URL, recipe.getUrl());
		assertEquals(NOTES_ID, recipe.getNotes().getId());
		assertEquals(2, recipe.getCategories().size());
		assertEquals(2, recipe.getIngredients().size());
	}

}
