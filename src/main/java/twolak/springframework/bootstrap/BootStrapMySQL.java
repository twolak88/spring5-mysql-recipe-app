/**
 * 
 */
package twolak.springframework.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import twolak.springframework.domain.Category;
import twolak.springframework.domain.UnitOfMeasure;
import twolak.springframework.repositories.CategoryRepository;
import twolak.springframework.repositories.UnitOfMeasureRepository;

/**
 * @author twolak
 *
 */
@Slf4j
@Profile({"dev", "prod"})
@Component
public class BootStrapMySQL implements ApplicationListener<ContextRefreshedEvent> {
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final CategoryRepository categoryRepository;
	
	public BootStrapMySQL(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (this.categoryRepository.count() == 0) {
			log.debug("Loading categories");
			loadCategories();
		}
		
		if (this.unitOfMeasureRepository.count() == 0) {
			log.debug("Loading Unit of measures");
			LoadUnitOfMeasures();
		}
		
	}
	
	private void loadCategories() {
		saveCategory("American");
		saveCategory("Italian");
		saveCategory("Mexican");
		saveCategory("Fast Food");
	}
	
	private void saveCategory(String category) {
		Category cat = new Category();
		cat.setDescription(category);
		this.categoryRepository.save(cat);
	}
	
	private void LoadUnitOfMeasures() {
		saveUnitOfMeasure("Teaspoon");
		saveUnitOfMeasure("Tablespoon");
		saveUnitOfMeasure("Cup");
		saveUnitOfMeasure("Pinch");
		saveUnitOfMeasure("Ounce");
		saveUnitOfMeasure("Dash");
		saveUnitOfMeasure("Pound");
		saveUnitOfMeasure("Piece");
		saveUnitOfMeasure("Quart");
	}
	
	private void saveUnitOfMeasure(String description) {
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setDescription(description);
		this.unitOfMeasureRepository.save(unitOfMeasure);
	}
}
