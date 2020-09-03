package twolak.springframework.controllers;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import twolak.springframework.commands.RecipeCommand;
import twolak.springframework.exceptions.NotFoundException;
import twolak.springframework.services.RecipeService;
import twolak.springframework.tools.ExceptionHandlerForControllers;

/**
 * @author twolak
 *
 */
@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

	private RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/{recipeId}/show")
	public String getRecipe(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", this.recipeService.findById(recipeId));
		return "recipe/show";
	}
	
	@GetMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@GetMapping("/{recipeId}/update")
	public String updateRecipe(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", this.recipeService.findById(recipeId));
		return "recipe/recipeform";
	}
	
	@PostMapping
	public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
		RecipeCommand savedRecipeCommand = this.recipeService.save(recipeCommand);
		return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
	}
	
	@GetMapping("/{recipeId}/delete")
	public String deleteRecipe(@PathVariable Long recipeId) {
		log.debug("Deleting id: " + recipeId);
		this.recipeService.deleteById(recipeId);
		return "redirect:/";
	}
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(NotFoundException exception) {
		return ExceptionHandlerForControllers.handleException(exception, HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormat(NumberFormatException exception) {
		return ExceptionHandlerForControllers.handleException(exception, HttpStatus.BAD_REQUEST);
	}
}
