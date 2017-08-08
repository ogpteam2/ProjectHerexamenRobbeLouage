package rpg.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import rpg.Hero;
import rpg.inventory.Ducat;
import rpg.value.Unit;
import rpg.value.Weight;

public class DucatTest {
	
	private Ducat ducat;
	
	@Before
	public void setup(){
		ducat = new Ducat();
	}
	
	@Test
	public void correctnesstest() {
		assertEquals(ducat.getId(),-1L);
		assertEquals(ducat.getWeight(),new Weight(50,Unit.g));
		assertEquals(ducat.getValue(),1);
		assertEquals(ducat.getHolder(),null);
	}

	@Test
	public void stressTest(){
		for (Integer i=0;i<Integer.MAX_VALUE/100;i++){
			Ducat name = new Ducat();
			assertEquals(name.getId(),-1L);
			assertEquals(name.getWeight(),new Weight(50,Unit.g));
			assertEquals(name.getValue(),1);
			assertEquals(ducat.getHolder(),null);
		}
	}
	
	@Test
	public void changeHolderTest(){
		Ducat ducat = new Ducat();
		Hero hero = new Hero("James", 100, 30);
		ducat.setHolder(hero);
		assertFalse(ducat.getHolder()==hero);
		assertTrue(ducat.getHolder()==null);
	}
	
	@Test
	public void changeValue(){
		Ducat ducat = new Ducat();
		ducat.setValue(1000);
		assertEquals(ducat.getValue(),1);
	}
}
