package ags.goldenlionerp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ags.goldenlionerp.application.system.businessunit.BusinessUnit;
import ags.goldenlionerp.application.system.businessunit.BusinessUnitRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BURepoTest {

	@Autowired
	BusinessUnitRepository repo;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		BusinessUnit bu = repo.findById("100").get();
		assertFalse(bu.getRelatedBusinessUnit() == null);
		assertEquals(repo.count(), 4);
	}

}
