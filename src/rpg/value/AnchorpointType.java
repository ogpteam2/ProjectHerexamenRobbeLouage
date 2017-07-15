package rpg.value;


import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration introducing different anchor points.
 * 
 * @version 1.0
 * @author Robbe
 *
 */
@Value
public enum AnchorpointType {

	BODY("BODY"),BACK("BACK"),BELT("BELT"),RIGHT("RIGHT"),LEFT("LEFT");
	
	/**
	 * Initialize the given anchor point wit given anchorpoint.
	 * 
	 * @param anchorpoint type
	 * 		  The anchor point type for this new anchor point type.
	 * @post  The anchor point type of this anchor point type is set to the given achor point type.
	 * 		  | this.anchorpoint.equals(anchorpoint)
	 */
	@Raw
	private AnchorpointType(String anchorpoint){
		this.anchorpointType = anchorpoint;
	}
	
	/**
	 * Returns the anchorpoint type.
	 */
	@Raw @Basic @Immutable
	public String getAnchorpointType(){
		return this.anchorpointType;
	}
	
	/**
	 * Return the number of different types.
	 * 
	 * @return the number of different types.
	 * 		   | result ==  AnchorpointType.values().length
	 * 		  
	 */
	public static int NbOfAnchorpointTypes(){
		return AnchorpointType.values().length;
	}
	
	/**
	 * A variable referencing the type of this anchorpoint.
	 */
	private final String anchorpointType;
	

}
