/**
 * 
 */
package twolak.springframework.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import twolak.springframework.domain.Recipe;
import twolak.springframework.repositories.RecipeRepository;
import twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

	@InjectMocks
	private ImageServiceImpl imageServiceImpl;

	@Mock
	private RecipeRepository recipeRepository;

	@Mock
	private RecipeService recipeService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSaveImageFile() throws Exception {
		Long id = 1L;
		MultipartFile multipartFile = new MockMultipartFile("file", "testing.txt", "text/plain",
				"Spring Framework TW".getBytes());
		Recipe recipe = new Recipe();
		recipe.setId(id);

		when(this.recipeRepository.findById(id)).thenReturn(Optional.of(recipe));
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		imageServiceImpl.saveImageFile(id, multipartFile);
		
		verify(this.recipeRepository, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
	}
}
