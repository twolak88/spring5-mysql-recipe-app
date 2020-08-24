package twolak.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import twolak.springframework.commands.RecipeCommand;
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

	@GetMapping("/{recipeId}/show")
	public String getRecipe(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", this.recipeService.findRecipeById(recipeId));
		return "recipe/show";
	}
	
	@GetMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@GetMapping("/{recipeId}/update")
	public String updateRecipe(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", this.recipeService.findRecipeById(recipeId));
		return "recipe/recipeform";
	}
	
	@PostMapping
	public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
		RecipeCommand savedRecipeCommand = this.recipeService.saveRecipe(recipeCommand);
		
		return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
	}
}
