package rpg.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import rpg.Hero;
import rpg.inventory.Backpack;
import rpg.inventory.Ducat;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

public class HeroContainerTest {

	private Hero hero1,hero2;
	private Backpack backpack1,backpack2,backpack3;
	private Weapon weapon1, weapon2, weapon3, weapon4, weapon5;
	private Purse purse1;
	
	@Before
	public void setup(){
		hero1 = new Hero("Jamjes",100L,50);
		hero2 = new Hero("Jfqamjes",100L,50);
		backpack1 = new Backpack(new Weight(1,Unit.kg),0, new Weight(100,Unit.kg));
		backpack2 = new Backpack(new Weight(1,Unit.kg),0, new Weight(100,Unit.kg));
		backpack3 = new Backpack(new Weight(1,Unit.kg),0, new Weight(100,Unit.kg));
		weapon1 = new Weapon(new Weight(1,Unit.kg),10);
		weapon2 = new Weapon(new Weight(1,Unit.kg),10);
		weapon3 = new Weapon(new Weight(1,Unit.kg),10);
		weapon4 = new Weapon(new Weight(1,Unit.kg),10);
		weapon5 = new Weapon(new Weight(1,Unit.kg),10);
		purse1 = new Purse(new Weight(100,Unit.g), new Weight(5,Unit.kg));
		purse1.addItem(new Ducat());
		purse1.addItem(new Ducat());
	}
	
	@Test
	public void nbTest() {
		backpack1.addItem(weapon1);
		backpack2.addItem(weapon2);
		backpack3.addItem(weapon3);
		backpack1.addItem(backpack2);
		backpack2.addItem(backpack3);
		backpack1.addItem(weapon4);
		hero1.addItemAt(AnchorpointType.BACK, backpack1);
		hero1.addItemAt(AnchorpointType.BELT, purse1);
		hero1.addItemAt(AnchorpointType.BODY, weapon5);
		assertEquals(hero1.getNbItems(),11);
	}
	
	@Test
	public void nbTest2() {
		backpack1.addItem(backpack2);
		backpack2.addItem(backpack3);
		backpack3.addItem(weapon1);
		backpack3.addItem(weapon2);
		backpack3.addItem(weapon3);
		backpack3.addItem(weapon4);
		hero1.addItemAt(AnchorpointType.BACK, backpack1);
		assertEquals(hero1.getNbItems(),7);
	}
	
	@Test
	public void checkTest(){
		backpack1.addItem(weapon1);
		backpack2.addItem(weapon2);
		backpack3.addItem(weapon3);
		backpack1.addItem(backpack2);
		backpack2.addItem(backpack3);
		backpack1.addItem(weapon4);
		hero1.addItem(backpack1);
		assertTrue(hero1.checkItemInAnchors(weapon3));
		assertFalse(hero1.checkItemInAnchors(weapon5));
	}
	@Test
	public void holderTest(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack2.addItem(weapon4);
		backpack2.addItem(weapon5);
		backpack1.addItem(backpack2);
		hero1.addItem(backpack1);
		assertEquals(weapon1.getHolder().getName(),"Jamjes");
		assertEquals(weapon2.getHolder().getName(),"Jamjes");
		assertEquals(weapon3.getHolder().getName(),"Jamjes");
		assertEquals(weapon4.getHolder().getName(),"Jamjes");
		assertEquals(weapon5.getHolder().getName(),"Jamjes");	
	}
	@Test
	public void removeTest(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack2.addItem(weapon4);
		backpack2.addItem(weapon5);
		backpack1.addItem(backpack2);
		hero1.addItemAt(AnchorpointType.BACK,backpack1);
		assertEquals(weapon1.getHolder().getName(),"Jamjes");
		assertEquals(weapon2.getHolder().getName(),"Jamjes");
		assertEquals(weapon3.getHolder().getName(),"Jamjes");
		assertEquals(weapon4.getHolder().getName(),"Jamjes");
		assertEquals(weapon5.getHolder().getName(),"Jamjes");
		hero1.removeItemAt(AnchorpointType.BACK);
		assertEquals(weapon1.getHolder(),null);
		assertEquals(weapon2.getHolder(),null);
		assertEquals(weapon3.getHolder(),null);
		assertEquals(weapon4.getHolder(),null);
		assertEquals(weapon5.getHolder(),null);
	}
	@Test
	public void transferItemToAnchorTest1(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack2.addItem(weapon4);
		backpack2.addItem(weapon5);
		backpack1.addItem(backpack2);
		hero1.addItemAt(AnchorpointType.BACK, backpack1);
		hero1.transfersItemToAnchor(AnchorpointType.BACK, hero2, AnchorpointType.BACK);
		assertFalse(hero1.checkItemInAnchors(backpack1));
		assertTrue(hero2.checkItemInAnchors(backpack1));
		assertTrue(hero2.checkItemInAnchors(backpack2));
		assertEquals(weapon1.getHolder(),hero2);
		assertEquals(weapon2.getHolder(),hero2);
		assertEquals(weapon3.getHolder(),hero2);
		assertEquals(weapon4.getHolder(),hero2);
		assertEquals(weapon5.getHolder(),hero2);
		assertEquals(backpack1.getHolder(),hero2);
		assertEquals(backpack2.getHolder(),hero2);
		assertEquals(hero2.getItemAt(AnchorpointType.BACK),backpack1);
	}
	@Test
	public void transferItemToAnchorTest2(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack2.addItem(weapon4);
		backpack2.addItem(weapon5);
		backpack1.addItem(backpack2);
		hero1.addItemAt(AnchorpointType.BACK, backpack1);
		hero1.transfersItemToAnchor(AnchorpointType.BACK, hero2);
		assertFalse(hero1.checkItemInAnchors(backpack1));
		assertTrue(hero2.checkItemInAnchors(backpack1));
		assertTrue(hero2.checkItemInAnchors(backpack2));
		assertEquals(weapon1.getHolder(),hero2);
		assertEquals(weapon2.getHolder(),hero2);
		assertEquals(weapon3.getHolder(),hero2);
		assertEquals(weapon4.getHolder(),hero2);
		assertEquals(weapon5.getHolder(),hero2);
		assertEquals(backpack1.getHolder(),hero2);
		assertEquals(backpack2.getHolder(),hero2);
	}
	@Test
	public void transferItemToAnchorTest3Overflow(){
		Hero weak = new Hero("Hames",100L,0.5);
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack2.addItem(weapon4);
		backpack2.addItem(weapon5);
		backpack1.addItem(backpack2);
		hero1.addItemAt(AnchorpointType.BACK, backpack1);
		hero1.transfersItemToAnchor(AnchorpointType.BACK, weak);
		assertTrue(hero1.checkItemInAnchors(backpack1));
		assertFalse(weak.checkItemInAnchors(backpack1));
		assertFalse(weak.checkItemInAnchors(backpack2));
		assertEquals(weapon1.getHolder(),hero1);
		assertEquals(weapon2.getHolder(),hero1);
		assertEquals(weapon3.getHolder(),hero1);
		assertEquals(weapon4.getHolder(),hero1);
		assertEquals(weapon5.getHolder(),hero1);
		assertEquals(backpack1.getHolder(),hero1);
		assertEquals(backpack2.getHolder(),hero1);
	}
	@Test
	public void addItemToBackpackTest1(){
		hero1.addItemAt(AnchorpointType.BACK,backpack1);
		hero1.addItemToBackpack(AnchorpointType.BACK, weapon1);
		assertEquals(weapon1.getHolder(),hero1);
		assertEquals(backpack1.getHolder(),hero1);
		assertTrue(hero1.hasProperItems());
		assertTrue(backpack1.ItemIn(weapon1));
		assertTrue(hero1.checkItemInAnchors(weapon1));
	}
	@Test (expected = IllegalArgumentException.class)
	public void addItemToBackpackTest2(){
		Weapon weapon = new Weapon(new Weight(1000,Unit.kg),50);
		hero1.addItemAt(AnchorpointType.BACK,backpack1);
		hero1.addItemToBackpack(AnchorpointType.BACK, weapon);
	}
    @Test
    public void addItemToBackpackTest3(){
    	hero1.addItem(backpack1);
    	hero1.addItem(backpack2);
    	hero1.addItem(backpack3);
    	hero1.addItemToBackpack(weapon1);
    	assertTrue(weapon1.getHolder().equals(hero1));
    	assertTrue(hero1.checkItemInAnchors(weapon1));
    	assertEquals(hero1.getTotalValue(),20);
    }
    @Test
    public void addItemToBackpackTest4(){
    	Weapon weapon = new Weapon(new Weight(30,Unit.kg),10);
    	Backpack backpack = new Backpack(new Weight(1,Unit.kg),0, new Weight(2,Unit.kg));
    	hero1.addItem(backpack);
    	hero1.addItem(backpack2);
    	hero1.addItem(backpack3);
    	hero1.addItemToBackpack(weapon);
    	assertTrue(weapon.getHolder().equals(hero1));
    	assertTrue(hero1.checkItemInAnchors(weapon));
    	assertEquals(hero1.getTotalValue(),20);
    }
	@Test 
	public void addItemTest5Fail(){
		Weapon weapon = new Weapon(new Weight(101,Unit.kg),10);
    	hero1.addItem(backpack1);
    	hero1.addItem(backpack2);
    	hero1.addItem(backpack3);
    	hero1.addItemToBackpack(weapon);
    	assertEquals(weapon.getHolder(),null);
    	assertFalse(hero1.checkItemInAnchors(weapon));
    	assertEquals(hero1.getTotalValue(),0);
	}
	@Test
	public void addOwnItemToBackpackTest1(){
		hero1.addItemAt(AnchorpointType.BACK, backpack1);
		hero1.addItemAt(AnchorpointType.BELT,weapon1);
		hero1.addOwnItemToBackpack(AnchorpointType.BELT, AnchorpointType.BACK);
		assertTrue(backpack1.ItemIn(weapon1));
		assertEquals(hero1.getItemAt(AnchorpointType.BELT),null);
	}
	@Test
	public void addOwnItemToBackpackTest2(){
		Weapon weapon = new Weapon(new Weight(101,Unit.kg),10);
		hero1.addItemAt(AnchorpointType.BACK, backpack1);
		hero1.addItemAt(AnchorpointType.BELT,weapon);
		hero1.addOwnItemToBackpack(AnchorpointType.BELT, AnchorpointType.BACK);
		assertFalse(backpack1.ItemIn(weapon));
		assertFalse(hero1.getItemAt(AnchorpointType.BELT).equals(null));
		assertEquals(hero1.getItemAt(AnchorpointType.BELT),weapon);
	}
	@Test
	public void addOwnItemToBackpack1(){
		hero1.addItemAt(AnchorpointType.BACK, backpack1);
		hero1.addItemAt(AnchorpointType.BODY, backpack2);
		hero1.addItemAt(AnchorpointType.BELT, backpack3);
		hero1.addItemAt(AnchorpointType.LEFT, weapon1);
		hero1.addOwnItemToBackpack(AnchorpointType.LEFT);
		assertEquals(hero1.getItemAt(AnchorpointType.LEFT),null);
		assertEquals(hero1.getTotalValue(),20);
	}
	@Test
	public void addOwnItemToBackpack2(){
		hero1.addItemAt(AnchorpointType.LEFT, weapon1);
		hero1.addOwnItemToBackpack(AnchorpointType.LEFT);
		assertEquals(hero1.getItemAt(AnchorpointType.LEFT),weapon1);
		assertEquals(hero1.getTotalValue(),20);
	}
	
	@Test
	public void getterTest(){
		hero1.addItemAt(AnchorpointType.BACK, backpack1);
		hero1.getAnchors()[1] = null;
		assertFalse(hero1.getAnchors()[1]==null);
	}
	
}
