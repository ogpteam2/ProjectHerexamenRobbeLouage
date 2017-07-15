package rpg.inventory;
import be.kuleuven.cs.som.annotate.*;
import rpg.Mobile;
import rpg.utility.IDGenerator;
import rpg.utility.WeaponIDGenerator;
import rpg.value.Weight;

/**
 * A class of weapons with ID , given weight, value and holder.
 * 
 * @invar Each weapon must have a valid damage.
 * 		  | isValidDamage(getDamage())
 * @author Robbe
 * @version 1.0
 */
public class Weapon extends Item {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a weapon with a weight,value,holder.
	 * 
	 * @effect the weapon is an item with given weigh,value and holder.
	 * 		   | super(weight,value,holder)
	 */
	@Raw
	public Weapon(Weight weight,int value,Mobile holder) {
		super(weight,value,holder);
	}
	
	/**
	 * Initializes a weapon with a weight,value,holder.
	 * 
	 * @effect the weapon is an item with given weigh,value.
	 * 		   | super(weight,value,null)
	 */
	@Raw
	public Weapon(Weight weight,int value){
		this(weight,value,null);
	}
	
	/************************************************
	 * ID
	 ************************************************/
	
	/**
	 * Returns the generator for weapons.
	 */
	@Raw @Basic @Override
	protected IDGenerator getIDGenerator() {
		return generator;
	}
	
	/**
	 * A variable referencing the generator for this class that generates IDs.
	 */
	private static WeaponIDGenerator generator = new WeaponIDGenerator();
	
}
