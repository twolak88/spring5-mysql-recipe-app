/**
 * 
 */
package twolak.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import twolak.springframework.domain.UnitOfMeasure;

/**
 * @author twolak
 *
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

}
