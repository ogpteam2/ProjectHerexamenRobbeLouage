package rpg.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import rpg.Hero;
import rpg.inventory.Backpack;
import rpg.inventory.Ducat;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.Unit;
import rpg.value.Weight;

public class HeroContainerTest {

	private Hero hero1;
	private Backpack backpack1,backpack2,backpack3;
	private Weapon weapon1, weapon2, weapon3, weapon4, weapon5;
	private Purse purse1;
	
	@Before
	public void setup(){
		hero1 = new Hero("James",100L,50);
		backpack1 = new Backpack(new Weight(1,Unit.kg),0, new Weight(100,Unit.kg));
		backpack2 = new Backpack(new Weight(1,Unit.kg),0, new Weight(100,Unit.kg));
		backpack3 = new Backpack(new Weight(1,Unit.kg),0, new Weight(100,Unit.kg));
		weapon1 = new Weapon(new Weight(1,Unit.kg),10);
		weapon2 = new Weapon(new Weight(1,Unit.kg),10);
		weapon3 = new Weapon(new Weight(1,Unit.kg),10);
		weapon4 = new Weapon(new Weight(1,Unit.kg),10);
		purse1 = new Purse(new Weight(100,Unit.g), new Weight(5,Unit.kg));
		purse1.addItem(new Ducat());
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
