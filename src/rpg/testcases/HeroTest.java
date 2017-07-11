package rpg.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import rpg.*;

public class HeroTest {

	private Hero invalidName0,invalidName1,invalidName2,invalidName3,invalidName4,invalidName5,validName1,validName2,validName3;
	
	@Before
	public void setup(){

	}
	
	@Test (expected = IllegalArgumentException.class)
	public void isValidName0() {
		invalidName0 = new Hero(null);
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName1() {
		invalidName1 = new Hero("bob");
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName2() {
		invalidName2 = new Hero("James:");
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName3() {
		invalidName3 = new Hero("James 'O' H'ara");
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName4() {
		invalidName4 = new Hero("Erik:O");
	}
	@Test (expected = IllegalArgumentException.class)
	public void isValidName5() {
		invalidName5 = new Hero("B*b");
	}
	@Test
	public void isValidName6() {
		validName1 = new Hero("James 'O' Hara");
		assertEquals(validName1.getName(),"James 'O' Hara");
	}
	@Test
	public void isValidName7() {
		validName2 = new Hero("James: 'O' Hara");
		assertEquals(validName2.getName(),"James: 'O' Hara");
	}	
	@Test
	public void isValidName8() {
		validName3 = new Hero("James");
		assertEquals(validName3.getName(),"James");
	}
	

}
