package twolak.springframework.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import twolak.springframework.commands.RecipeCommand;
import twolak.springframework.converters.CategoryToCategoryCommand;
import twolak.springframework.converters.IngredientToIngredientCommand;
import twolak.springframework.converters.RecipeToRecipeCommand;
import twolak.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import twolak.springframework.domain.Recipe;
import twolak.springframework.exceptions.NotFoundException;
import twolak.springframework.repositories.RecipeRepository;

/**
 * @author twolak
 *
 */
public class RecipeServiceImplTest {

	private final Long RECIPE_ID = 1L;

	private RecipeServiceImpl recipeService;

	@Mock
	private RecipeRepository recipeRepository;

	private RecipeToRecipeCommand recipeToRecipeCommand;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeToRecipeCommand = new RecipeToRecipeCommand(
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new CategoryToCategoryCommand());
		recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, null);

	}

	@Test
	public void testDeleteById() {
		Long idToDelete = 2L;
		this.recipeService.deleteById(idToDelete);

		// woid, returned nothing, so no when

		verify(this.recipeRepository, times(1)).deleteById(anyLong());
	}

	@Test
	public void testFindById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		RecipeCommand foundRecipe = this.recipeService.findById(RECIPE_ID);

		assertNotNull("Null recipe found", foundRecipe);
		assertEquals(recipe.getId(), foundRecipe.getId());
		verify(this.recipeRepository, times(1)).findById(anyLong());
		verifyNoMoreInteractions(this.recipeRepository);
		verify(this.recipeRepository, never()).findAll();
	}
	
	@Test
	public void testFindByIdNotFound() {
		Optional<Recipe> recipeOptional = Optional.empty();
		
		when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		assertThrows(NotFoundException.class, () -> this.recipeService.findById(RECIPE_ID));
		verify(this.recipeRepository, times(1)).findById(anyLong());
		verifyNoMoreInteractions(this.recipeRepository);
	}

	@Test
	public void testFindAll() throws Exception {
		Recipe recipe = new Recipe();
		Set<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);

		when(this.recipeRepository.findAll()).thenReturn(recipesData);

		Set<Recipe> recipes = recipeService.findAll();

		assertEquals(recipes.size(), 1);
		verify(this.recipeRepository, times(1)).findAll();
		verifyNoMoreInteractions(this.recipeRepository);
		verify(this.recipeRepository, never()).findById(anyLong());
	}
}