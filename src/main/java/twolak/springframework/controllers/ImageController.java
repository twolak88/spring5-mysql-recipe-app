package twolak.springframework.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import twolak.springframework.commands.RecipeCommand;
import twolak.springframework.services.ImageService;
import twolak.springframework.services.RecipeService;

/**
 * @author twolak
 *
 */
@Slf4j
@Controller
@RequestMapping("/recipe/{recipeId}")
public class ImageController {
	private final ImageService imageService;
	private final RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	@GetMapping("/image")
	public String getImageForm(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", this.recipeService.findById(recipeId));
		return "recipe/image/imageuploadform";
	}
	
	@PostMapping("/image")
	public String handleImagePost(@PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile file) {
		this.imageService.saveImageFile(recipeId, file);
		return "redirect:/recipe/" + recipeId + "/show";
	}
	
	@GetMapping("/recipeimage")
	public void renderImageFromDB(@PathVariable Long recipeId, HttpServletResponse httpServletResponse) throws IOException {
		RecipeCommand recipeCommand = this.recipeService.findById(recipeId);
		if (recipeCommand.getImage() != null && recipeCommand.getImage().length > 0) {
			byte[] imageBytes = new byte[recipeCommand.getImage().length];
			
			int i = 0;
			for(Byte wrappedBytes : recipeCommand.getImage()) {
				imageBytes[i++] = wrappedBytes;
			}
			
			httpServletResponse.setContentType("image/jpg");
			InputStream inputStream = new ByteArrayInputStream(imageBytes);
			IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
		} else {
			log.error("Image for recipe id: " + recipeId + " desn't exists!");
		}
	}
}
