package rpg.testcases;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import rpg.Hero;
import rpg.Monster;

public class MonsterTest {

	private Monster invalidName1,invalidName2,invalidName3,validName1,validName2,validName3;
	
	@Before
	public void setup(){
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void isValidName1() {
		invalidName1 = new Monster(null);
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName2() {
		invalidName2 = new Monster("bob");
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName3() {
		invalidName3 = new Monster("b*b");
	}
	@Test 
	public void isValidName4() {
		validName1 = new Monster("James");
	}
	@Test 
	public void isValidName5() {
		validName2 = new Monster("H'erld");
	}
	@Test 
	public void isValidName6() {
		validName3 = new Monster("Blub");
	}

}
