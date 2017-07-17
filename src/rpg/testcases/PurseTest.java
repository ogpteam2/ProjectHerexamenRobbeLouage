package rpg.testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import rpg.Hero;
import rpg.inventory.Ducat;
import rpg.inventory.Item;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.Unit;
import rpg.value.Weight;

public class PurseTest {
	
	private Purse purse1,purse2,purse3;
	private Ducat ducat1,ducat2,ducat3,ducat4,ducat5,ducat6,ducat7;
	private Weapon weapon1,weapon3,weapon4,weapon5;
	private Hero hero1,hero2,hero3,hero4;
	
	@Before
	public void setup(){
		hero1 = new Hero("Jared", 100L, 45);
		purse1 = new Purse(new Weight(100,Unit.g),new Weight(250,Unit.g));
		purse2 = new Purse(new Weight(51,Unit.g),hero1,new Weight(1000,Unit.g));
		purse3 = new Purse(new Weight(51,Unit.g),new Weight(100,Unit.g));
	}
	
	@Test
	public void constructor2Test() {
		assertEquals(purse1.getOwnValue(),0);
		assertEquals(purse1.getOwnWeight(),new Weight(100,Unit.g));
		assertEquals(purse1.getHolder(),null);
		assertEquals(purse1.getCapacity(),new Weight(250,Unit.g));
		assertEquals(purse1.getNbItems(),0);
	}
	@Test
	public void constructor1Test() {
		assertEquals(purse2.getCapacity(),new Weight(1000,Unit.g));
		assertEquals(purse2.getContents(),new ArrayList<Item>());
		assertEquals(purse2.getHolder(),hero1);
		assertTrue(hero1.checkItemInAnchors(purse2));
		assertTrue(hero1.hasProperItems());
		assertTrue(purse2.hasProperHolder());
	}
	@Test 
	public void addItemTest(){
		for (int i=0;i<5;i++){
			Ducat newDucat = new Ducat();
			purse1.addItem(newDucat);
		}
		assertEquals(purse1.getWeight(), new Weight(350,Unit.g));
		assertEquals(purse1.getWeightOfContents(Unit.g), new Weight(250,Unit.g));
		assertEquals(purse1.getValue(),5);
	}
	@Test 
	public void addItemTestBroken(){
		for (int i=0;i<6;i++){
			Ducat newDucat = new Ducat();
			purse1.addItem(newDucat);
		}
		assertEquals(purse1.getWeight(), new Weight(100,Unit.g));
		assertEquals(purse1.getWeightOfContents(Unit.g), new Weight(0,Unit.g));
		assertEquals(purse1.getValue(),0);
		assertTrue(purse1.getBroken());
		assertFalse(purse1.canAdd(new Ducat()));
	}
	@Test (expected = IllegalArgumentException.class)
	public void addItemTestBrokenFail(){
		purse1.addItem(new Weapon(null,50));
	}
	@Test
	public void canHaveAsContents(){
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Weapon(null,0));
		assertFalse(purse1.canHaveAsContents(items));
	}
	@Test
	public void canHaveAsContents2(){
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Ducat());
		items.add(new Ducat());
		items.add(new Ducat());
		items.add(new Ducat());
		assertTrue(purse1.canHaveAsContents(items));
	}
	@Test
	public void transferTest(){
		for (int i=0;i<5;i++){
			purse1.addItem(new Ducat());
		}
		purse1.transfer(purse2);
		assertTrue(purse1.getBroken());
		assertEquals(purse2.getNbItems(),5);
	}
	@Test
	public void transferTest2(){
		for (int i=0;i<5;i++){
			purse1.addItem(new Ducat());
		}
		purse1.transfer(purse3);
		assertEquals(purse1.getValue(),0);
		assertEquals(purse3.getValue(),0);
		assertEquals(purse1.getBroken(),true);
		assertEquals(purse3.getBroken(),true);
	}
	
}
