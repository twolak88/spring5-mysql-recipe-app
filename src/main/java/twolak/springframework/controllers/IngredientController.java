/**
 * 
 */
package twolak.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import twolak.springframework.commands.IngredientCommand;
import twolak.springframework.commands.RecipeCommand;
import twolak.springframework.commands.UnitOfMeasureCommand;
import twolak.springframework.services.IngredientService;
import twolak.springframework.services.RecipeService;
import twolak.springframework.services.UnitOfMeasureService;

/**
 * @author twolak
 *
 */
@Slf4j
@Controller
@RequestMapping("/recipe/{recipeId}")
public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;
	
	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}
	
	@GetMapping("/ingredients")
	public String listIngredients(@PathVariable Long recipeId, Model model) {
		log.debug("Getting ingredients for recipe: " + recipeId);
		model.addAttribute("recipe", this.recipeService.findById(recipeId));
		return "recipe/ingredient/list";
	}
	
	@GetMapping("/ingredient/{ingredientId}/show")
	public String showRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {
		log.debug("Getting ingredient for ingredient id: " + ingredientId + ", recipe id: " + recipeId);
		model.addAttribute("ingredient", this.ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
		return "recipe/ingredient/show";
	}
	
	@GetMapping("/ingredient/new")
	public String newIngredient(@PathVariable Long recipeId, Model model) {
		RecipeCommand recipeCommand = this.recipeService.findById(recipeId);
		if (recipeCommand == null) {
			throw new RuntimeException("Recipe doesn't exists!");
		}
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeId);
		
		model.addAttribute("ingredient", ingredientCommand);
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
		
		model.addAttribute("uomList", this.unitOfMeasureService.findAll());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping("/ingredient/{ingredientId}/update")
	public String updateRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {
		model.addAttribute("ingredient", this.ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
		model.addAttribute("uomList", this.unitOfMeasureService.findAll());
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping("/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
		IngredientCommand savedIngredientCommand = this.ingredientService.save(ingredientCommand);
		return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
	}
	
	@GetMapping("/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
		this.ingredientService.deleteById(recipeId, ingredientId);
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
}
