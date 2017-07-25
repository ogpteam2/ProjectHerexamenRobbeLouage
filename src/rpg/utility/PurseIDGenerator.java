package rpg.utility;
import be.kuleuven.cs.som.annotate.Basic;

/**
 * A generator that generates IDs for purses.
 * 		The generated ID are number of the Fibonnaci sequence.
 * 
 * @author Robbe
 * @version 1.0
 */
public class PurseIDGenerator implements IDGenerator {
	
	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a purse generator.
	 * 
	 * @effect the purse generator gets reset.
	 * 	       | reset()
	 */
	public PurseIDGenerator(){
		reset();
	}
	
	/************************************************
	 * Generator
	 ************************************************/
	
	/**
	 * Generates the next number in the Fibonacci sequence.
	 * 
	 * @effect sets the current number to the previous number + itself.
	 * 		   | setCurrentNumber(getPreviousNumber()+getCurrentNumber())
	 * @effect Sets the previous number to the new current number minus itself
	 * 		   | setPreviousNumber(getCurrentNumber() - getPreviousNumber())
	 * @return The current number.
	 * 		   | result == getCurrentNumber();
	 */
	@Override
	public long nextID() {
		setCurrentNumber(getPreviousNumber()+getCurrentNumber());
		setPreviousNumber(getCurrentNumber() - getPreviousNumber());
		return getCurrentNumber();
	}
	
	/**
	 * Checks whether to generator has a next ID.
	 * 
	 * @return false if the current number plus the previous number is greater than 
	 * 	       Long.MAX_VALUE, true otherwise.
	 * 		   | if ((getCurrentNumber()+getPreviousNumber())>=Long.MAX_VALUE)
	 * 		   |	then result == false.
	 * 		   | else
	 * 		   |	result == true	   
	 */
	@Override
	public boolean hasNextID() {
		try{
			Math.addExact(getCurrentNumber(), getPreviousNumber());
		}
		catch (ArithmeticException ex){
			return false;
		}
		return true;
	}
	
	/**
	 * Resets the generator.
	 * 
	 * @effect Sets the current number to 1.
	 * 		   | setCurrentNumber(1)
	 * @effect Sets the previous number to 0.
	 * 	       | setPreviousNumber(0)
	 */
	@Override
	public void reset() {
		setCurrentNumber(1);
		setPreviousNumber(0);
		
	}
	
	/**
	 * Generates the next ID.
	 * 
	 * @effect if the generator has no next ID, than the generator gets reset.
	 * 		   | if (!hasNextID())
	 * 	       | 	then reset()
	 * @return the nextID
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
	 * Position
	 ************************************************/
	
	/**
	 * Return the current number.
	 */
	@Basic
	public long getCurrentNumber(){
		return this.currentNumber;
	}
	
	/**
	 * Return the previous number.
	 */
	@Basic
	public long getPreviousNumber(){
		return this.previousNumber;
	}
	
	/**
	 * Sets the current number to the given amount.
	 * 
	 * @param amount
	 * 		  The new current number.
	 * @post Sets the current number to the given amount.
	 * 		 | new.getCurrentNumber() = amount
	 */
	private void setCurrentNumber(long amount){
		 this.currentNumber = amount;
	}
	/**
	 * Sets the previous number to the given amount.
	 * 
	 * @param amount
	 * 		  The new previous number.
	 * @post Sets the previous number to the given amount.
	 * 		 | new.getPreviousNumber() = amount
	 */	
	private void setPreviousNumber(long amount){
		 this.previousNumber = amount;
	}

	/**
	 * A variable referencing the current Fibonacci number.
	 */
	private long currentNumber;
	
	/**
	 * A variable referencing the previous Fibonnaci number.
	 */
	private long previousNumber;
	
}
