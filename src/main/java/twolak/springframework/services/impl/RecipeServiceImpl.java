package twolak.springframework.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import twolak.springframework.domain.Recipe;
import twolak.springframework.repositories.RecipeRepository;
import twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Set<Recipe> findAllRecipes() {
		Set<Recipe> recipes = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return  recipes;
	}
}
