package rpg.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import rpg.inventory.Weapon;
import rpg.value.Unit;
import rpg.value.Weight;

public class WeaponTest {
	
	private Weapon weapon1,weapon2,weapon3,weapon4;
	private Weight weight1,weight2, weight3;
	
	
	@Before
	public void setup(){
		weight1 = Weight.kg_0;
		weight2 = new Weight(10,Unit.kg);
		weight3 = new Weight(10000,Unit.g);
		weapon1 = new Weapon(null,0);
		weapon2 = new Weapon(weight1,0);
		weapon3 = new Weapon(weight2,0);
		weapon4 = new Weapon(weight3,0);
	}
	
	
	@Test
	public void IDtest() {
		for (int i=0;i<100;i++){
			Weapon weapon = new Weapon(null,0);
			assertEquals(weapon.getId(),36+6*i);
		}
	}
	
	@Test
	public void invalidWeightTest(){
		assertEquals(weapon1.getWeight(),new Weight(1,Unit.kg));
		assertEquals(weapon2.getWeight(),new Weight(1,Unit.kg));
		
	}
	
	@Test
	public void validWeightTest(){
		assertEquals(weapon3.getWeight(),weight2);
		assertEquals(weapon4.getWeight(),weight3);
	}

	@Test
	public void getterTest(){
		assertTrue(weapon3.getWeight().hasSameValue(weapon4.getWeight()));
	}
	
	@Test
	public void getterConversionTest(){
		assertEquals(weapon3.getWeight(Unit.g),weapon4.getWeight());
	}
	
	
}
