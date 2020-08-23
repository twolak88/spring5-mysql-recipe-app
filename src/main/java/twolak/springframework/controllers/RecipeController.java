package twolak.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@Controller
@RequestMapping("/recipe")
public class RecipeController {

	private RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/show/{recipeId}")
	public String getRecipe(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", this.recipeService.findRecipeById(recipeId));
		return "recipe/show";
	}
}
