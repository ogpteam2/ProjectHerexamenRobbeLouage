package rpg.testcases;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import rpg.value.Unit;

public class UnitTest {
	
	private static final double DELTA = 0.0000001;
	static Unit kilogram, gram, pound;
	static double kgTokg, kgTog, kgTolbs, gTog, gTolbs, lbsTolbs,gTokg,lbsTokg,lbsTog;
	
	@Before
	public void setup(){
		kilogram = Unit.kg; 
		gram = Unit.g;
		pound = Unit.lbs;
		kgTokg = 1d;
		gTog = 1d;
		lbsTolbs = 1d;
		kgTog = 1000d;
		kgTolbs = 2.20462262185d;
		gTolbs = 0.00220462262d;
		gTokg = 1/(kgTog);
		lbsTokg = 1/(kgTolbs);
		lbsTog = 1/(gTolbs);
	}
	
	@Test
	public void testDivision() {
		assertEquals(gTokg,0.001,DELTA);
		assertEquals(lbsTokg,0.4535923699997481,DELTA);
		assertEquals(lbsTog,453.5923703803783,DELTA);
	}
	
	@Test
	public void toUnitTestOnes(){
		assertEquals(kilogram.toUnit(kilogram),kgTokg,DELTA);
		assertEquals(gram.toUnit(gram),gTog,DELTA);
		assertEquals(pound.toUnit(pound),lbsTolbs,DELTA);
	}
	
	
	@Test
	public void toUnitTestOthers(){
		assertEquals(kilogram.toUnit(gram),kgTog,DELTA);
		assertEquals(kilogram.toUnit(pound),kgTolbs,DELTA);
		assertEquals(gram.toUnit(pound),gTolbs,DELTA);
	}
	
	@Test 
	public void toUnitTestInversion(){
		assertEquals(gram.toUnit(kilogram),gTokg,DELTA);
		assertEquals(pound.toUnit(kilogram),lbsTokg,DELTA);
		assertEquals(pound.toUnit(gram),lbsTog,DELTA);
	}
	
}
