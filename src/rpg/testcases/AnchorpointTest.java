package rpg.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Weapon;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

public class AnchorpointTest {

	private Weapon item1,item2;
	private static AnchorpointType back,belt,right,left,body;
	private Anchorpoint point1,point2,point3,point4,point5,point6,point7,point8;
	
	@Before
	public void setup(){
		item1 = new Weapon(null,0);
		item2 = new Weapon(new Weight(10,Unit.kg),100);
		back = AnchorpointType.BACK;
		belt = AnchorpointType.BELT;
		right = AnchorpointType.RIGHT;
		left= AnchorpointType.LEFT;
		body = AnchorpointType.BODY;
		point1 = new Anchorpoint(back,item1);
		point2 = new Anchorpoint(belt,item1);
		point3 = new Anchorpoint(right,item1);
		point4 = new Anchorpoint(left,item1);
		point5 = new Anchorpoint(body,item1);
		point6 = new Anchorpoint(back);
		point7 = new Anchorpoint(belt);
		point8 = new Anchorpoint();
	}
	
	@Test
	public void contructor1Test() {
		assertEquals(point1.getAnchorpointType(),back);
		assertEquals(point1.getItem(),item1);
		assertEquals(point2.getAnchorpointType(),belt);
		assertEquals(point2.getItem(),item1);
		assertEquals(point3.getAnchorpointType(),right);
		assertEquals(point3.getItem(),item1);
		assertEquals(point4.getAnchorpointType(),left);
		assertEquals(point4.getItem(),item1);
		assertEquals(point5.getAnchorpointType(),body);
		assertEquals(point5.getItem(),item1);
	}	
	
	@Test
	public void contructor2Test() {
		assertEquals(point6.getItem(),null);
		assertEquals(point7.getItem(),null);
	}
	
	@Test
	public void contructor3Test() throws InterruptedException {
		System.gc();
		Thread.sleep(1000);
		assertEquals(point8.getItem(),null);
		assertEquals(point8.getAnchorpointType(),null);
	}
	
	@Test
	public void setItemTest() {
		assertEquals(point1.getItem(),item1);
		point1.setItem(item2);
		assertEquals(point1.getItem(),item2);	
	}
	
	@Test
	public void cloneTest(){
		Anchorpoint clone = new Anchorpoint(point1);
		assertEquals(clone.getAnchorpointType(),point1.getAnchorpointType());
		assertFalse(clone.getItem()==point1.getItem());
	}
	
	@Test
	public void cloneTest2(){
		Anchorpoint clone = new Anchorpoint(point8);
		assertEquals(clone.getAnchorpointType(),point8.getAnchorpointType());
		assertEquals(clone.getItem(),null);
	}
	
	@Test
	public void cloneTest3(){
		Anchorpoint clone = new Anchorpoint(point7);
		assertEquals(clone.getAnchorpointType(),point7.getAnchorpointType());
		assertEquals(clone.getItem(),null);
	}
}
