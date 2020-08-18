/**
 * 
 */
package twolak.springframework.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import twolak.springframework.domain.Category;

/**
 * @author twolak
 *
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	Optional<Category> findByDescription(String description);
}
