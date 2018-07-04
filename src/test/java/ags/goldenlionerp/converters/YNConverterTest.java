package ags.goldenlionerp.converters;

import static org.junit.Assert.*;

import org.junit.Test;

public class YNConverterTest {

	@Test
	public void testConvertToDatabaseColumn() {
		YNConverter conv = new YNConverter();
		
		assertEquals(conv.convertToDatabaseColumn(true), "Y");
		assertEquals(conv.convertToDatabaseColumn(Boolean.TRUE), "Y");
		assertEquals(conv.convertToDatabaseColumn(false), "");
		assertEquals(conv.convertToDatabaseColumn(Boolean.FALSE), "");
	}

	@Test
	public void testConvertToEntityAttribute() {
		YNConverter conv = new YNConverter();
		
		assertEquals(conv.convertToEntityAttribute("Y"), true);
		assertEquals(conv.convertToEntityAttribute(""), false);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConvertToEntityAttributeException() {
		YNConverter conv = new YNConverter();
		conv.convertToEntityAttribute("X");
	}
}
