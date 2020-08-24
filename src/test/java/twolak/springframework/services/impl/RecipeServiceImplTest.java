package twolak.springframework.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import twolak.springframework.domain.Recipe;
import twolak.springframework.repositories.RecipeRepository;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {
	
	private final Long RECIPE_ID = 1L; 

	@InjectMocks
	private RecipeServiceImpl recipeService;

	@Mock
	private RecipeRepository recipeRepository;

	@BeforeEach
	public void setUp() throws Exception {
		//MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAllRecipes() throws Exception {
		Recipe recipe = new Recipe();
		Set<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);

		when(this.recipeRepository.findAll()).thenReturn(recipesData);

		Set<Recipe> recipes = recipeService.findAllRecipes();

		assertEquals(recipes.size(), 1);
		verify(this.recipeRepository, times(1)).findAll();
	}

	@Test
	public void testFindRecipeById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		Recipe foundRecipe = this.recipeService.findRecipeById(RECIPE_ID);
		
		assertNotNull("Null recipe found", foundRecipe);
		assertEquals(recipe, foundRecipe);
		verify(this.recipeRepository, times(1)).findById(anyLong());
		verifyNoMoreInteractions(this.recipeRepository);
		verify(this.recipeRepository, never()).findAll();
	}
}