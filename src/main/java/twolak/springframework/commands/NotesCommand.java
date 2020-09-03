/**
 * 
 */
package twolak.springframework.commands;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author twolak
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
	private Long id;
	
	@NotBlank
	private String note;
}
