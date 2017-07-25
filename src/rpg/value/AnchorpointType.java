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
	 * Initialize the given anchor point with given type.
	 * 
	 * @param anchorpoint type
	 * 		  The anchor point type for this new anchor point type.
	 * @post  The anchor point type of this anchor point type is set to the given achor point type.
	 * 		  | new.getAnchorpointType().equals(anchorpoint)
	 */
	@Raw
	private AnchorpointType(String anchorpoint){
		this.anchorpointType = anchorpoint;
	}
	

	
	/**
	 * Return the type for the given ordinal.
	 * 
	 * @param ordinal
	 * 		  The ordinal to get the type from.
	 * @pre the ordinal must lay between 0 and NbOfAnchorpointTypes()
	 * 		| ordinal >=0 && ordinal <= NbOfAnchorpointTypes()
	 * @return the type for the given ordinal
	 * 		   | for  type in AnchorpointType.values()
	 * 		   |	if (type.ordinal() == ordinal)
	 * 		   |		then result.equals(type)
	 */
	public static AnchorpointType getTypeFromOrdinal(int ordinal){
		for (AnchorpointType type:AnchorpointType.values()){
			if (type.ordinal() == ordinal){
				return type;
			}
		}
		return null;
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
