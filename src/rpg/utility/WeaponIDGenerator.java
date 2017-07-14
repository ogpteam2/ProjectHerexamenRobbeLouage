package rpg.utility;
import be.kuleuven.cs.som.annotate.*;

/**
 * An implementation of the IDgenerator interface, generates ID for weapons.
 * 		A weapon must have a ID that is positive and is even and divisible by 3
 * 			or simplified divisible by 6.	
 * 
 * @invar The counter must always be a strictly positive integer and must be able to
 * 		   have a next ID.
 * 		  | isValidCounter(getCounter())
 * @author Robbe
 * @version 1.0
 */
public class WeaponIDGenerator implements IDGenerator {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a weapon ID generator with given startCounter.
	 * 
	 * @effect If the given start is valid the counter is set to that position.
	 * 	     | if (isValidCounter(start)
	 * 		 |		then setCounter(start)
	 * @effect the weapon ID generator is reset if the start is not valid.
	 * 		   | if (!isValidCounter(start)
	 * 		   | 	then reset()
	 */
	@Raw
	public WeaponIDGenerator(long start) {
		if (isValidCounter(start)){
			setCounter(start);
		}
		else {
			reset();
		}
	}
	
	
	/**
	 * Initializes a weapon ID generator.
	 * 
	 * @effect the weapon ID generator is reset.
	 * 		   | reset()
	 */
	@Raw
	public WeaponIDGenerator() {
		reset();
	}
	
	/************************************************
	 * Generator
	 ************************************************/
	
	/**
	 * Returns the next ID for a weapon.
	 * 
	 * @effect Increments the counter
	 * 		   | setCounter(getCounter()+1)
	 * @return The counter multiplied with 6.
	 * 		   | result == counter*6
	 */
	@Override
	public long nextID() {
		setCounter(getCounter()+1);
		return counter*6;
	}
	
	/**
	 * Checks whether the generator has an next ID.
	 * 
	 * @return true if it has a valid counter.
	 * 		   | result == isValidCounter(getCounter())
	 */
	@Override
	public boolean hasNextID() {
		return isValidCounter(getCounter());
	}

	/**
	 * Resets the counter to 1.
	 * 
	 * @effect Sets counter to 1.
	 * 	       | setCouter(1)
	 */
	@Override
	public void reset() {
		setCounter(1);
	}

	/**
	 * Generates an next ID.
	 * 
	 * @effect resets the counter if the generator has no next ID.
	 * 		  | if(!hasNextID())
	 * 		  |		then reset()
	 * @return the next ID.
	 * 		   | result == nextID()
	 */
	@Override
	public long generateID() {
		if(!hasNextID()){
			reset();
		}
		return nextID();
	}
	

	/************************************************
	 * Counter
	 ************************************************/
	
	/**
	 * Return the counter.
	 */
	@Raw @Basic
	public long getCounter(){
		return this.counter;
	}
	
	/**
	 * Checks whether a given counter is a valid one.
	 * 
	 * @param counter
	 * 		  The counter to check.
	 * @return	True if the counter is strictly positive and if the counter
	 * 		    can be multiplied by six without an overflow.
	 * 	        | if (counter<=0)
	 * 		    | 	then result == false
	 * 		    | else if ((counter+1)*6>Long.MAX_VALUE)
	 * 		    |		result == false
	 * 		   	| else result == true
	 */
	public static boolean isValidCounter(long counter){
		if (counter<=0)
			return false;
		try {
			Math.multiplyExact(counter+1, 6);
		}
		catch (ArithmeticException ex){
			return false;
		}
		return true;
	}
	
	/**
	 * Sets the counter to the given amount.
	 * 
	 * @param amount
	 * 	      The new amount for the counter.
	 * @post The counter is set to the given amount.
	 * 	     | (new).getCounter() == amount
	 */
	@Model
	private void setCounter(long amount){
		this.counter = amount;
	}
	
	/**
	 * A variable that keeps track of how many weapon's IDs have been generated.
	 */
	private long counter = 1;
}
