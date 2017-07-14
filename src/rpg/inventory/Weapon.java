package rpg.inventory;
import be.kuleuven.cs.som.annotate.*;
import rpg.utility.IDGenerator;
import rpg.utility.WeaponIDGenerator;

/**
 * A class of weapons.
 * 
 * @author Robbe
 * @version 1.0
 */
public class Weapon extends Item {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a weapon.
	 */
	@Raw
	public Weapon() {
		super();
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
