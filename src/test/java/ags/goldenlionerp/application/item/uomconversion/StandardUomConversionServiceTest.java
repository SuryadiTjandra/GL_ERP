package ags.goldenlionerp.application.item.uomconversion;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StandardUomConversionServiceTest {

	@Autowired private StandardUomConversionService service;
	
	@Test
	public void testFindConversionValue() {
		Optional<BigDecimal> res1 = service.findConversionValue("MT", "MT");
		assertEquals(1, res1.get().intValueExact());
		
		//Optional<BigDecimal> res2 = service.findConversionValue("IN", "CM");
		//assertEquals(2.54, res2.get().doubleValue(), 0.001);
		
		//Optional<BigDecimal> res3 = service.findConversionValue("GM", "KG");
		//assertEquals(0.001, res3.get().doubleValue(), 0.001);
		
		//Optional<BigDecimal> res4 = service.findConversionValue("KM", "MM");
		//assertEquals(1000000, res4.get().intValue());
		
		//Optional<BigDecimal> res5 = service.findConversionValue("OZ", "KG");
		//assertEquals(0.02835, res5.get().doubleValue(), 0.001);
		
		//Optional<BigDecimal> res6 = service.findConversionValue("CM", "KG");
		//assertEquals(Optional.empty(), res6);
		
		//Optional<BigDecimal> res7 = service.findConversionValue("CM", "FT");
		//assertEquals(0.0328084, res7.get().doubleValue(), 0.001);
		
	}
	
	@Test
	public void test1() {
		Optional<BigDecimal> res2 = service.findConversionValue("IN", "CM");
		assertEquals(2.54, res2.get().doubleValue(), 0.001);
	}
	
	@Test
	public void test2() {
		Optional<BigDecimal> res3 = service.findConversionValue("GM", "KG");
		assertEquals(0.001, res3.get().doubleValue(), 0.001);
	}

	
	@Test
	public void test3() {
		Optional<BigDecimal> res4 = service.findConversionValue("KM", "MM");
		assertEquals(1000000, res4.get().intValue());
	}

	
	@Test
	public void test4() {
		Optional<BigDecimal> res5 = service.findConversionValue("OZ", "KG");
		assertEquals(0.02835, res5.get().doubleValue(), 0.001);
	}

	
	@Test
	public void test5() {
		Optional<BigDecimal> res6 = service.findConversionValue("CM", "KG");
		assertEquals(Optional.empty(), res6);
	}

	@Test
	public void test6() {
		Optional<BigDecimal> res7 = service.findConversionValue("CM", "FT");
		assertEquals(0.0328084, res7.get().doubleValue(), 0.001);
	}
}
