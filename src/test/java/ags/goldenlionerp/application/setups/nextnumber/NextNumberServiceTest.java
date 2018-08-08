package ags.goldenlionerp.application.setups.nextnumber;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

import java.time.YearMonth;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class NextNumberServiceTest {

	@Autowired
	NextNumberService service;
	@Autowired
	EntityManager em;
	
	@Test @Rollback
	public void testFindExisting() {
		NextNumber nn = service.findNextNumber("11000", "BK", YearMonth.of(2018, 4));
		assertNotNull(nn);
		assertEquals("11000", nn.getPk().getCompanyId());
		assertEquals("BK", nn.getPk().getDocumentOrBatchType());
		assertEquals(2018, nn.getPk().getYear());
		assertEquals(4, nn.getPk().getMonth());
		assertEquals(12, nn.getNextSequence());
	}
	
	@Test @Rollback
	public void testFindNotExisting() {
		NextNumber nn = service.findNextNumber("11000", "BK", YearMonth.of(2018, 5));
		assertNotNull(nn);
		assertEquals("11000", nn.getPk().getCompanyId());
		assertEquals("BK", nn.getPk().getDocumentOrBatchType());
		assertEquals(2018, nn.getPk().getYear());
		assertEquals(5, nn.getPk().getMonth());
		assertEquals(1, nn.getNextSequence());
	}
	
	@Test @Rollback
	public void testFindExistButDifferentMethod() {
		NextNumber nn = service.findNextNumber("11000", "RP", YearMonth.of(2018, 3));
		assertNotNull(nn);
		assertEquals("11000", nn.getPk().getCompanyId());
		assertEquals("RP", nn.getPk().getDocumentOrBatchType());
		assertEquals(2018, nn.getPk().getYear());
		assertEquals(1, nn.getPk().getMonth());
		assertEquals(0, nn.getNextSequence());
	}
	
	@Test @Rollback
	public void testFindExistButDifferentMethod2() {
		NextNumber nn = service.findNextNumber("11000", "P", YearMonth.of(2018, 3));
		assertNotNull(nn);
		assertEquals("00000", nn.getPk().getCompanyId());
		assertEquals("P", nn.getPk().getDocumentOrBatchType());
		assertEquals(9999, nn.getPk().getYear());
		assertEquals(1, nn.getPk().getMonth());
		assertEquals(162, nn.getNextSequence());
	}
	
	@Test @Rollback
	public void testIncrement() {
		NextNumber nn = service.findNextNumber("11000", "P", YearMonth.now());
		
		em.clear();
		service.incrementNextNumber("11000", "P", YearMonth.now());
		NextNumber nn2 = service.findNextNumber("11000", "P", YearMonth.now());
		
		assertEquals(nn.getPk().getCompanyId()			, nn2.getPk().getCompanyId());
		assertEquals(nn.getPk().getDocumentOrBatchType(), nn2.getPk().getDocumentOrBatchType());
		assertEquals(nn.getPk().getYear()				, nn2.getPk().getYear());
		assertEquals(nn.getPk().getMonth()				, nn2.getPk().getMonth());
		assertEquals(nn.getNextSequence() + 1			, nn2.getNextSequence());
	}
	
	@Test @Rollback
	public void testDocumentNo_CM() {
		String docNo = service.findNextDocumentNumber("11000", "BK", YearMonth.of(2018, 4));
		assertEquals("180400012", docNo);
	}
	
	@Test @Rollback
	public void testDocumentNo_CM_NotExisting() {
		String docNo = service.findNextDocumentNumber("11000", "BK", YearMonth.of(2018, 5));
		assertEquals("180500001", docNo);
	}
	
	@Test @Rollback
	public void testDocumentNo_CY() {
		assumeTrue(false); //skip first because not sure how behavior should be implemented
		String docNo = service.findNextDocumentNumber("11000", "RP", YearMonth.of(2018, 4));
		assertEquals("????", docNo);
	}
	
	@Test @Rollback
	public void testDocumentNo_NR() {
		assumeTrue(false); //skip first because not sure how behavior should be implemented
		String docNo = service.findNextDocumentNumber("11000", "P", YearMonth.of(2018, 4));
		assertEquals("????", docNo);
	}

	@Test @Rollback
	public void testDocumentNo_YR() {
		assumeTrue(false); //skip first because not sure how behavior should be implemented
		String docNo = service.findNextDocumentNumber("11000", "??", YearMonth.of(2018, 4));
		assertEquals("????", docNo);
	}
	
	@Test @Rollback
	public void testDocumentNo_MO() {
		assumeTrue(false); //skip first because not sure how behavior should be implemented
		String docNo = service.findNextDocumentNumber("11000", "??", YearMonth.of(2018, 4));
		assertEquals("????", docNo);
	}
}
