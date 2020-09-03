/**
 * 
 */
package twolak.springframework.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import twolak.springframework.commands.IngredientCommand;
import twolak.springframework.converters.IngredientCommandToIngredient;
import twolak.springframework.converters.IngredientToIngredientCommand;
import twolak.springframework.domain.Ingredient;
import twolak.springframework.domain.Recipe;
import twolak.springframework.exceptions.NotFoundException;
import twolak.springframework.repositories.RecipeRepository;
import twolak.springframework.repositories.UnitOfMeasureRepository;
import twolak.springframework.services.IngredientService;

/**
 * @author twolak
 *
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Recipe recipe = getRecipeById(recipeId);
		
		Optional<IngredientCommand> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
				.findFirst().map(ingredientToIngredientCommand::convert);
		if (!ingredientOptional.isPresent()) {
			String msg = "Ingredient for id: " + ingredientId + " not found!";
			log.debug(msg);
			throw new NotFoundException(msg);
		}
		return ingredientOptional.get(); 
	}

	@Override
	public IngredientCommand save(IngredientCommand ingredientCommand) {
		Recipe recipe = getRecipeById(ingredientCommand.getRecipeId());
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
		if (ingredientOptional.isPresent()) {
			Ingredient ingredient = ingredientOptional.get();
			ingredient.setAmount(ingredientCommand.getAmount());
			ingredient.setDescription(ingredientCommand.getDescription());
			ingredient.setUnitOfMeasure(
					this.unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
							.orElseThrow(() -> new RuntimeException("Unit of measure not found!")));
		} else {
			recipe.addIngredient(this.ingredientCommandToIngredient.convert(ingredientCommand));

		}
		Recipe savedRecipe = this.recipeRepository.save(recipe);

		Optional<Ingredient> savedingredientOptional = savedRecipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
		Ingredient savedIngredient;
		if (!savedingredientOptional.isPresent()) {
			
			savedingredientOptional = savedRecipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
					.filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
					.filter(ingredient -> ingredient.getUnitOfMeasure().getId()
							.equals(ingredientCommand.getUnitOfMeasure().getId()))
					.findFirst();
			if (!savedingredientOptional.isPresent()) {
				String msg = "Ingredient for id: " + ingredientCommand.getId() + " not found!";
				log.debug(msg);
				throw new RuntimeException(msg);
			}
		}
		savedIngredient = savedingredientOptional.get();
		return this.ingredientToIngredientCommand.convert(savedIngredient);
	}

	private Recipe getRecipeById(Long recipeId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			String msg = "Recipe for id: " + recipeId + " not found!";
			log.debug(msg);
			throw new RuntimeException(msg);
		}
		return recipeOptional.get();
	}

	@Override
	public void deleteById(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = this.recipeRepository.findById(recipeId);
		
		if (!recipeOptional.isPresent()) {
			String msg = "Recipe for id: " + recipeId + " not found!";
			log.debug(msg);
			throw new RuntimeException(msg);
		}
		
		Recipe recipe = recipeOptional.get();
		Optional<Ingredient> ingredientToDeleteOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();
		
		if (!ingredientToDeleteOptional.isPresent()) {
			String msg  = "Ingredient for id: " + ingredientId + " not found!";
			log.debug(msg);
			throw new RuntimeException(msg);
		}
		Ingredient ingredientToDelete = ingredientToDeleteOptional.get();
		ingredientToDelete.setRecipe(null);
		recipe.getIngredients().remove(ingredientToDelete);
		this.recipeRepository.save(recipe);
	}
}
