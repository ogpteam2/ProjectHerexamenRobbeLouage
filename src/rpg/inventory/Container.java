package rpg.inventory;
import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.*;
import rpg.Mobile;
import rpg.utility.IDGenerator;
import rpg.utility.WeaponIDGenerator;
import rpg.value.Weight;

/**
 * A class of containers, meaning items where one can store items in.
 * 
 * @invar Each container must have valid contents
 * 		  | canHaveAsContents(contents)
 * 
 * @author Robbe
 * @version 1.0
 */
abstract public class Container extends Item {

	/************************************************
	 * Constructors
	 ************************************************/
	
	protected Container(Weight weight, int value, Mobile holder) {
		super(weight, value,holder);
	}
	
	protected Container(Weight weight, int value) {
		this(weight, value,null);
	}
	
	/************************************************
	 * Content
	 ************************************************/
	
	
	
	public void addItem(){
		
	}
	
	/**
	 * A variable keeping track of all items stored in the backpack.
	 */
	private ArrayList<Item> contens = new ArrayList<Item>();
	/************************************************
	 * Weight
	 ************************************************/
	/************************************************
	 * Value
	 ************************************************/
	/************************************************
	 * Capacity
	 ************************************************/

}
