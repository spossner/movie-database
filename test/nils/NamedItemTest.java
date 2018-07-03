package nils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NamedItemTest {
	private NamedItem item = null;
	
	@BeforeEach
	void setup() {
		this.item = new NamedItem("Keanu Reeves in THE MATRIX");
	}

	@Test
	void testToString() {
		String s = this.item.getName();
		assertEquals("Keanu Reeves in THE MATRIX", s);
	}

	@Test
	void testLowercase() {
		String s = this.item.getLowercaseName();
		assertEquals("keanu reeves in the matrix", s);
	}
	
	@Test
	void testCamelcase() {
		String s = this.item.getCamelcaseName();
		assertEquals("KeanuReevesInTheMatrix", s);
	}
}
