package twolak.springframework.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import twolak.springframework.domain.Recipe;
import twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {
	private final int METHODS_CALL_TIMES = 1;
	
	@Mock
	private RecipeService recipeService;
	
	@InjectMocks
	private RecipeController recipeController;
	
	@Mock
	private Model model;
	
	@BeforeEach
	void setUp() throws Exception {
//		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		
		when(this.recipeService.findRecipeById(anyLong())).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/show/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"))
				.andExpect(MockMvcResultMatchers.model().attribute("recipe", Matchers.hasProperty("id", is(1L))))
				.andExpect(handler().handlerType(RecipeController.class))
		        .andExpect(handler().methodName("getRecipe"));
		
		verify(this.recipeService, times(METHODS_CALL_TIMES)).findRecipeById(anyLong());
		verifyNoMoreInteractions(this.recipeService);
	}
}
