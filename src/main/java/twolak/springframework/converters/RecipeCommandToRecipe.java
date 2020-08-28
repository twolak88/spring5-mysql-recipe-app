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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
	
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final CategoryCommandToCategory categoryCommandToCategory;

	public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientCommandToIngredient,
			CategoryCommandToCategory categoryCommandToCategory) {
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.categoryCommandToCategory = categoryCommandToCategory;
	}

	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}
		final Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setDescription(source.getDescription());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setCookTime(source.getCookTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setDirections(source.getDirections());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setImage(source.getImage());
		recipe.setNotes((new NotesCommandToNotes()).convert(source.getNotes()));
		recipe.setIngredients(source.getIngredients().stream().map(this.ingredientCommandToIngredient::convert)
				.collect(Collectors.toSet()));
		recipe.setCategories(source.getCategories().stream().map(this.categoryCommandToCategory::convert)
				.collect(Collectors.toSet()));
		return recipe;
	}

}
