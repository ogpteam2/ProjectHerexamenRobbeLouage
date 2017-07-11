package rpg.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import rpg.value.Unit;
import rpg.value.Weight;

public class WeightTest {

	private static final double DELTA = 1E-15;
	static Unit kilogram, gram, pound;
	static double zero,one,two,three,four,five;
	static double number1,number2,number3,number4,number5,number6;
	static Weight weight1,weight2,weight3,weight4,weight5,weight6,weight7,weight8,weight9;
	
	 @Before
	 public void setup(){
		 kilogram = Unit.kg;
		 gram = Unit.g;
		 pound = Unit.lbs;
		 zero = 0.0;
		 one = 1.0;
		 two = 2.0;
		 three = 3.0;
		 four = 4.0;
		 five = 5.0;
		 number1 = 0.5;
		 number2 = 0.7555;
		 number3 = 2.88;
		 number4 = 8.5454533353;
		 number5 = 45456.546545646;
		 number6 = 51534534.12121;
		 weight1 = new Weight();
		 weight2 = new Weight(one,kilogram);
		 weight3 = new Weight(two,gram);
		 weight4 = new Weight(three,pound);
		 weight5 = new Weight(number1,kilogram);
		 weight6 = new Weight(number2,gram);
		 weight7 = new Weight(number3,pound);
		 weight8 = new Weight(number4,kilogram);
		 weight9 = new Weight(number6);
	 }
	
	 @Test 
	 public void constructor1Fail(){
		 Weight weight = new Weight(-100,Unit.lbs);
		 assertEquals(weight.getNumeral(),zero,DELTA);
		 assertEquals(weight.getUnit(),Unit.kg);
	 }
	 
	 
	@Test
	public void thirdConstructorTest() {
		assertEquals(weight1.getNumeral(),1.0,DELTA);
		assertEquals(weight1.getUnit(),kilogram);
	}
	
	@Test
	public void secondConstructorTest() {
		assertEquals(weight9.getNumeral(),number6,DELTA);
		assertEquals(weight9.getUnit(),kilogram);
	}
	
	@Test
	public void firstConstructorTest() {
		assertEquals(weight2.getNumeral(),one,DELTA);
		assertEquals(weight2.getUnit(),kilogram);
	}
	
	@Test 
	public void isValidNumeralTest1(){
		assertFalse(Weight.isValidNumeral(-10));
		assertTrue(Weight.isValidNumeral(0.0));
		assertTrue(Weight.isValidNumeral(1.0));
	}


	
	
	
	
	
	

}
