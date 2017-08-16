package rpg.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import rpg.utility.WeaponIDGenerator;

public class WeaponIDGeneratorTest {

	private static WeaponIDGenerator generator,overflow,illegalCounter;
	
	@Before
	public void setup(){
		generator = new WeaponIDGenerator();
		overflow = new WeaponIDGenerator(Long.MAX_VALUE/6-1);
		illegalCounter = new WeaponIDGenerator(Long.MAX_VALUE-1000);
	}
	
	@Test
	public void stressTest() {
		for (long i=1;i<Integer.MAX_VALUE;i++){
			assertTrue(generator.generateID()%6==0);
			System.out.println(i);
		}
	}
	
	@Test
	public void overflowLegalCounterTest(){
		assertEquals(overflow.getCounter(),Long.MAX_VALUE/6 - 1);
		assertEquals(overflow.generateID(),(Long.MAX_VALUE/6 - 1)*6+6);	
	}
	
	@Test
	public void overflowLegalCounterTest2(){
		assertEquals(overflow.generateID(),(Long.MAX_VALUE/6 - 1)*6+6);
		assertEquals(overflow.generateID(),12);
		assertEquals(overflow.getCounter(),2);
	}
	
	@Test
	public void illegalCounterTest(){
		assertEquals(illegalCounter.getCounter(),1);
	}
	@Test
	public void isValidCounterTest(){
		assertTrue(WeaponIDGenerator.isValidCounter(Long.MAX_VALUE/6-1));
		assertFalse(WeaponIDGenerator.isValidCounter(Long.MAX_VALUE/6));
		assertFalse(WeaponIDGenerator.isValidCounter(0));
		assertFalse(WeaponIDGenerator.isValidCounter(-1));
	}
}
