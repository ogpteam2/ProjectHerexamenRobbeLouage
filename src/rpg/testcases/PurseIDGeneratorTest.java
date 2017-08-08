package rpg.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import rpg.utility.PurseIDGenerator;

public class PurseIDGeneratorTest {
		
	private static long[] correctFibonacci = new long[] {1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987};
	private PurseIDGenerator generator;
	
	@Before
	public void setup(){
		generator = new PurseIDGenerator();
	}
	
	@Test
	public void correctnessTest() {
		for (long i:correctFibonacci){
			assertEquals(generator.generateID(),i);
		}
	}
	
	@Test
	public void overflowTest(){
		for (int i=0;i<91;i++){ // should reset after 91 iterations
			generator.generateID();
		}
		assertEquals(generator.generateID(),1);
	}

}
