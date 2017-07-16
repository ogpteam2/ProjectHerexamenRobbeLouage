package rpg.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rpg.Hero;
import rpg.Monster;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Weapon;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

public class MonsterTest {

	private Monster invalidName1,invalidName2,invalidName3,validName1,validName2,validName3;
	private Monster capacity1,capacity2,capacity3,capacity4;
	private Monster monster1,monster2,monster3,monster4;
	private Anchorpoint[] valid,invalid1,invalid2,invalid3;
	private Weapon weapon1,weapon2,weapon3,weapon4,weapon5;
	
	
	@Before
	public void setup(){
		capacity1 = new Monster("James",0,-10);
		capacity2 = new Monster("James",0,0);
		capacity3 = new Monster("James",0,10);
		capacity4 = new Monster("James",0,10000);
		weapon1 = new Weapon(new Weight(50,Unit.kg),0);
		weapon2 = new Weapon(new Weight(100,Unit.kg),0);
		weapon3 = new Weapon(new Weight(150,Unit.kg),0);
		weapon4 = new Weapon(new Weight(200,Unit.kg),0);
		weapon5 = new Weapon(new Weight(250,Unit.kg),0);
		valid = new Anchorpoint[5];
		initializeValid();
		invalid1 = new Anchorpoint[4];
		initializeInvalid1();
		invalid2 = new Anchorpoint[5];
		initializeInvalid2();
	}
	
	private void initializeValid(){
		valid[0] = new Anchorpoint(AnchorpointType.BACK,weapon1);
		valid[1] = new Anchorpoint(AnchorpointType.BODY,weapon2);
		valid[2] = new Anchorpoint(AnchorpointType.BELT,weapon3);
		valid[3] = new Anchorpoint(AnchorpointType.LEFT,weapon4);
		valid[4] = new Anchorpoint(null,null);
	}
	private void initializeInvalid1(){
		invalid1[0] = new Anchorpoint(AnchorpointType.BACK,weapon1);
		invalid1[1] = new Anchorpoint(AnchorpointType.BODY,weapon2);
		invalid1[2] = new Anchorpoint(AnchorpointType.BELT,weapon3);
		invalid1[3] = new Anchorpoint(AnchorpointType.LEFT,weapon4);
	}
	private void initializeInvalid2(){
		invalid2[0] = new Anchorpoint(AnchorpointType.BACK,weapon1);
		invalid2[1] = new Anchorpoint(AnchorpointType.BODY,weapon2);
		invalid2[2] = new Anchorpoint(AnchorpointType.BELT,weapon3);
		invalid2[3] = new Anchorpoint(AnchorpointType.LEFT,weapon4);
		invalid2[4] = new Anchorpoint(AnchorpointType.LEFT,weapon5);
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
	@Test
	public void generatorTest(){
		for (int i=0;i<10000;i++){
			assertTrue(Monster.generateNbOfAnchorpoints()>=0);
			assertTrue(Monster.generateNbOfAnchorpoints()<=AnchorpointType.NbOfAnchorpointTypes());
		}
	}
	@Test
	public void constructor2Test(){
		monster1 = new Monster("Jared", 50L, 100);
		assertTrue(monster1.getFreeAnchorpoints().size()>=0);
		assertTrue(monster1.getFreeAnchorpoints().size()<=5);
		assertEquals(monster1.getFreeAnchorpoints().size(),monster1.getNbOfAnchorpoints());
	}
	@Test
	public void isValidListTest(){
		monster1 = new Monster("Jared", 50L, 100);
		assertTrue(monster1.isValidAnchorpointList(valid));
		assertFalse(monster1.isValidAnchorpointList(invalid1));
		assertFalse(monster1.isValidAnchorpointList(invalid2));
	}
	
	@Test
	public void addTest(){
		monster1 = new Monster("Jared", 50L, 100);
		monster1.addItemAt(AnchorpointType.BACK, weapon1);
		if (monster1.getNbItems() == 1){
			assertEquals(monster1.getItemAt(AnchorpointType.BACK),weapon1);
			assertEquals(monster1.getItemAt(AnchorpointType.BACK).getHolder(),monster1);
		}
		else {
			assertEquals(monster1.getNbItems(),0);
			assertEquals(monster1.getAnchors()[AnchorpointType.BACK.ordinal()].getAnchorpointType(),null);
		}
	}
	
	
}
