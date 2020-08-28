/**
 * 
 */
package twolak.springframework.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import twolak.springframework.commands.RecipeCommand;
import twolak.springframework.converters.RecipeToRecipeCommand;
import twolak.springframework.domain.Recipe;
import twolak.springframework.repositories.RecipeRepository;
import twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceImplIT {
	
	private static final String DESRIPTION = "some description"; 
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private RecipeToRecipeCommand recipeToRecipeCommand; 
	
	@Transactional
	@Test
	public void testSaveRecipes() throws Exception {
		Iterable<Recipe> recipes = this.recipeRepository.findAll();
		Recipe testRecipe = recipes.iterator().next();
		RecipeCommand testRecipeCommand = this.recipeToRecipeCommand.convert(testRecipe);
		
		testRecipeCommand.setDescription(DESRIPTION);
		RecipeCommand savedRecipeCommand = this.recipeService.save(testRecipeCommand);
		
		assertNotNull(savedRecipeCommand);
		assertEquals(DESRIPTION, savedRecipeCommand.getDescription());
		assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
		assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(),savedRecipeCommand.getIngredients().size());
	}
}
