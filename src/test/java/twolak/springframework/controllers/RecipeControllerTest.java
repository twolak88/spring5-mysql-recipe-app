package twolak.springframework.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import twolak.springframework.commands.RecipeCommand;
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

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}

	@Test
	public void testGetRecipe() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		when(this.recipeService.findRecipeById(anyLong())).thenReturn(recipeCommand);

		mockMvc.perform(get("/recipe/1/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"))
				.andExpect(model().attribute("recipe", Matchers.hasProperty("id", is(1L))))
				.andExpect(handler().handlerType(RecipeController.class))
				.andExpect(handler().methodName("getRecipe"));

		verify(this.recipeService, times(METHODS_CALL_TIMES)).findRecipeById(anyLong());
		verifyNoMoreInteractions(this.recipeService);
	}

	@Test
	public void testNewRecipeForm() throws Exception {
		mockMvc.perform(get("/recipe/new")).andExpect(status().isOk()).andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"))
				.andExpect(model().attribute("recipe", Matchers.notNullValue(RecipeCommand.class)))
				.andExpect(handler().handlerType(RecipeController.class))
				.andExpect(handler().methodName("newRecipe"));
	}

	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2L);

		when(this.recipeService.saveRecipe(any())).thenReturn(recipeCommand);

		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("recipeId", "")
				.param("description", "some description")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/show"));
	}

	@Test
	public void testUpdateRecipeForm() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2L);

		when(this.recipeService.findRecipeById(anyLong())).thenReturn(recipeCommand);

		mockMvc.perform(get("/recipe/2/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"))
				.andExpect(model().attribute("recipe", Matchers.notNullValue(RecipeCommand.class)))
				.andExpect(handler().handlerType(RecipeController.class))
				.andExpect(handler().methodName("updateRecipe"));
		verify(this.recipeService, times(METHODS_CALL_TIMES)).findRecipeById(anyLong());
		verifyNoMoreInteractions(this.recipeService);
	}
}
