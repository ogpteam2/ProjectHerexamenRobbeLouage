package rpg.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import rpg.Hero;
import rpg.inventory.Backpack;
import rpg.inventory.BackpackIterator;
import rpg.inventory.Ducat;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.Unit;
import rpg.value.Weight;

public class BackpackTest {

	private Backpack backpack1,backpack2,backpack3;
	private Weapon weapon1,weapon2,weapon3,weapon4;
	private Purse purse1 = new Purse(new Weight(200,Unit.g),new Weight(2,Unit.kg));
	private Ducat ducat1 = new Ducat();
	
	@Before
	public void setup(){
		weapon1 = new Weapon(new Weight(0.05,Unit.kg),50);
		weapon2 = new Weapon(new Weight(0.06,Unit.kg),50);
		weapon3 = new Weapon(new Weight(0.07,Unit.kg),50);
		weapon4 = new Weapon(new Weight(0.08,Unit.kg),50);
		purse1.addItem(ducat1);
		backpack1  = new Backpack(new Weight(1,Unit.kg),10,new Weight(7,Unit.kg));
		backpack2  = new Backpack(new Weight(2,Unit.kg),10,new Weight(10,Unit.kg));
		backpack3  = new Backpack(new Weight(2,Unit.kg),10,new Weight(100,Unit.kg));
	}
	
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void test() {
		System.out.println(backpack1.getItemAt(5));
	}
	@Test
	public void addItem(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack1.addItem(weapon4);
		backpack1.addItem(purse1);
		BackpackIterator it = backpack1.getIterator();
		int i = 0;
		while (it.hasMoreElements()){
			assertEquals(it.nextElement(),backpack1.getItemAt(i++));
		}
	}
	@Test
	public void addItemWeight(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack1.addItem(weapon4);
		backpack1.addItem(purse1);
		BackpackIterator it = backpack1.getIterator();
		Weight sum = Weight.kg_0;
		while (it.hasMoreElements()){
			sum = sum.add(it.nextElement().getWeight(Unit.kg));
		}
		sum = sum.add(backpack1.getOwnWeight());
		assertEquals(sum,backpack1.getWeight(Unit.kg));
	}
	@Test (expected = IllegalArgumentException.class)
	public void addItemAlreadyIn(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack1.addItem(weapon4);
		backpack1.addItem(purse1);
		backpack1.addItem(weapon1);
	}
	@Test (expected = IllegalArgumentException.class)
	public void addOverflowTest(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack1.addItem(weapon4);
		backpack1.addItem(purse1);
		backpack1.addItem(new Weapon(new Weight(1000,Unit.kg),50));
	}
	@Test
	public void ItemIn(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(purse1);
		assertTrue(backpack1.ItemIn(weapon1));
		assertTrue(backpack1.ItemIn(weapon2));
		assertFalse(backpack1.ItemIn(purse1));
		assertTrue(backpack1.ItemIn(ducat1));
	}
	@Test (expected = IllegalArgumentException.class)
	public void addItemContainerTest(){
		backpack2.addItem(weapon1);
		backpack1.addItem(weapon1);
	}
	@Test
	public void ItemInRecursionTest(){
		backpack2.addItem(weapon3);
		backpack1.addItem(weapon1);
		backpack1.addItem(backpack2);
		assertTrue(backpack1.ItemIn(weapon3));
	}
	@Test
	public void ItemInRecursionTest2(){
		backpack2.addItem(weapon3);
		backpack3.addItem(weapon2);
		backpack1.addItem(backpack2);
		backpack1.addItem(backpack3);
		assertTrue(backpack1.ItemIn(weapon2));
	}
	@Test
	public void removeTest1(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack1.addItem(weapon4);
		assertTrue(backpack1.ItemIn(weapon1));
		backpack1.removeItem(weapon1);
		assertFalse(backpack1.ItemIn(weapon1));
	}
	@Test
	public void removeTest2(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack1.addItem(weapon4);
		assertTrue(backpack1.ItemIn(weapon1));
		backpack1.removeItem(0);
		assertFalse(backpack1.ItemIn(weapon1));
	}
	@Test (expected=IllegalArgumentException.class)
	public void removeTest3(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack1.addItem(weapon4);
		assertTrue(backpack1.ItemIn(weapon1));
		backpack1.removeItem(5);
		assertFalse(backpack1.ItemIn(weapon1));
	}
	@Test (expected=IllegalArgumentException.class)
	public void removeTest4(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		assertTrue(backpack1.ItemIn(weapon1));
		backpack1.removeItem(weapon4);
	}

	@Test 
	public void removeTest5(){
		Hero hero1 = new Hero("James",100L,50);
		Backpack backpack = new Backpack(new Weight(1,Unit.kg),10,hero1,new Weight(7,Unit.kg));
		backpack.addItem(weapon1);
		assertEquals(weapon1.getHolder(),hero1);
		backpack.removeItem(0);
		assertEquals(weapon1.getHolder(),null);
	}
	
	@Test 
	public void getNbTest(){
		backpack3.addItem(weapon3);
		backpack2.addItem(weapon2);
		backpack2.addItem(weapon1);
		backpack2.addItem(backpack3);
		backpack1.addItem(backpack2);
		backpack1.addItem(weapon4);
		backpack1.addItem(purse1);
		assertEquals(backpack1.getNbItems(),8);
		
	}
	@Test 
	public void getNbTest2(){
		backpack1.addItem(weapon1);
		backpack2.addItem(weapon2);
		backpack3.addItem(weapon3);
		backpack3.addItem(backpack2);
		backpack1.addItem(backpack3);
		assertEquals(backpack1.getNbItems(),5);
		
	}
	@Test
	public void weightTest(){
		backpack1.addItem(weapon1);
		backpack2.addItem(weapon2);
		backpack3.addItem(weapon3);
		backpack2.addItem(backpack1);
		backpack3.addItem(backpack2);
		assertEquals(backpack3.getWeight(Unit.kg),new Weight(5.18,Unit.kg));
		Hero hero = new Hero("Rich",100L,50);
		hero.addItem(backpack3);
		assertEquals(hero.getTotalWeight(Unit.kg),backpack3.getWeight(Unit.kg));
	}
	@Test
	public void ValueTest(){
		backpack1.addItem(weapon1);
		backpack2.addItem(weapon2);
		backpack3.addItem(weapon3);
		backpack2.addItem(backpack1);
		backpack3.addItem(backpack2);
		assertEquals(backpack3.getValue(),330);
		Hero hero = new Hero("Rich",100L,50);
		hero.addItem(backpack3);
		assertEquals(hero.getTotalValue(),backpack3.getValue());
	}
	@Test
	public void getTest1(){
		backpack1.addItem(weapon1);
		backpack1.addItem(weapon2);
		backpack1.addItem(weapon3);
		backpack1.addItem(weapon4);
		assertEquals(backpack1.getItemWithID(weapon1.getId()),weapon1);
		assertEquals(backpack1.getItemWithID(weapon2.getId()),weapon2);
		assertEquals(backpack1.getItemWithID(weapon3.getId()),weapon3);
		assertEquals(backpack1.getItemWithID(weapon4.getId()),weapon4);
	}
	@Test
	public void duplicateGet(){
		Ducat ducat5 = new Ducat();
		Ducat ducat6 = new Ducat();
		backpack1.addItem(ducat5);
		backpack1.addItem(ducat6);
		assertEquals(backpack1.getItemWithID(ducat6.getId()),ducat5);
	}
	@Test 
	public void addTestRecursion(){
		Backpack backpack = new Backpack(new Weight(1,Unit.kg),10,new Weight(7,Unit.kg));
		Backpack backpack2 = new Backpack(new Weight(1,Unit.kg),10,new Weight(100,Unit.kg));
		Weapon weapon = new Weapon(new Weight(8,Unit.kg),50);
		backpack.addItem(backpack2);
		backpack2.addItem(weapon);
		System.out.println(backpack.getCapacity());
		System.out.println(backpack.getWeightOfContents(Unit.kg));
	}

}
