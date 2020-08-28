package twolak.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@Slf4j
@Controller()
public class IndexController {
    
	private final RecipeService recipeService;
    
	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
		
		model.addAttribute("recipes", recipeService.findAll());
		log.info("Getting Index Page");
		return "index";
    }
}
