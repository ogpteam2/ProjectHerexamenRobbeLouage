package rpg.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import rpg.value.Unit;
import rpg.value.Weight;

public class WeightTest {

	private static final double DELTA = 1E-5;
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
	
	@Test
	public void isValidUnitTest(){
		Unit unit = null;
		Unit unit2 = Unit.kg;
		assertFalse(Weight.isValidUnit(unit));
		assertTrue(Weight.isValidUnit(unit2));
	}	
	
	@Test (expected = IllegalArgumentException.class)
	public void toUnitFailTest(){
		weight7.toUnit(null);
	}
	
	@Test 
	public void toUnitSameTest(){
		Weight newWeight = weight7.toUnit(Unit.lbs);
		assertEquals(newWeight,weight7);
	}
	
	@Test 
	public void toUnitOtherTest(){
		Weight newWeight = weight2.toUnit(Unit.g);
		assertEquals(newWeight,new Weight(1000,Unit.g));
		Weight newWeight2 = weight2.toUnit(Unit.lbs);
		assertEquals(newWeight2.getNumeral(),new Weight(2.20462262,Unit.lbs).getNumeral(),DELTA);
		Weight newWeight3 = weight8.toUnit(Unit.lbs);
		assertEquals(newWeight3,new Weight(18.839499736965912,Unit.lbs));
	}
	
	@Test
	public void compareTest(){
		assertEquals(weight1.compareTo(weight2),0);
		assertEquals(weight1.compareTo(weight3),1);
		assertEquals(weight1.compareTo(weight4),-1);
		assertEquals(weight1.compareTo(weight5),1);
		assertEquals(weight6.compareTo(weight7),-1);
		assertEquals(weight6.compareTo(weight8),-1);
		assertEquals(weight6.compareTo(weight9),-1);
	}
	
	@Test
	public void equalsTest(){
		assertTrue(weight1.equals(weight2));
		assertFalse(weight1.equals(new Weight(1000,Unit.g)));
		assertFalse(weight1.equals(weight3));
		assertFalse(weight1.equals(weight4));
	}
	
	
	@Test
	public void hasSameValueTest1(){
		assertTrue(weight1.hasSameValue(weight2));
		assertTrue(weight1.hasSameValue(new Weight(1000,Unit.g)));
		assertTrue(weight1.hasSameValue(new Weight(2.20462262185 ,Unit.lbs)));
	}	
	
	@Test
	public void addTest1(){
		Weight newWeight1 = weight1.add(weight2);
		assertEquals(newWeight1,new Weight(2,Unit.kg));
		Weight newWeight2 = weight1.add(weight3);
		assertEquals(newWeight2,new Weight(1.002,Unit.kg));
		Weight newWeight3 = weight7.add(weight8);
		assertEquals(newWeight3,new Weight(21.71949973696591,Unit.lbs));
	}
	
	@Test
	public void substractTest1(){
		Weight newWeight1 = weight1.substract(weight2);
		assertEquals(newWeight1,new Weight(0,Unit.kg));
		Weight newWeight2 = weight1.substract(weight3);
		assertEquals(newWeight2,new Weight(0.998,Unit.kg));
		Weight newWeight3 = weight8.substract(weight7);
		assertEquals(newWeight3,new Weight(7.239107309700725,Unit.kg));
	}
	
	@Test
	public void substractTest2(){
		Weight newWeight1 = weight3.substract(weight9); // should be less than 0
		assertEquals(newWeight1,new Weight(0.0,Unit.kg)); // catches it
	}

	@Test 
	public void timesTest1(){
		Weight newWeight1 = weight1.times(weight2);
		assertEquals(newWeight1,weight1);
		Weight newWeight2 = weight1.times(weight3);
		assertEquals(newWeight2,new Weight(0.002,Unit.kg));
		Weight newWeight3 = weight1.times(weight5);
		assertEquals(newWeight3,new Weight(0.5,Unit.kg));
		Weight newWeight4 = weight1.times(Weight.kg_0);
		assertEquals(newWeight4,new Weight(0.0,Unit.kg));
		Weight newWeight5 = weight3.times(weight8);
		assertEquals(newWeight5,new Weight(17090.906670599998,Unit.g));		
	}
	
	@Test 
	public void timesTest2(){
		Weight newWeight1 = weight1.times(1000);
		assertEquals(newWeight1,new Weight(1000,Unit.kg));
		Weight newWeight2 = weight3.times(500);
		assertEquals(newWeight2,(new Weight(1,Unit.kg)).toUnit(Unit.g));
		Weight newWeight3 = weight9.times(0);
		assertEquals(newWeight3,new Weight(00,Unit.kg));
	}
	
	@Test 
	public void devideTest(){
		Weight newWeight1 = weight1.divide(weight1);
		assertEquals(newWeight1,new Weight(1,Unit.kg));
		Weight newWeight2 = weight1.divide(weight3);
		assertEquals(newWeight2,new Weight(500,Unit.kg));
		Weight newWeight3 = weight7.divide(weight8);
		assertEquals(newWeight3,new Weight(0.1528703012399533,Unit.lbs));
	}
	
	@Test 
	public void devideByZeroTest(){
		Weight newWeight0 = weight1.divide(new Weight(0.0,Unit.kg));
		assertEquals(newWeight0,weight1);
	}
	
}
