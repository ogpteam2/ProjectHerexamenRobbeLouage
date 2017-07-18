package rpg.testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import rpg.inventory.BackpackIterator;
import rpg.inventory.Ducat;
import rpg.inventory.Item;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.Unit;
import rpg.value.Weight;

public class BackpackIteratorTest {

	private BackpackIterator it;
	private ArrayList<Item> contents1,contents2,contents3;
	private Weapon weapon1,weapon2,weapon3,weapon4;
	private Purse purse1;
	
	@Before
	public void setup(){
		weapon1 = new Weapon(new Weight(1.2,Unit.kg),50);
		weapon2 = new Weapon(new Weight(1.3,Unit.kg),50);
		weapon3 = new Weapon(new Weight(1.4,Unit.kg),50);
		weapon4 = new Weapon(new Weight(1.5,Unit.kg),50);
		purse1 = new Purse(new Weight(100,Unit.g),new Weight(5000,Unit.g));
		for (int i=0;i<20;i++){
			purse1.addItem(new Ducat());
		}
		contents1 = new ArrayList<Item>();
		contents1.add(weapon1);
		contents1.add(weapon2);
		contents1.add(weapon3);
		contents1.add(weapon4);
		contents1.add(purse1);
		
	}
	
	
	
	@Test
	public void iterationTest() {
		it = new BackpackIterator(contents1);
		int i = 0;
		while (it.hasMoreElements()){
			assertEquals(it.nextElement(),contents1.get(i++));
		}
	}
	@Test (expected = RuntimeException.class)
	public void iterationTestFail() {
		it = new BackpackIterator(contents1);
		int i = 0;
		while (it.hasMoreElements()){
			assertEquals(it.nextElement(),contents1.get(i++));
		}
		it.nextElement();
	}
	@Test
	public void iterationTestReset() {
		it = new BackpackIterator(contents1);
		int i = 0;
		while (it.hasMoreElements()){
			assertEquals(it.nextElement(),contents1.get(i++));
		}
		it.reset();
		int j = 0;
		while (it.hasMoreElements()){
			assertEquals(it.nextElement(),contents1.get(j++));
		}
	}
	@Test
	public void emptyTest(){
		it = new BackpackIterator(new ArrayList<Item>());
		assertFalse(it.hasMoreElements());
	}
}
