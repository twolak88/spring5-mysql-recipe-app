/**
 * 
 */
package twolak.springframework.commands;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import twolak.springframework.domain.Difficulty;

/**
 * @author twolak
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	
	@NotNull
	@Min(1)
	@Max(999)
	private Integer prepTime;
	
	@Min(1)
	@Max(999)
	private Integer cookTime;
	
	@Min(1)
	@Max(100)
	private Integer servings;
	private String source;
	
	@URL
//	@NotBlank
	private String url;
	
	@NotBlank
	private String directions;
	
	private Difficulty difficulty;
	
	@Valid
	private NotesCommand notes;
    private Byte[] image;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private Set<CategoryCommand> categories = new HashSet<>();
}
