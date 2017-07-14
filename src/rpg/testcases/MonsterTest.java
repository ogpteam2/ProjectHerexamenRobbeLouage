package rpg.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rpg.Hero;
import rpg.Monster;
import rpg.value.Unit;
import rpg.value.Weight;

public class MonsterTest {

	private Monster invalidName1,invalidName2,invalidName3,validName1,validName2,validName3;
	private Monster capacity1,capacity2,capacity3,capacity4;
	
	@Before
	public void setup(){
		capacity1 = new Monster("James",0,-10);
		capacity2 = new Monster("James",0,0);
		capacity3 = new Monster("James",0,10);
		capacity4 = new Monster("James",0,10000);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void isValidName1() {
		invalidName1 = new Monster(null,0,0);
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName2() {
		invalidName2 = new Monster("bob",0,0);
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName3() {
		invalidName3 = new Monster("b*b",0,0);
	}
	@Test 
	public void isValidName4() {
		validName1 = new Monster("James",0,0);
		assertEquals(validName1.getName(),"James");
	}
	@Test 
	public void isValidName5() {
		validName2 = new Monster("H'erld",0,0);
		assertEquals(validName2.getName(),"H'erld");
	}
	@Test 
	public void isValidName6() {
		validName3 = new Monster("Blub",0,0);
		assertEquals(validName3.getName(),"Blub");
	}
	@Test (expected = IllegalArgumentException.class)
	public void capacityTest1() {
		capacity1.getCapacity(null);
	}	
	@Test 
	public void capacityTest2() {
		assertEquals(capacity1.getCapacity(Unit.kg),Weight.kg_0);
		assertEquals(capacity1.calculateCapacity(capacity1.getRawStrength(),Unit.kg),Weight.kg_0);
		
	}	
	@Test 
	public void capacityTest3() {
		assertEquals(capacity2.getCapacity(Unit.kg),Weight.kg_0);
		assertEquals(capacity2.getCapacity(Unit.kg),Weight.kg_0);
		assertEquals(capacity3.getCapacity(Unit.kg),new Weight(90,Unit.kg));
		assertEquals(capacity4.getCapacity(Unit.kg),new Weight(90000,Unit.kg));
	}
}
