package rpg.inventory;
import be.kuleuven.cs.som.annotate.*;
import rpg.utility.IDGenerator;

/**
 * A class of items with given ID, weight, value and holder.
 * 
 * @invar Each weapon must have a valid weight.
 * 		  | isValidWeight(getWeight())
 * @author Robbe
 * @version
 */
abstract public class Item {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes an item.
	 * 
	 * @effect the ID is generated and set.
	 * 		   | this.ID = generateID()
	 */
	@Raw
	protected Item(){
		this.ID = generateID();
	}
	
	
	/************************************************
	 * ID
	 ************************************************/
	
	/**
	 * Return the ID of this item.
	 */
	@Raw @Basic 
	public long getId(){
		return this.ID;
	}
	
	/**
	 * Returns the static IDGenerator for a specific item.
	 * 
	 * @note The static IDGenerator is implemented in each subclass.
	 */
	@Raw
	protected abstract IDGenerator getIDGenerator();
	
	/**
	 * Generates an ID in accordance with the item type's ID specification.
	 * 
	 * @return an ID based on the ID generator inherent to each item type.
	 * 		   | result == getIDGenerator().generateID()
	 */
	private long generateID(){
		return getIDGenerator().generateID();
	}
	
	/**
	 * A variable referencing the ID of the item.
	 */
	private final long ID;
	
	/************************************************
	 * Weight
	 ************************************************/
	
	/************************************************
	 * Value
	 ************************************************/
	
	/************************************************
	 * Holder
	 ************************************************/
}
