package rpg.testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import rpg.Hero;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Weapon;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

public class WeaponTest {
	
	private Weapon weapon1,weapon2,weapon3,weapon4;
	private Weight weight1,weight2, weight3;
	private Weapon weapon5,weapon6,weapon7,weapon8,weapon9,weapon10,weapon11,weapon12;
	private Hero hero1,hero2,hero3,hero4,strong;
	private Weapon heavy1,heavy2,heavy3,heavy4;
	private Weapon damage1,damage2,damage3,damage4;
	
	@Before
	public void setup(){
		weight1 = Weight.kg_0;
		weight2 = new Weight(10,Unit.kg);
		weight3 = new Weight(10000,Unit.g);
		weapon1 = new Weapon(null,0);
		weapon2 = new Weapon(weight1,0);
		weapon3 = new Weapon(weight2,0);
		weapon4 = new Weapon(weight3,0);
		weapon5 = new Weapon(null,10);
		weapon6 = new Weapon(null,-10);
		weapon7 = new Weapon(null,0);
		hero1 = new Hero("James",10L,15);
		hero2 = new Hero("James",10L,10);
		hero3 = new Hero("James",10L,30);
		hero4 = new Hero("James",10L,0);
		heavy1 = new Weapon(new Weight(100,Unit.kg),50);
		heavy2 = new Weapon(new Weight(300,Unit.kg),50);
		heavy3 = new Weapon(new Weight(10000,Unit.kg),50);
		heavy4 = new Weapon(new Weight(1,Unit.kg),50);
		strong = new Hero("Strong",100L,50);
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
	
	@Test
	public void valueWeaponTest(){
		assertEquals(weapon5.getDamage(),10);
		assertEquals(weapon6.getDamage(),-10);
		assertEquals(weapon7.getDamage(),weapon7.getDamage()*2);
		assertTrue(weapon5.canHaveAsValue(0));
		assertFalse(weapon5.canHaveAsValue(-1));
		assertTrue(weapon5.canHaveAsValue(1));
	}
	
	@Test 
	public void constructor1HolderTest(){
		assertEquals(hero1.getNbItems(),0);
		Weapon holder = new Weapon(new Weight(100,Unit.kg),0,hero1,5);
		assertEquals(holder.getHolder(),hero1); // first part of bidirectional link OK
		assertEquals(hero1.getNbItems(),1);
		for (Anchorpoint anchor:hero1.getAnchors()){
			if (anchor.getItem()!=null){
				assertEquals(anchor.getItem(),holder); // second part of bidirectional link OK
			}
		}
		assertTrue(holder.hasProperHolder()); // double check OK
	}
	
	@Test 
	public void constructor2HolderTest(){
		Weapon weapon = new Weapon(null,50);
		assertEquals(weapon1.getHolder(),null);
		assertTrue(weapon1.hasProperHolder());
	}
	
	@Test 
	public void canHaveAsHolderTest1(){
		assertTrue(heavy1.canHaveAsHolder(hero1));
		assertTrue(heavy1.canHaveAsHolder(hero2));
		assertTrue(heavy1.canHaveAsHolder(hero3));
		assertFalse(heavy1.canHaveAsHolder(hero4));
	}
	
	@Test 
	public void canHaveAsHolderTest2(){
		assertFalse(heavy2.canHaveAsHolder(hero1));
		assertFalse(heavy2.canHaveAsHolder(hero2));
		assertTrue(heavy2.canHaveAsHolder(hero3));
		assertFalse(heavy2.canHaveAsHolder(hero4));
	}
	
	@Test 
	public void canHaveAsHolderTest3(){
		assertFalse(heavy3.canHaveAsHolder(hero1));
		assertFalse(heavy3.canHaveAsHolder(hero2));
		assertFalse(heavy3.canHaveAsHolder(hero3));
		assertFalse(heavy3.canHaveAsHolder(hero4));
	}
	
	@Test 
	public void canHaveAsHolderTest4(){
		assertTrue(heavy4.canHaveAsHolder(hero1));
		assertTrue(heavy4.canHaveAsHolder(hero2));
		assertTrue(heavy4.canHaveAsHolder(hero3));
		assertFalse(heavy4.canHaveAsHolder(hero4));
	}
	
	@Test
	public void addingTest(){
		assertEquals(strong.getNbItems(),0);
		Weapon adding1 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		Weapon adding2 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		Weapon adding3 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		Weapon adding4 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		Weapon adding5 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		assertEquals(adding1.getHolder(),strong);
		assertEquals(adding2.getHolder(),strong);
		assertEquals(adding3.getHolder(),strong);
		assertEquals(adding4.getHolder(),strong);
		assertEquals(adding5.getHolder(),strong);
		assertEquals(strong.getNbItems(),5);
		assertTrue(strong.hasProperItems());
		assertEquals(strong.getFreeAnchorpoints(),new ArrayList<Anchorpoint>());
		assertTrue(strong.checkItemInAnchors(adding1));
		assertTrue(strong.checkItemInAnchors(adding2));
		assertTrue(strong.checkItemInAnchors(adding3));
		assertTrue(strong.checkItemInAnchors(adding4));
		assertTrue(strong.checkItemInAnchors(adding5));
		assertFalse(strong.checkItemInAnchors(heavy2));
		assertEquals(strong.getTotalWeight(Unit.kg),new Weight(250,Unit.kg));
	}

	@Test
	public void addingTest2(){
		Weapon adding1 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		Weapon adding2 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		Weapon adding3 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		Weapon adding4 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		Weapon adding5 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		Weapon adding6 = new Weapon(new Weight(50,Unit.kg),0,strong,5);
		assertEquals(adding1.getHolder(),strong);
		assertEquals(adding2.getHolder(),strong);
		assertEquals(adding3.getHolder(),strong);
		assertEquals(adding4.getHolder(),strong);
		assertEquals(adding5.getHolder(),strong);
		assertEquals(adding6.getHolder(),null);
	}
	
	@Test
	public void constructor1TestDamage(){
		damage1 = new Weapon(new Weight(5,Unit.kg),50,strong,77);
		assertEquals(damage1.getWeight(),new Weight(5.0,Unit.kg));
		assertEquals(damage1.getDamage(),77);
		assertEquals(damage1.getValue(),50);
		assertEquals(damage1.getHolder(),strong);
		assertTrue(strong.checkItemInAnchors(damage1));
	}
	@Test
	public void constructor2TestDamage(){
		damage2 = new Weapon(new Weight(5,Unit.kg),50,77);
		assertEquals(damage2.getWeight(),new Weight(5.0,Unit.kg));
		assertEquals(damage2.getDamage(),77);
		assertEquals(damage2.getValue(),50);
		assertEquals(damage2.getHolder(),null);
	}
	@Test
	public void constructor3TestDamage(){
		damage3 = new Weapon(new Weight(5,Unit.kg),strong,77);
		assertEquals(damage3.getWeight(),new Weight(5.0,Unit.kg));
		assertEquals(damage3.getDamage(),77);
		assertEquals(damage3.getValue(),154);
		assertEquals(damage3.getHolder(),strong);
	}
	@Test
	public void constructor4TestDamage(){
		damage4 = new Weapon(new Weight(5,Unit.kg),77);
		assertEquals(damage4.getWeight(),new Weight(5.0,Unit.kg));
		assertEquals(damage4.getDamage(),77);
		assertEquals(damage4.getValue(),154);
	}
	@Test
	public void settDamageTest(){
		damage4 = new Weapon(new Weight(5,Unit.kg),77);
		assertEquals(damage4.getValue(),154);
		damage4.setDamage(80);
		assertEquals(damage4.getValue(),160);
	}
	@Test
	public void damageValueTest(){
		damage1 = new Weapon(new Weight(5,Unit.kg),50,strong,77);
		assertEquals(damage1.getValue(),50);
		damage1.setDamage(80);
		assertEquals(damage1.getValue(),160);
	}
	
	
	
}
