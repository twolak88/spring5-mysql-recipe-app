package twolak.springframework.domain;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Generated;

import org.junit.Before;
import org.junit.Test;

@Generated(value = "org.junit-tools-1.1.0")
public class CategoryTest {

	Category category;
	
	@Before
	public void setUp() {
		category = new Category();
	}
	
	@Test
	public void testGetId() throws Exception {
		Long idVal = 4L;
		category.setId(idVal);
		
		assertEquals(idVal, category.getId());
	}
	
	@Test
	public void testGetDescription() throws Exception {
		String description = "description";
		category.setDescription(description);
		
		assertEquals(description, category.getDescription());
	}
	
	@Test
	public void testGetRecipes() throws Exception {
		Set<Recipe> recipes = new HashSet<>();
		Recipe recipe = new Recipe();
		recipe.setCookTime(1);
		recipe.setDescription("description");
		recipes.add(recipe);
		category.setRecipes(recipes);
		
		assertEquals(recipes, category.getRecipes());
	}
}