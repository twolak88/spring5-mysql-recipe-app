/**
 * 
 */
package twolak.springframework.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import twolak.springframework.commands.UnitOfMeasureCommand;
import twolak.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import twolak.springframework.domain.UnitOfMeasure;
import twolak.springframework.repositories.UnitOfMeasureRepository;

/**
 * @author twolak
 *
 */
class UnitOfMeasureServiceImplTest {
	
	private UnitOfMeasureServiceImpl unitOfMeasureServiceImpl; 
	
	@Mock
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.unitOfMeasureServiceImpl = new UnitOfMeasureServiceImpl(this.unitOfMeasureRepository, new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Test
			void testFindAll() {
				Set<UnitOfMeasure> uoms = new HashSet<>();
				UnitOfMeasure uom1 = new UnitOfMeasure();
				uom1.setId(1L);
				UnitOfMeasure uom2 = new UnitOfMeasure();
				uom2.setId(2L);
				UnitOfMeasure uom3 = new UnitOfMeasure();
				uom2.setId(3L);
				uoms.add(uom1);
				uoms.add(uom2);
				uoms.add(uom3);
				
				when(this.unitOfMeasureRepository.findAll()).thenReturn(uoms);
				
				Set<UnitOfMeasureCommand> uomCommands = this.unitOfMeasureServiceImpl.findAll();
				
				assertEquals(3, uomCommands.size());
				verify(this.unitOfMeasureRepository, times(1)).findAll();
				verifyNoMoreInteractions(this.unitOfMeasureRepository);
			}

}
