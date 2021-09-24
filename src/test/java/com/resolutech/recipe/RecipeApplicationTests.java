package com.resolutech.recipe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @Important this is to test with Spring context. Slower.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeApplicationTests {

	@Test
	public void contextLoads() {
	}

}
