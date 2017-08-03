package rpg.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import rpg.*;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Weapon;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

public class HeroTest {

	private Hero invalidName1,invalidName2,invalidName3,invalidName4,invalidName5,validName1,validName2,validName3;
	private Hero hitpoints1,hitpoints2,hitpoints3,hitpoints4;
	private Hero strength1,strength2,strength3,strength4,strength5,strength6,strength7,strength8;
	private static final double DELTA = 10E-10;
	private Hero capacity1,capacity2,capacity3,capacity4,capacity5,capacity6,capacity7,capacity8,capacity9,capacity10,capacity11,capacity12,capacity13,capacity14,capacity15;
	// bidirectional link test
	private Hero link1;
	private Weapon weapon1,weapon2,weapon3,weapon4,weapon5,weapon6;
	private Anchorpoint[] valid,invalid1,invalid2,invalid3;
	
	
	
	
	@Before
	public void setup(){
		hitpoints1 = new Hero("IllegalMaximumHitpoints",-10,0);
		hitpoints2 = new Hero("IllegalMaximumHitpoints",0,0);
		hitpoints3 = new Hero("LegalMaximumHitpoints",7,0);
		hitpoints4 = new Hero("IlLegalMaximumHitpoints",10,0);
		strength1 = new Hero("James",0,-10.164456);
		strength2 = new Hero("James",0,-10.165456);
		strength3 = new Hero("James",0,-0.21321213);
		strength4 = new Hero("James",0,0.123456789123456789);
		strength5 = new Hero("James",0,456546);
		strength6 = new Hero("James",0,123456789123456789123456789.65165546);
		strength7 = new Hero("James",0,Double.MAX_VALUE);
		strength8 = new Hero("James",0,Double.MIN_VALUE);
		capacity1 = new Hero("James",0,-1);
		capacity2 = new Hero("James",0,0);
		capacity3 = new Hero("James",0,1);
		capacity4 = new Hero("James",0,1.01);
		capacity5 = new Hero("James",0,5);
		capacity6 = new Hero("James",0,10);
		capacity7 = new Hero("James",0,10.01);
		capacity8 = new Hero("James",0,18.5);
		capacity9 = new Hero("James",0,20);
		capacity10 = new Hero("James",0,25);
		capacity11 = new Hero("James",0,35);
		capacity12 = new Hero("James",0,45);
		capacity13 = new Hero("James",0,100);
		capacity14 = new Hero("James",0,1000);
		capacity15 = new Hero("James",0,5097);
		link1 = new Hero("James",100L,40);
		weapon1 = new Weapon(new Weight(50,Unit.kg),0);
		weapon2 = new Weapon(new Weight(100,Unit.kg),0);
		weapon3 = new Weapon(new Weight(150,Unit.kg),0);
		weapon4 = new Weapon(new Weight(200,Unit.kg),0);
		weapon5 = new Weapon(new Weight(250,Unit.kg),0);
		weapon6 = new Weapon(new Weight(250,Unit.kg),0);
		valid = new Anchorpoint[5];
		initializeValid();
		invalid1 = new Anchorpoint[4];
		initializeInvalid1();
		invalid2 = new Anchorpoint[5];
		initializeInvalid2();
		invalid3 = new Anchorpoint[5];
		initializeInvalid2();
	}
	
	private void initializeValid(){
		valid[0] = new Anchorpoint(AnchorpointType.BACK,weapon1);
		valid[1] = new Anchorpoint(AnchorpointType.BODY,weapon2);
		valid[2] = new Anchorpoint(AnchorpointType.BELT,weapon3);
		valid[3] = new Anchorpoint(AnchorpointType.LEFT,weapon4);
		valid[4] = new Anchorpoint(AnchorpointType.RIGHT,weapon5);
	}
	private void initializeInvalid1(){
		invalid1[0] = new Anchorpoint(AnchorpointType.BACK,weapon1);
		invalid1[1] = new Anchorpoint(AnchorpointType.BODY,weapon2);
		invalid1[2] = new Anchorpoint(AnchorpointType.BELT,weapon3);
		invalid1[3] = new Anchorpoint(null,weapon4);
	}
	private void initializeInvalid2(){
		invalid2[0] = new Anchorpoint(AnchorpointType.BACK,weapon1);
		invalid2[1] = new Anchorpoint(AnchorpointType.BODY,weapon2);
		invalid2[2] = new Anchorpoint(AnchorpointType.BELT,weapon3);
		invalid2[3] = new Anchorpoint(AnchorpointType.LEFT,weapon4);
		invalid2[4] = new Anchorpoint(AnchorpointType.LEFT,weapon5);
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName0() {
		new Hero(null,0,0);
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName1() {
		invalidName1 = new Hero("bob",0,0);
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName2() {
		invalidName2 = new Hero("James:",0,0);
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName3() {
		invalidName3 = new Hero("James 'O' H'ara",0,0);
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName4() {
		invalidName4 = new Hero("Erik:O",0,0);
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName5() {
		invalidName5 = new Hero("B*b",0,0);
	}
	@Test
	public void isValidName6() {
		validName1 = new Hero("James 'O' Hara",0,0);
		assertEquals(validName1.getName(),"James 'O' Hara");
	}
	@Test
	public void isValidName7() {
		validName2 = new Hero("James: 'O' Hara",0,0);
		assertEquals(validName2.getName(),"James: 'O' Hara");
	}	
	@Test
	public void isValidName8() {
		validName3 = new Hero("James",0,0);
		assertEquals(validName3.getName(),"James");
	}
	@Test
	public void gettersAndSettersHitpoints(){
		assertEquals(hitpoints1.getCurrentHitpoints(),-10L);
		assertEquals(hitpoints1.getMaximumHitpoints(),-10L);
		hitpoints1.setMaximumHitpoints(13L);
		assertEquals(hitpoints1.getCurrentHitpoints(),-10L);
		assertEquals(hitpoints1.getMaximumHitpoints(),13L);
	}
	@Test
	public void illegalMaxHitpointsTest() {
		assertFalse(Mobile.isValidMaximumHitpoint(hitpoints1.getMaximumHitpoints()));
		assertFalse(Mobile.isValidMaximumHitpoint(hitpoints2.getMaximumHitpoints()));
		assertFalse(Mobile.isValidMaximumHitpoint(hitpoints4.getMaximumHitpoints()));
	}
	@Test
	public void legalMaxHitpointsTest() {
		assertTrue(Mobile.isValidMaximumHitpoint(hitpoints3.getMaximumHitpoints()));
	}
	@Test
	public void legalCurrentHitpointsTest() {
		for (int i=0;i<=7;i++){
			assertTrue(hitpoints3.canHaveAsCurrentHitpoints(i));
		}
	}
	@Test
	public void illegalCurrentHitpointsTest() {
		assertFalse(hitpoints3.canHaveAsCurrentHitpoints(-1));
		assertFalse(hitpoints3.canHaveAsCurrentHitpoints(8));
		assertFalse(hitpoints3.canHaveAsCurrentHitpoints(9));
	}
	
	@Test
	public void gettersAndSettersRawStrength(){
		assertEquals(strength1.getRawStrength(),-10.16,DELTA);
		assertEquals(strength2.getRawStrength(),-10.17,DELTA);
		assertEquals(strength3.getRawStrength(),-0.21,DELTA);
		assertEquals(strength4.getRawStrength(),0.12,DELTA);
		assertEquals(strength5.getRawStrength(),456546.00,DELTA);
		assertEquals(strength6.getRawStrength(),123456789123456789123456789.65,DELTA);
		assertEquals(strength7.getRawStrength(),Double.MAX_VALUE,DELTA);
		assertEquals(strength8.getRawStrength(),Double.MIN_VALUE,DELTA);
	}
	
	@Test
	public void multiplyFailTest(){
		strength7.multiplyRawStrength(1000000000);
		strength8.multiplyRawStrength(1000000000);
		assertEquals(strength7.getRawStrength(),Double.MAX_VALUE,DELTA);
		assertEquals(strength8.getRawStrength(),Double.MIN_VALUE,DELTA);
		
	}
	
	@Test
	public void multiplyTest0(){
		double previous = strength1.getRawStrength();
		strength1.multiplyRawStrength(7);
		assertEquals(strength1.getRawStrength(),previous*7,DELTA);
	}
	
	@Test
	public void multiplyTest1(){
		double previous = strength2.getRawStrength();
		strength2.multiplyRawStrength(-7);
		assertEquals(strength2.getRawStrength(),previous*-7,DELTA);
	}
	
	@Test
	public void multiplyTest2(){
		double previous = strength4.getRawStrength();
		strength4.multiplyRawStrength(7);
		assertEquals(strength4.getRawStrength(),previous*7,DELTA);
	}
	
	@Test
	public void multiplyTest3(){
		double previous = strength6.getRawStrength();
		strength6.multiplyRawStrength(10);
		assertEquals(strength6.getRawStrength(),previous*10,DELTA);
	}
	
	@Test
	public void multiplyTest4(){
		double previous = strength7.getRawStrength();
		strength7.multiplyRawStrength(10);
		assertEquals(strength7.getRawStrength(),previous,DELTA);
	}
	
	@Test
	public void multiplyTest5(){
		double previous = strength8.getRawStrength();
		strength8.multiplyRawStrength(10);
		assertEquals(strength8.getRawStrength(),previous,DELTA);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getCapacityUnitFailTest(){
		capacity1.getCapacity(null);
	}
	
	@Test 
	public void getCapacityUnitFailTest2(){
		assertEquals(capacity1.calculateCapacity(1000, null),Weight.kg_0);

	}
	
	@Test
	public void getCapacityUnitTest(){
		assertEquals(capacity1.getCapacity(Unit.kg),Weight.kg_0);
		assertEquals(capacity1.getCapacity(Unit.g),Weight.g_0);
		assertEquals(capacity1.getCapacity(Unit.lbs),Weight.lbs_0);
	}
	
	@Test
	public void getCapacityTest2(){
		assertEquals(capacity2.getCapacity(Unit.kg),Weight.kg_0);
		assertEquals(capacity2.getCapacity(Unit.g),Weight.g_0);
		assertEquals(capacity2.getCapacity(Unit.lbs),Weight.lbs_0);
	}
	@Test
	public void getCapacityTest3(){
		assertEquals(capacity3.getCapacity(Unit.kg),new Weight(10,Unit.kg));
		assertEquals(capacity3.getCapacity(Unit.g),new Weight(10000,Unit.g));
		assertEquals(capacity3.getCapacity(Unit.lbs),new Weight(22.0462262185,Unit.lbs));
	}
	
	@Test
	public void calculateCapacityTest(){
		assertEquals(capacity1.getCapacity(Unit.kg),Weight.kg_0);
		assertEquals(capacity2.getCapacity(Unit.kg),Weight.kg_0);
		assertEquals(capacity3.getCapacity(Unit.kg),new Weight(10,Unit.kg));
		assertEquals(capacity4.getCapacity(Unit.kg),new Weight(10.1,Unit.kg));
		assertEquals(capacity5.getCapacity(Unit.kg),new Weight(50.0,Unit.kg));
		assertEquals(capacity6.getCapacity(Unit.kg),new Weight(100,Unit.kg));
		assertEquals(capacity7.getCapacity(Unit.kg),new Weight(115,Unit.kg));
		assertEquals(capacity8.getCapacity(Unit.kg),new Weight(350,Unit.kg));
		assertEquals(capacity9.getCapacity(Unit.kg),new Weight(400,Unit.kg));
	}
	@Test
	public void calculateCapacityTest2(){
		assertEquals(capacity10.getCapacity(Unit.kg),new Weight(800,Unit.kg));
		assertEquals(capacity11.getCapacity(Unit.kg),new Weight(3200,Unit.kg));
		assertEquals(capacity12.getCapacity(Unit.kg),new Weight(12800,Unit.kg));
	}
	@Test
	public void calculateCapacityTest3(){
		assertEquals(capacity13.getCapacity(Unit.kg),new Weight(26214400,Unit.kg));
		assertEquals(capacity14.getCapacity(Unit.kg),new Weight(4.0173451106474757E61,Unit.kg));
	}
	@Test
	public void calculateCapacityTest4(){
		assertEquals(capacity15.getCapacity(Unit.kg),new Weight(Double.POSITIVE_INFINITY,Unit.kg));
		// method gives infinity if strength >= 5097
	}
	
	@Test 
	public void constructor2Test(){
		assertEquals(link1.getNbItems(),0);
		assertEquals(link1.getCapacity(),new Weight(6400,Unit.kg));
		ArrayList<AnchorpointType> all = new ArrayList<AnchorpointType>();
		all.add(AnchorpointType.BODY);all.add(AnchorpointType.BACK);
		all.add(AnchorpointType.BELT);all.add(AnchorpointType.RIGHT);
		all.add(AnchorpointType.LEFT);
		assertEquals(link1.getFreeAnchorpoints(),all);
		assertEquals(link1.getTotalWeight(Unit.kg),Weight.kg_0);
		for (Anchorpoint anchor:link1.generateAnchorpoints()){
			assertEquals(anchor.getItem(),null);
		}
		
	}
	
	@Test
	public void differentTest(){
		assertTrue(link1.canHaveAsAnchorpointList(valid));
		assertFalse(link1.canHaveAsAnchorpointList(invalid1));
		assertFalse(link1.canHaveAsAnchorpointList(invalid3));
		assertFalse(link1.canHaveAsAnchorpointList(invalid2));
	}
	
	@Test
	public void constructor1Test(){
		Hero okay = new Hero("Jamevhs",10L,45,valid);
		for (Anchorpoint anchor:okay.getAnchors()){
			assertEquals(anchor.getAnchorpointType(),anchor.getAnchorpointType());
			assertEquals(anchor.getItem().getHolder().getName(),"Jamevhs");
		}
		assertTrue(okay.hasProperItems());
	}
	
	@Test
	public void nbItemsCheck(){
		assertTrue(link1.canHaveAsNbItems(5));
		assertFalse(link1.canHaveAsNbItems(-1));
		assertTrue(link1.canHaveAsNbItems(0));
	}
	
	@Test
	public void getNbItemsCheck(){
		assertEquals(link1.getNbItems(),0);
		link1.addItemAt(AnchorpointType.BACK, weapon1);
		assertEquals(link1.getNbItems(),1);
		link1.addItemAt(AnchorpointType.BODY, weapon2);
		assertEquals(link1.getNbItems(),2);
		link1.addItemAt(AnchorpointType.BELT, weapon3);
		assertEquals(link1.getNbItems(),3);
		link1.addItemAt(AnchorpointType.LEFT, weapon4);
		assertEquals(link1.getNbItems(),4);
		link1.addItemAt(AnchorpointType.RIGHT, weapon5);
		assertEquals(link1.getNbItems(),5);
	}
	
	@Test
	public void getItemAtTest(){
		link1.addItemAt(AnchorpointType.BACK, weapon1);
		assertEquals(link1.getItemAt(AnchorpointType.BACK),weapon1);
		assertEquals(weapon1.getHolder(),link1);
	}
	
	@Test
	public void freeAnchorpointsTest(){
		link1.addItemAt(AnchorpointType.BACK, weapon1);
		ArrayList<AnchorpointType> all = new ArrayList<AnchorpointType>();
		all.add(AnchorpointType.BODY);
		all.add(AnchorpointType.BELT);all.add(AnchorpointType.RIGHT);
		all.add(AnchorpointType.LEFT);
		assertEquals(link1.getFreeAnchorpoints(),all);
		link1.addItemAt(AnchorpointType.BODY, weapon2);
		ArrayList<AnchorpointType> all2 = new ArrayList<AnchorpointType>();
		all2.add(AnchorpointType.BELT);all2.add(AnchorpointType.RIGHT);
		all2.add(AnchorpointType.LEFT);
		assertEquals(link1.getFreeAnchorpoints(),all2);
	}
	
	@Test
	public void totalWeight(){
		assertEquals(link1.getTotalWeight(Unit.kg),Weight.kg_0);
		link1.addItemAt(AnchorpointType.BODY, weapon1);
		link1.addItemAt(AnchorpointType.BACK, weapon2);
		link1.addItemAt(AnchorpointType.BELT, weapon3);
		link1.addItemAt(AnchorpointType.LEFT, weapon4);
		link1.addItemAt(AnchorpointType.RIGHT, weapon5);
		assertEquals(link1.getTotalWeight(Unit.kg),new Weight(750,Unit.kg));
	}
	@Test
	public void properTest(){
		Hero hero1 = new Hero("HeroOne",100L,50);
		hero1.addItem(weapon1);
		hero1.addItem(weapon2);
		hero1.addItem(weapon3);
		hero1.addItem(weapon4);
		assertTrue(hero1.hasProperItems());
	}
	@Test
	public void canHaveAsItemAt(){
		Weapon weapon45 = new Weapon(new Weight(500000,Unit.kg),0);
		assertFalse(link1.canHaveAsItemAt(AnchorpointType.BODY, weapon45));
		assertTrue(link1.canHaveAsItemAt(AnchorpointType.BODY, weapon1));
	}
	
	@Test
	public void canAddItemAt(){
		Weapon weapon45 = new Weapon(new Weight(500000,Unit.kg),0);
		assertTrue(link1.canAddItemAt(AnchorpointType.BODY, weapon1));
		link1.addItemAt(AnchorpointType.BODY, weapon1);
		assertFalse(link1.canAddItemAt(AnchorpointType.BODY, weapon2));
		assertFalse(link1.canAddItemAt(AnchorpointType.BODY, weapon1));
		assertFalse(link1.canAddItemAt(AnchorpointType.BODY, weapon45));
	}

	@Test
	public void check(){
		assertFalse(link1.checkItemInAnchors(weapon1));
		link1.addItemAt(AnchorpointType.BACK, weapon1);
		assertTrue(link1.checkItemInAnchors(weapon1));
	}
	
	@Test
	public void removeItem(){
		link1.addItemAt(AnchorpointType.BACK, weapon1);
		assertEquals(weapon1.getHolder(),link1);
		link1.removeItemAt(AnchorpointType.BACK);
		assertEquals(weapon1.getHolder(),null);
		assertFalse(link1.checkItemInAnchors(weapon1));
		
	}
	
	@Test
	public void addItemAt(){
		Weapon weapon6 = new Weapon(null,0);
		Weapon weapon7 = new Weapon(null,0);
		link1.addItemAt(AnchorpointType.BACK, weapon1);
		link1.addItemAt(AnchorpointType.BACK, weapon2);
		link1.addItemAt(AnchorpointType.BODY, weapon3);
		link1.addItemAt(AnchorpointType.BELT, weapon4);
		link1.addItemAt(AnchorpointType.LEFT, weapon5);
		link1.addItemAt(AnchorpointType.RIGHT, weapon6);
		link1.addItemAt(AnchorpointType.RIGHT, weapon7);
		assertTrue(link1.getNbItems()==5);
		assertTrue(link1.checkItemInAnchors(weapon1));
		assertTrue(link1.checkItemInAnchors(weapon3));
		assertTrue(link1.checkItemInAnchors(weapon4));
		assertTrue(link1.checkItemInAnchors(weapon5));
		assertTrue(link1.checkItemInAnchors(weapon6));
		assertFalse(link1.checkItemInAnchors(weapon2));
		assertFalse(link1.checkItemInAnchors(weapon7));
	}
	@Test
	public void addItemTest(){
		Hero hero1 = new Hero("HeroOne",100L,50);
		hero1.addItem(weapon1);
		assertEquals(hero1.getFreeAnchorpoints().size(),4);
		assertTrue(hero1.hasProperItems());
		assertFalse(hero1.checkItemInAnchors(weapon6));	

		
	}
	@Test
	public void transferTest1(){
		Hero hero1 = new Hero("HeroOne",100L,50);
		Hero hero2 = new Hero("HeroTwo",100L,50);
		hero1.addItemAt(AnchorpointType.BACK,weapon1);
		hero1.addItemAt(AnchorpointType.BELT,weapon2);
		hero1.transfersItemToAnchor(AnchorpointType.BACK, hero2, AnchorpointType.BACK);
		hero1.transfersItemToAnchor(AnchorpointType.BELT, hero2, AnchorpointType.BACK);
		assertEquals(hero1.getNbItems(),1);
		assertEquals(hero2.getNbItems(),1);
		assertFalse(hero1.checkItemInAnchors(weapon1));
		assertTrue(hero2.checkItemInAnchors(weapon1));
		assertTrue(hero1.hasProperItems());
		assertTrue(hero2.hasProperItems());
	}
	@Test
	public void transferTest2(){
		Hero hero1 = new Hero("HeroOne",100L,50);
		Hero hero2 = new Hero("HeroTwo",100L,50);
		hero1.addItemAt(AnchorpointType.BACK, weapon1);
		hero1.transfersItemToAnchor(AnchorpointType.BACK, hero2);
		assertFalse(hero1.checkItemInAnchors(weapon1));
		assertTrue(hero2.checkItemInAnchors(weapon1));
	}
	@Test
	public void transferTest3(){
		Hero hero1 = new Hero("HeroOne",100L,50);
		Hero hero2 = new Hero("HeroTwo",100L,50);
		hero2.addItem(weapon1);
		hero2.addItem(weapon2);
		hero2.addItem(weapon3);
		hero2.addItem(weapon4);
		hero2.addItem(weapon5);
		hero1.addItemAt(AnchorpointType.BACK,weapon6);
		hero1.transfersItemToAnchor(AnchorpointType.BACK, hero2);
		assertEquals(hero1.getNbItems(),1);
		assertEquals(hero2.getNbItems(),5);
		assertTrue(hero1.checkItemInAnchors(weapon6));
		assertFalse(hero2.checkItemInAnchors(weapon6));
	}

}
