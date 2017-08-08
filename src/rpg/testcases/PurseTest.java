package rpg.testcases;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import rpg.Hero;
import rpg.inventory.Backpack;
import rpg.inventory.Ducat;
import rpg.inventory.Item;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.Unit;
import rpg.value.Weight;

public class PurseTest {
	
	private Purse purse1,purse2,purse3;
	private Hero hero1;
	
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
	public void addItemTestFail(){
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
		assertEquals(purse3.getValue(),0);
	}
	@Test
	public void nbItems(){
		for (int i=0;i<10;i++){
			purse2.addItem(new Ducat());
		}
		assertEquals(purse2.getNbItems(),10);
	}
	@Test
	public void brokenTest(){
		Backpack backpack = new Backpack(new Weight(1,Unit.kg),0,new Weight(2,Unit.kg));
		backpack.addItem(purse1);
		purse1.addItem(new Ducat());
		purse1.addItem(new Ducat());
		purse1.addItem(new Ducat());
		purse1.addItem(new Ducat());
		purse1.addItem(new Ducat());
		purse1.addItem(new Ducat());
		purse1.addItem(new Ducat());
		purse1.addItem(new Ducat());
		assertTrue(purse1.getBroken());
		assertEquals(backpack.getValue(),5);
		assertFalse(backpack.ItemIn(purse1));
	}


	@Test (expected = IllegalArgumentException.class)
	public void brokenTest2Fail(){
		Backpack backpack = new Backpack(new Weight(1,Unit.kg),0,new Weight(150,Unit.g));
		Purse purse = new Purse(new Weight(100,Unit.g),new Weight(300,Unit.g));
		backpack.addItem(purse);
		purse.addItem(new Ducat());
		purse.addItem(new Ducat());
		purse.addItem(new Ducat());
		purse.addItem(new Ducat());
		purse.addItem(new Ducat());
	}
	
	@Test
	public void valueChangeTest(){
		Purse purse = new Purse(new Weight(100,Unit.g),new Weight(300,Unit.g));
		purse.addItem(new Ducat());
		purse.setValue(100);
		assertEquals(purse.getValue(),1);
	}
}
