/**
 * 
 */
package twolak.springframework.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import twolak.springframework.commands.IngredientCommand;
import twolak.springframework.converters.IngredientCommandToIngredient;
import twolak.springframework.converters.IngredientToIngredientCommand;
import twolak.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import twolak.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import twolak.springframework.domain.Ingredient;
import twolak.springframework.domain.Recipe;
import twolak.springframework.repositories.RecipeRepository;
import twolak.springframework.repositories.UnitOfMeasureRepository;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {
	private static Long RECIPE_ID = 1L;
	private static Long ING_ID = 1L;

	private IngredientServiceImpl ingredientServiceImpl;

	@Mock
	private RecipeRepository recipeRepository;
	
	@Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

	@BeforeEach
	void setUp() throws Exception {
		this.ingredientServiceImpl = new IngredientServiceImpl(this.recipeRepository, this.unitOfMeasureRepository,
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
	}

	@Test
	public void testFindByRecipeIdAndIngredientId() {
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ING_ID);
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(ING_ID + 1);
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(ING_ID);
		recipe.addIngredient(ingredient);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		IngredientCommand ingredientCommand = this.ingredientServiceImpl.findByRecipeIdAndIngredientId(RECIPE_ID,
				ING_ID);

		assertNotNull("Null ingredient found", ingredientCommand);
		assertEquals(ING_ID, ingredientCommand.getId());
		assertEquals(RECIPE_ID, ingredientCommand.getRecipeId());
		verify(this.recipeRepository, times(1)).findById(anyLong());
		verifyNoMoreInteractions(this.recipeRepository);
	}

	@Test
	public void testSave() {
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ING_ID);
		ingredientCommand.setRecipeId(RECIPE_ID);

		Optional<Recipe> optionalRecipe = Optional.of(new Recipe());

		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(ING_ID);

		when(this.recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
		when(this.recipeRepository.save(any())).thenReturn(savedRecipe);

		IngredientCommand savedIngredientCommand = this.ingredientServiceImpl.save(ingredientCommand);

		assertEquals(ING_ID, savedIngredientCommand.getId());
		verify(this.recipeRepository, times(1)).findById(anyLong());
		verify(this.recipeRepository, times(1)).save(any());
		verifyNoMoreInteractions(this.recipeRepository);
	}
	
	@Test
	public void testDelete() {
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ING_ID);
		recipe.addIngredient(ingredient);
		
		when(this.recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
		
		this.ingredientServiceImpl.deleteById(RECIPE_ID, ING_ID);
		
		verify(this.recipeRepository, times(1)).findById(anyLong());
		verify(this.recipeRepository, times(1)).save(any(Recipe.class));
		verifyNoMoreInteractions(this.recipeRepository);
	}
}
