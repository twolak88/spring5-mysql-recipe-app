/**
 * 
 */
package twolak.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import twolak.springframework.domain.Category;

/**
 * @author twolak
 *
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
