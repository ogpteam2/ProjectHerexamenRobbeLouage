package rpg.testcases;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import rpg.Hero;
import rpg.Mobile;
import rpg.Monster;
import rpg.inventory.Ducat;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

public class HeroHitTest {
	
	private Hero hero1;
	private Weapon weapon1,weapon2,weapon3;
	private Purse purse1;
	private double DELTA = 10E-5;
	
	@Before
	public void setup(){
		hero1 = new Hero("James",50,21);
		weapon1 = new Weapon(new Weight(1,Unit.kg),5,77);
		weapon2 = new Weapon(new Weight(1,Unit.kg),5,35);
		purse1 = new Purse(new Weight(1,Unit.kg),new Weight(1,Unit.kg));
		purse1.addItem(new Ducat());
	}
	
	
	@Test
	public void damageTest() {
		assertEquals(hero1.getTotalDamage(),5.0,DELTA);
		hero1.addItemAt(AnchorpointType.LEFT, weapon1);
		hero1.addItemAt(AnchorpointType.RIGHT, weapon2);
		assertEquals(hero1.getTotalDamage(),61.0,DELTA);
	}
	@Test
	public void damageTest2() {
		assertEquals(hero1.getTotalDamage(),5.0,DELTA);
		hero1.addItemAt(AnchorpointType.BODY, weapon1);
		hero1.addItemAt(AnchorpointType.BACK, weapon2);
		assertEquals(hero1.getTotalDamage(),5.0,DELTA);
	}
	@Test
	public void damageTest3() {
		assertEquals(hero1.getTotalDamage(),5.0,DELTA);
		hero1.addItemAt(AnchorpointType.BODY, weapon1);
		hero1.addItemAt(AnchorpointType.BACK, weapon2);
		hero1.addItemAt(AnchorpointType.LEFT, purse1);
		assertEquals(hero1.getTotalDamage(),5.0,DELTA);
	}
	@Test 
	public void hitTestWithoutcollectingTreasuresTest1(){
		Hero hero0 = new Hero("Fighter",50L,25);
		Monster monster0 = new Monster("Jared",50L,25,new Weapon(null,0,10));
		while (monster0.getCurrentHitpoints()>0){
			hero0.hit(monster0);
		}
		assertEquals(monster0.getCurrentHitpoints(),0);
	}
	@Test
	public void hitTestHeroHero(){
		Hero hero0 = new Hero("Fighter",50L,25);
		Hero hero1 = new Hero("Fighter",50L,25);
		hero0.hit(hero1);
		assertEquals(hero1.getCurrentHitpoints(),50L);
	}
	@Test
	public void healTest(){
		
	}
	
	
	
	
}
