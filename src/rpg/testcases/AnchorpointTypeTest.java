package rpg.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rpg.value.AnchorpointType;

public class AnchorpointTypeTest {
	
	public static AnchorpointType back,belt,right,left,body;
	
	@Before
	public void setup(){
		 back = AnchorpointType.BACK;
		 belt = AnchorpointType.BELT;
		 right = AnchorpointType.RIGHT;
		 left = AnchorpointType.LEFT;
		 body = AnchorpointType.BODY;
	}
	
	@Test
	public void getTest() {
		assertEquals(back.getAnchorpointType(),"BACK");
		assertEquals(belt.getAnchorpointType(),"BELT");
		assertEquals(right.getAnchorpointType(),"RIGHT");
		assertEquals(left.getAnchorpointType(),"LEFT");
		assertEquals(body.getAnchorpointType(),"BODY");
	}
	
	@Test
	public void ordinalTest() {
		assertEquals(back.ordinal(),1);
		assertEquals(belt.ordinal(),2);
		assertEquals(right.ordinal(),3);
		assertEquals(left.ordinal(),4);
		assertEquals(body.ordinal(),0);
	}
	
	@Test
	public void numberTest(){
		assertEquals(AnchorpointType.NbOfAnchorpointTypes(),5);
	}
	
	@Test
	public void ordinalTest2(){
		assertEquals(AnchorpointType.getTypeFromOrdinal(0),AnchorpointType.BODY);
		assertEquals(AnchorpointType.getTypeFromOrdinal(1),AnchorpointType.BACK);
		assertEquals(AnchorpointType.getTypeFromOrdinal(2),AnchorpointType.BELT);
		assertEquals(AnchorpointType.getTypeFromOrdinal(3),AnchorpointType.RIGHT);
		assertEquals(AnchorpointType.getTypeFromOrdinal(4),AnchorpointType.LEFT);
	}


}
