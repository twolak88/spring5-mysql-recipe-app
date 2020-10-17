/**
 * 
 */
package twolak.springframework.controllers;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author twolak
 *
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler({WebExchangeBindException.class})
	public String handleNotFound(Exception exception, Model model) {
		return handleException(exception, model, HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public String handleNumberFormat(NumberFormatException exception, Model model) {
		return handleException(exception, model, HttpStatus.BAD_REQUEST);
	}
	
	private String handleException(Exception exception, Model model, HttpStatus httpStatus) {
		log.error("Handling " + exception.getClass());
		log.error(exception.getMessage());
		log.error(ExceptionUtils.getStackTrace(exception));
		
		model.addAttribute("exception", exception);
		model.addAttribute("httpStatus", httpStatus);
		return "recipe/error/404error";
	}
}
