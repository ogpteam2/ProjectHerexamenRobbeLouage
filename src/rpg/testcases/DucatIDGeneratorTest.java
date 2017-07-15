package rpg.testcases;

import static org.junit.Assert.*;

import org.junit.Test;

import rpg.utility.DucatIDGenerator;

public class DucatIDGeneratorTest {
	
	private DucatIDGenerator generator = new DucatIDGenerator();
	
	
	@Test
	public void correctnessTest() {
		assertEquals(generator.generateID(),-1L);
	}
	
	@Test
	public void stressTest() {
		for (int i=0;i<Integer.MAX_VALUE;i++){
			assertEquals(generator.generateID(),-1L);
		}
	}

}
