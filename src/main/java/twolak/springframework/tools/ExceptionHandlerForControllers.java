/**
 * 
 */
package twolak.springframework.tools;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * @author twolak
 *
 */
@Slf4j
public class ExceptionHandlerForControllers {
	
	public static ModelAndView handleException(Exception exception, HttpStatus httpStatus) {
		log.error("Handling " + exception.getClass());
		log.error(exception.getMessage());
		log.error(ExceptionUtils.getStackTrace(exception));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("recipe/error/404error");
		modelAndView.addObject("exception", exception);
		modelAndView.addObject("httpStatus", httpStatus);
		return modelAndView;
	}
}
