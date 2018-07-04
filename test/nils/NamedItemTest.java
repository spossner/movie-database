package nils;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class NamedItemTest {
	private NamedItem item = null;
	
	@Before
	public void setup() {
		this.item = new NamedItem("Keanu Reeves in THE MATRIX");
	}

	@Test
	public void testToString() {
		String s = this.item.getName();
		assertEquals("Keanu Reeves in THE MATRIX", s);
	}

	@Test
	public void testLowercase() {
		String s = this.item.getLowercaseName();
		assertEquals("keanu reeves in the matrix", s);
	}
	
	@Test
	public void testCamelcase() {
		String s = this.item.getCamelcaseName();
		assertEquals("KeanuReevesInTheMatrix", s);
	}
}
