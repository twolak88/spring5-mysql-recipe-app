package twolak.springframework.services;

import twolak.springframework.commands.IngredientCommand;

/**
 * @author twolak
 *
 */
public interface IngredientService {
	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);
	IngredientCommand save(IngredientCommand ingredientCommand);
	void deleteById(Long recipeId, Long ingredientId);
}
