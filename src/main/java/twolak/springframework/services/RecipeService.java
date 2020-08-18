package twolak.springframework.services;

import java.util.Set;

import twolak.springframework.domain.Recipe;

/**
 * @author twolak
 *
 */
public interface RecipeService {
	Set<Recipe> findAllRecipes();
}
