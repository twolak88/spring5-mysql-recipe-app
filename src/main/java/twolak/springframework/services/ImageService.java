package twolak.springframework.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author twolak
 *
 */
public interface ImageService {
	void saveImageFile(Long id, MultipartFile file);
}
