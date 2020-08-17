/**
 * 
 */
package twolak.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import twolak.springframework.domain.Recipe;

/**
 * @author twolak
 *
 */
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
