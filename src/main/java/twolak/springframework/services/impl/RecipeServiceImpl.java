package twolak.springframework.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import twolak.springframework.commands.RecipeCommand;
import twolak.springframework.converters.RecipeCommandToRecipe;
import twolak.springframework.converters.RecipeToRecipeCommand;
import twolak.springframework.domain.Recipe;
import twolak.springframework.exceptions.NotFoundException;
import twolak.springframework.repositories.RecipeRepository;
import twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand,
			RecipeCommandToRecipe recipeCommandToRecipe) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> findAll() {
		log.info("Calling findAllRecipes()");
		Set<Recipe> recipes = new HashSet<>();
		this.recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}

	@Override
	public RecipeCommand findById(Long id) {
		log.info("Calling findRecipeById(Long)");
		return this.recipeToRecipeCommand.convert(this.recipeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No Recipe found with id: " + id)));
	}

	@Transactional
	@Override
	public RecipeCommand save(RecipeCommand recipeCommand) {
		Recipe convertedRecipe = this.recipeCommandToRecipe.convert(recipeCommand);
		
		Recipe savedRecipe = this.recipeRepository.save(convertedRecipe);
		return this.recipeToRecipeCommand.convert(savedRecipe);
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		this.recipeRepository.deleteById(id);		
	}
}
