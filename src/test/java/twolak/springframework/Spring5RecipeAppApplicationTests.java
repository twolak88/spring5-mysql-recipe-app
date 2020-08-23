package twolak.springframework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import twolak.springframework.controllers.RecipeController;
import twolak.springframework.domain.CategoryTest;
import twolak.springframework.services.impl.RecipeServiceImplTest;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class Spring5RecipeAppApplicationTests {

	@Test
	void contextLoads() {
	}

}
