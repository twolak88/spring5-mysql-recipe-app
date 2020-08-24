/**
 * 
 */
package twolak.springframework.commands;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import twolak.springframework.domain.UnitOfMeasure;

/**
 * @author twolak
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
	private Long id;
	private String note;
}
