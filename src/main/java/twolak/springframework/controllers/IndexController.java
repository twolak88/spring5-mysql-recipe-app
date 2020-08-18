package twolak.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import twolak.springframework.services.RecipeService;

@Controller()
public class IndexController {
    
	private final RecipeService recipeService;
    
	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
		
		model.addAttribute("recipes", recipeService.findAllRecipes());
		
		return "index";
    }
}
