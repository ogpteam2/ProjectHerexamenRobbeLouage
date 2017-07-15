package rpg.inventory;
import be.kuleuven.cs.som.annotate.*;
import rpg.utility.DucatIDGenerator;
import rpg.utility.IDGenerator;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * A class of Ducats, the unit of money in the rpg.
 * 
 * @author Robbe
 * @version 1.0
 */
public class Ducat extends Item{
	
	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a ducat.
	 * 
	 * @effect the ducat is initializes with the ducatWeight, ducatValue.
	 * 	       | super(ducatWeight,ducatValue)
	 */
	public Ducat() {
		super(ducatWeight,ducatValue);
	}

	/************************************************
	 * getter and static variables
	 ************************************************/
	/**
	 * Generates an ID for each ducat.
	 * 
	 * @return an IDGenerator
	 * 		   | result.equals(generator)
	 */
	@Override
	protected IDGenerator getIDGenerator() {
		return generator;
	}
	
	/**
	 * Returns the weight of a ducat.
	 */
	@Override
	public Weight getWeight(){
		return ducatWeight;
	}
	
	/**
	 * Returns the value of a ducat.
	 */
	@Override
	public int getValue(){
		return ducatValue;
	}

	/**
	 * A variable referencing the generator.
	 */
	private static DucatIDGenerator generator = new DucatIDGenerator();
	/**
	 * A variable referencing the ducatWeight.
	 */
	private static final Weight ducatWeight = new Weight(50,Unit.g);
	/**
	 * A variable referencing the ducatValue.
	 */
	private static final int ducatValue = 1;
}
