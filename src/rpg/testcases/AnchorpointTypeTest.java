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

}
