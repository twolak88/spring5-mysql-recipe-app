/**
 * 
 */
package twolak.springframework.converters;

import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import twolak.springframework.commands.RecipeCommand;
import twolak.springframework.domain.Recipe;

/**
 * @author twolak
 *
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final CategoryToCategoryCommand categoryToCategoryCommand; 

	public RecipeToRecipeCommand(IngredientToIngredientCommand ingredientToIngredientCommand,
			twolak.springframework.converters.CategoryToCategoryCommand categoryToCategoryCommand) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.categoryToCategoryCommand = categoryToCategoryCommand;
	}

	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if (source == null) {
			return null;
		}
		final RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setImage(source.getImage());
		recipeCommand.setNotes((new NotesToNotesCommand()).convert(source.getNotes()));
		recipeCommand.setIngredients(source.getIngredients().stream().map(this.ingredientToIngredientCommand::convert)
				.collect(Collectors.toSet()));
		recipeCommand.setCategories(source.getCategories().stream().map(this.categoryToCategoryCommand::convert)
				.collect(Collectors.toSet()));
		return recipeCommand;
	}
}
