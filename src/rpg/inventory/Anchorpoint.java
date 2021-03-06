package rpg.inventory;

import be.kuleuven.cs.som.annotate.*;
import rpg.value.AnchorpointType;

/**
 * A class of anchorpoints with a given item and an anchorpoint type.
 * 
 * @author Robbe
 * @version 1.0
 */
public class Anchorpoint {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a anchorpoint with given type and item.
	 * 
	 * @param anchorpointType
	 * 		  The type of the anchorpoint.
	 * @param item
	 * 		  The item to set at this anchorpoint.
	 * @post the anchorpoint type is set to the given type
	 * 	     | new.getAnchorpointType().equals(anchorpointType)
	 * @post the item is set to the given item
	 * 	     | new.getItem().equals(item)
	 */
	public Anchorpoint(AnchorpointType anchorpointType,Item item){
		this.anchorpointType = anchorpointType;
		this.item = item;
	}
	
	/**
	 * Initializes a anchorpoint with no item.
	 * 
	 * @param anchorpointType
	 * 		  The anchorpointType of this anchorpoint.
	 * @effect the anchorpoint is initialized with given type and no item.
	 * 		   | this(anchorpointType,null)
	 */
	public Anchorpoint(AnchorpointType anchorpointType){
		this(anchorpointType,null);
	}
	
	/**
	 * Initializes a empty acnhorpoint
	 * 
	 * @effect the anchorpoint is initialized empty.
	 * 		   | this(null,null)
	 */
	public Anchorpoint(){
		this(null,null);
	}
	
	/************************************************
	 * type
	 ************************************************/
	
	/**
	 * Returns the anchorpoint type of this anchorpoint.
	 */
	@Basic @Immutable 
	public AnchorpointType getAnchorpointType(){
		return this.anchorpointType;
	}
	
	/**
	 * A variable referencing the anchorpoint type of this anchorpoint.
	 */
	private final AnchorpointType anchorpointType;
	
	/************************************************
	 * item
	 ************************************************/
	
	/**
	 * Returns the item of this anchorpoint.
	 */
	@Basic 
	public Item getItem(){
		return this.item;
	}
	
	/**
	 * Sets the given item as new item.
	 * 
	 * @param item
	 * 		 the new item.
	 * @post the given item is set as the new item.
	 * 		 | new.getItem().equals(item)
	 */
	public void setItem(Item item){
		this.item = item;
	}
	
	/**
	 * A variable referencing the item in the anchorpoint.
	 */
	private Item item; 
}
