package rpg.inventory;
import be.kuleuven.cs.som.annotate.*;
import rpg.Mobile;
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
	
	/**
	 * Initializes a deep copy of a given ducat.
	 * 
	 * @param other
	 * 		  the other ducat to deep copy.
	 * @effect the ducat gets deep copied as an Item.
	 * 		   | super(other)
	 */
	public Ducat(Ducat other){
		super(other);
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
	@Raw @Basic @Immutable
	public static Weight getDucatWeight(){
		return ducatWeight;
	}
	
	/**
	 * Returns the value of a ducat.
	 */
	@Raw @Basic @Immutable
	public static int getDucatValue(){
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
	
	
	/************************************************
	 * setters
	 ************************************************/
	
	/**
	 * Does nothing, makes sure that a ducat never has a holder.
	 */
	@Override
	public void setHolder(Mobile holder){}
	/**
	 * Makes sure that the value of a Ducat never changes.
	 * 
	 * @effect sets the value of the Ducat to the ducat value.
	 * 		   | new.getValue()==Ducat.getDucatValue()
	 */
	@Override
	public void setValue(int value){
		super.setValue(Ducat.getDucatValue());
	}
}