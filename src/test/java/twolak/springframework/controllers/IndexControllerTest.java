package twolak.springframework.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import twolak.springframework.domain.Recipe;
import twolak.springframework.services.RecipeService;

public class IndexControllerTest {
	
	private final int METHODS_CALL_TIMES = 1;
	private final int RECIPES_IN_SET = 2;

	private IndexController indexController;
	
	@Mock
	private RecipeService recipeService;
	
	@Mock
	private Model model;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeService);
	}
	
	@Test
	public void testMVCGetIndexPage() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		
		mockMvc.perform(get("/index/"))
			.andExpect(status().isOk())
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("index"));
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("index"));
		mockMvc.perform(get("/page"))
			.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testGetIndexPage() throws Exception {
		
		//given
		Set<Recipe> recipes = new HashSet<>();
		for(int i = 0; i < RECIPES_IN_SET; i++) {
			Recipe recipe = new Recipe();
			recipe.setId(i+1L);
			recipes.add(recipe);
		}
		
		when(recipeService.findAllRecipes()).thenReturn(recipes);
		
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class); 
		
		//when
		String viewName = indexController.getIndexPage(model);
		
		//then
		assertEquals("index", viewName);
		verify(model, times(METHODS_CALL_TIMES)).addAttribute(eq("recipes"), argumentCaptor.capture());
		verify(recipeService, times(METHODS_CALL_TIMES)).findAllRecipes();
		
		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(RECIPES_IN_SET, setInController.size());
	}
}