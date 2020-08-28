package twolak.springframework.services;

import java.util.Set;

import twolak.springframework.commands.UnitOfMeasureCommand;

/**
 * @author twolak
 *
 */
public interface UnitOfMeasureService {
	Set<UnitOfMeasureCommand> findAll();
}
