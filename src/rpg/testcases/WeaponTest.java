package rpg.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import rpg.inventory.Weapon;

public class WeaponTest {
	
	private Weapon weapon1,weapon2,weapon3,weapon4;
	
	@Before
	public void setup(){
		weapon1 = new Weapon();
	}
	
	
	@Test
	public void IDtest() {
		for (int i=0;i<100;i++){
			Weapon weapon = new Weapon();
			assertEquals(weapon.getId(),18+6*i);
		}
	}

}
