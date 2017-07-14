package rpg.testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import rpg.utility.BackpackIDGenerator;

public class BackpackIDGeneratorTest {
	
	private static final double DELTA = 10E3;
	private BackpackIDGenerator generator1,generator2,generator3,generator4;
	
	
	@Before
	public void setup(){
		generator1 = new BackpackIDGenerator();
		generator2 = new BackpackIDGenerator(70);
		generator3 = new BackpackIDGenerator(5);
		generator4 = new BackpackIDGenerator(62);
		
		
	}
	
	@Test
	public void validPositionTest() {
		assertEquals(generator1.getPosition(),0);
		assertEquals(generator2.getPosition(),0);
		assertEquals(generator3.getPosition(),5);
		assertEquals(generator4.getPosition(),62);
	}
	
	@Test
	public void stessTest() {
		for (int i=0;i<63;i++){
			assertEquals(generator1.generateID(),BackpackIDGenerator.calculateBinomialCoefficient(i));
		}
	}
	
	@Test
	public void stessTest2() {
		for (int i=0;i<63;i++){
			assertEquals(BackpackIDGenerator.calculateBinomialCoefficient(i),Math.pow(2, i),DELTA);
		}
	}
	
	@Test
	public void overflow() {
		assertEquals(generator4.generateID(),4611686018427387392L);
		assertEquals(generator4.generateID(),1);
		
	}
	
	
	
}
