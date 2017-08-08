package rpg.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import rpg.Hero;
import rpg.Mobile;
import rpg.Monster;
import rpg.inventory.Backpack;
import rpg.inventory.Ducat;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

public class HeroHitTest {
	
	private Hero hero1;
	private Weapon weapon1,weapon2,weapon3,weapon4,weapon5;
	private Purse purse1;
	private double DELTA = 10E-5;
	private Backpack backpack1,backpack2;
	private Monster monster0;
	
	@Before
	public void setup(){
		hero1 = new Hero("James",50,21);
		weapon1 = new Weapon(new Weight(1,Unit.kg),5,77);
		weapon2 = new Weapon(new Weight(1,Unit.kg),5,35);
		weapon3 = new Weapon(new Weight(1,Unit.kg),5,35);
		weapon4 = new Weapon(new Weight(1,Unit.kg),5,35);
		weapon5 = new Weapon(new Weight(1,Unit.kg),5,35);
		purse1 = new Purse(new Weight(1,Unit.kg),new Weight(1,Unit.kg));
		purse1.addItem(new Ducat());
		monster0 = new Monster("Jared",26L,25,Mobile.generateAllAnchorpoints(),new Weapon(null,0,10));
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
		Hero hero0 = new Hero("Fighter",50L,25);
		hero0.addItemAt(AnchorpointType.LEFT, weapon1);
		Monster monster0 = new Monster("Jared",50L,25,new Weapon(null,0,10));
		while (hero0.getCurrentHitpoints()>=50){
			monster0.hit(hero0);
		}
		assertEquals(hero0.getCurrentHitpoints(),34);
		while (monster0.getCurrentHitpoints()>0){
			hero0.hit(monster0);
		}
		assertTrue(hero0.getCurrentHitpoints()>=34);
	}
	@Test
	public void collectTreasuresTest1(){
		Hero hero0 = new Hero("Fighter",50L,25);
		Monster monster0 = new Monster("Jared",50L,25,Mobile.generateAllAnchorpoints(),new Weapon(null,0,10));
		monster0.addItemAt(AnchorpointType.BACK,weapon1);
		monster0.addItemAt(AnchorpointType.BODY,weapon2);
		monster0.addItemAt(AnchorpointType.BELT,weapon3);
		monster0.addItemAt(AnchorpointType.LEFT,weapon4);
		monster0.addItemAt(AnchorpointType.RIGHT,weapon5);
		assertEquals(hero0.getNbItems(),0);
		while (monster0.getCurrentHitpoints()>0){
			hero0.hit(monster0);
		}
		assertEquals(hero0.getNbItems(),5);
		assertTrue(hero0.checkItemInAnchors(weapon1));
		assertTrue(hero0.checkItemInAnchors(weapon2));
		assertTrue(hero0.checkItemInAnchors(weapon3));
		assertTrue(hero0.checkItemInAnchors(weapon4));
		assertTrue(hero0.checkItemInAnchors(weapon5));
		assertTrue(hero0.hasProperItems());
		assertEquals(monster0.getNbItems(),0);
	}
	@Test
	public void collectTreasuresTest2(){
		Hero hero0 = new Hero("Fighter",50L,25);
		Ducat ducat1 = new Ducat();
		hero0.addItemAt(AnchorpointType.BACK, ducat1);
		Monster monster0 = new Monster("Jared",50L,25,Mobile.generateAllAnchorpoints(),new Weapon(null,0,10));
		monster0.addItemAt(AnchorpointType.BACK,weapon1);
		monster0.addItemAt(AnchorpointType.BODY,weapon2);
		monster0.addItemAt(AnchorpointType.BELT,weapon3);
		monster0.addItemAt(AnchorpointType.RIGHT,weapon4);
		monster0.addItemAt(AnchorpointType.LEFT,weapon5);
		assertEquals(hero0.getNbItems(),1);
		while (monster0.getCurrentHitpoints()>0){
			hero0.hit(monster0);
		}
		assertEquals(hero0.getNbItems(),5);
		assertTrue(hero0.checkItemInAnchors(ducat1));
		assertTrue(hero0.checkItemInAnchors(weapon1));
		assertTrue(hero0.checkItemInAnchors(weapon2));
		assertTrue(hero0.checkItemInAnchors(weapon3));
		assertTrue(hero0.checkItemInAnchors(weapon4));
		assertFalse(hero0.checkItemInAnchors(weapon5));
		assertFalse(monster0.checkItemInAnchors(weapon5));
		assertTrue(hero0.hasProperItems());
	}
	@Test
	public void collectTreasuresTest3(){
		Hero hero0 = new Hero("Fighter",50L,10);
		Weapon weapon = new Weapon(new Weight(100,Unit.kg),50);
		hero0.addItemAt(AnchorpointType.LEFT, weapon);
		Monster monster0 = new Monster("Jared",26L,25,Mobile.generateAllAnchorpoints(),new Weapon(null,0,10));
		monster0.addItemAt(AnchorpointType.BACK,weapon1);
		monster0.addItemAt(AnchorpointType.BODY,weapon2);
		monster0.addItemAt(AnchorpointType.BELT,weapon3);
		monster0.addItemAt(AnchorpointType.RIGHT,weapon4);
		monster0.addItemAt(AnchorpointType.LEFT,weapon5);
		assertEquals(hero0.getNbItems(),1);
		while (monster0.getCurrentHitpoints()>0){
			hero0.hit(monster0);
		}
		assertEquals(hero0.getNbItems(),1);
		assertTrue(hero0.checkItemInAnchors(weapon));
		assertFalse(hero0.checkItemInAnchors(weapon1));
		assertFalse(hero0.checkItemInAnchors(weapon2));
		assertFalse(hero0.checkItemInAnchors(weapon3));
		assertFalse(hero0.checkItemInAnchors(weapon4));
		assertFalse(hero0.checkItemInAnchors(weapon5));
		assertFalse(monster0.checkItemInAnchors(weapon1));
		assertFalse(monster0.checkItemInAnchors(weapon2));
		assertFalse(monster0.checkItemInAnchors(weapon3));
		assertFalse(monster0.checkItemInAnchors(weapon4));
		assertFalse(monster0.checkItemInAnchors(weapon5));
		assertTrue(hero0.hasProperItems());
	}
	@Test
	public void collectTreasuresTest4(){
		Hero hero0 = new Hero("Fighter",50L,50);
		Monster monster0 = new Monster("Jared",26L,25,null,new Weapon(null,0,10));
		if (monster0.getFreeAnchorpoints().size()>0){
			monster0.addItem(weapon1);
			while (monster0.getCurrentHitpoints()>0){
				hero0.hit(monster0);
			}
			assertEquals(hero0.getNbItems(),1);
			assertTrue(hero0.checkItemInAnchors(weapon1));
			assertFalse(monster0.checkItemInAnchors(weapon1));
		}

	}
	@Test 
	public void collectTreasuresTestBackpack1(){
		backpack1 = new Backpack(null,10,new Weight(6,Unit.kg));
		Hero hero0 = new Hero("Fighter",50L,50);
		hero0.addItem(backpack1);
		for (int i=0;i<4;i++){
			hero0.addItem(new Ducat());
		}
		assertEquals(hero0.getFreeAnchorpoints().size(),0);
		monster0.addItem(weapon1);
		monster0.addItem(weapon2);
		monster0.addItem(weapon3);
		monster0.addItem(weapon4);
		monster0.addItem(weapon5);
		while (monster0.getCurrentHitpoints()>0){
			hero0.hit(monster0);
		}
		assertTrue(hero0.checkItemInAnchors(weapon1));
		assertTrue(hero0.checkItemInAnchors(weapon2));
		assertTrue(hero0.checkItemInAnchors(weapon3));
		assertTrue(hero0.checkItemInAnchors(weapon4));
		assertTrue(hero0.checkItemInAnchors(weapon5));
		assertTrue(hero0.checkItemInAnchors(backpack1));
		assertTrue(backpack1.ItemIn(weapon1));
		assertTrue(backpack1.ItemIn(weapon2));
		assertTrue(backpack1.ItemIn(weapon3));
		assertTrue(backpack1.ItemIn(weapon4));
		assertTrue(backpack1.ItemIn(weapon5));
		assertEquals(weapon1.getHolder(),hero0);
		assertEquals(weapon2.getHolder(),hero0);
		assertEquals(weapon3.getHolder(),hero0);
		assertEquals(weapon4.getHolder(),hero0);
		assertEquals(weapon5.getHolder(),hero0);
		assertTrue(hero0.hasProperItems());
		assertFalse(monster0.checkItemInAnchors(weapon1));		
		assertFalse(monster0.checkItemInAnchors(weapon2));	
		assertFalse(monster0.checkItemInAnchors(weapon3));	
		assertFalse(monster0.checkItemInAnchors(weapon4));	
		assertFalse(monster0.checkItemInAnchors(weapon5));	
	}
	@Test 
	public void collectTreasuresTestBackpack2(){
		backpack1 = new Backpack(null,10,new Weight(2,Unit.kg));
		backpack2 = new Backpack(null,10,new Weight(3,Unit.kg));
		Hero hero0 = new Hero("Fighter",50L,50);
		hero0.addItem(backpack1);
		hero0.addItem(backpack2);
		for (int i=0;i<3;i++){
			hero0.addItem(new Ducat());
		}
		assertEquals(hero0.getFreeAnchorpoints().size(),0);
		monster0.addItem(weapon1);
		monster0.addItem(weapon2);
		monster0.addItem(weapon3);
		monster0.addItem(weapon4);
		monster0.addItem(weapon5);
		while (monster0.getCurrentHitpoints()>0){
			hero0.hit(monster0);
		}
		assertEquals(backpack1.getNbItems(),2);
		assertEquals(backpack2.getNbItems(),3);
	}
	@Test 
	public void collectTreasuresTestBackpack3(){
		backpack1 = new Backpack(null,10,new Weight(0,Unit.kg));
		backpack2 = new Backpack(null,10,new Weight(5,Unit.kg));
		Hero hero0 = new Hero("Fighter",50L,50);
		hero0.addItem(backpack1);
		hero0.addItem(backpack2);
		for (int i=0;i<3;i++){
			hero0.addItem(new Ducat());
		}
		assertEquals(hero0.getFreeAnchorpoints().size(),0);
		monster0.addItem(weapon1);
		monster0.addItem(weapon2);
		monster0.addItem(weapon3);
		monster0.addItem(weapon4);
		monster0.addItem(weapon5);
		while (monster0.getCurrentHitpoints()>0){
			hero0.hit(monster0);
		}
		assertEquals(backpack1.getNbItems(),0);
		assertEquals(backpack2.getNbItems(),5);
	}
	@Test 
	public void collectTreasuresTestBackpack4(){
		backpack1 = new Backpack(null,10,new Weight(0,Unit.kg));
		backpack2 = new Backpack(null,10,new Weight(3,Unit.kg));
		Hero hero0 = new Hero("Fighter",50L,50);
		hero0.addItem(backpack1);
		hero0.addItem(backpack2);
		for (int i=0;i<3;i++){
			hero0.addItem(new Ducat());
		}
		assertEquals(hero0.getFreeAnchorpoints().size(),0);
		monster0.addItem(weapon1);
		monster0.addItem(weapon2);
		monster0.addItem(weapon3);
		monster0.addItem(weapon4);
		monster0.addItem(weapon5);
		while (monster0.getCurrentHitpoints()>0){
			hero0.hit(monster0);
		}
		assertEquals(backpack1.getNbItems(),0);
		assertEquals(backpack2.getNbItems(),3);
	}
}
