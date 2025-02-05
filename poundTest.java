package poundConverter;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class poundTest {
	@Test
	public void testValues() {
		assertEquals(22.68, consoleConvert.poundConvert(50), 0.01, "50 pounds should be 22.68kg");
		assertEquals(0, consoleConvert.poundConvert(0), 0.0, "0 pounds should be 0kg");
	}
	@Test
	public void testPositive() {
		double kg = consoleConvert.poundConvert(1);
		assertTrue(kg > 0, "A positive pound value should result in a positive kg value");
		
	}
	
	
}
