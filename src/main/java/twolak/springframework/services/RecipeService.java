package twolak.springframework.services;

import java.util.Set;

import twolak.springframework.commands.RecipeCommand;
import twolak.springframework.domain.Recipe;

/**
 * @author twolak
 *
 */
public interface RecipeService {
	Set<Recipe> findAllRecipes();
	RecipeCommand findRecipeById(Long id);
	RecipeCommand saveRecipe(RecipeCommand recipeCommand);
}
