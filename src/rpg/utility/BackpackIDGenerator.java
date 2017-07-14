package rpg.utility;

import be.kuleuven.cs.som.annotate.*;

/**
 * A binomial generator that generates IDs for backpacks.
 *		This generator uses the recursive formula to compute the coefficients.
 *
 * @invar The generator must have a valid position.
 * 	      | isValidPostion(getPosition())
 * 
 * @author Robbe
 * @version  1.0
 */
public class BackpackIDGenerator implements IDGenerator {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a BackpackIDGenerator with given position.
	 * 
	 * @param position
	 * 		  The position of the generator.
	 * @effect the generator is set at the given position if the given position is valid.
	 * 	       | if (isValidPosition(position)
	 * 		   |	setPosition(position)
	 * @effect the generator is reset if the given position is not valid.
	 * 		   | else
	 * 		   |	reset()
	 */
	@Raw
	public BackpackIDGenerator(int position){
		if (isValidPosition(position)){
			setPosition(position);
		}
		else {
			reset();
		}
	}
	
	/**
	 * Initializes a BackpackIDGenerator.
	 * 
	 * @effect resets the BackpackIDGenerator
	 * 		   | reset()
	 */
	@Raw
	public BackpackIDGenerator(){
		reset();
	}
	
	/************************************************
	 * Generator
	 ************************************************/
	
	/**
	 * Generates the nextID.
	 * 
	 * @effects increments the position.
	 *  		| setPosition(getPosition()+1)
	 * @return the binominal coefficient of the when entering the method.
	 * 	       | result == calculateBinomialCoefficient(getPosition()-1)
	 */
	@Override
	public long nextID() {
		setPosition(getPosition()+1);
		return calculateBinomialCoefficient(getPosition()-1);
	}
	
	/**
	 * Checks whether the generator can generate a next ID.
	 * 
	 * @return true if the position is valid.
	 * 		   | isValidPosition(getPosition())
	 */
	@Override
	public boolean hasNextID() {
		return isValidPosition(getPosition());
	}
	
	/**
	 * Resets the generator.
	 * 
	 * @effect sets the position at 0.
	 * 		   | setPosition(0)
	 */
	@Override
	public void reset() {
		setPosition(0);
		
	}

	/**
	 * Generates an ID.
	 * 
	 * @effect if the generator has no nextID, the generator gets reset.
	 * 		   | if (!hasNextID())
	 * 		   |	then reset()
	 * @return the next ID
	 * 		   |	result == nextID()
	 */
	@Override
	public long generateID() {
		if(!hasNextID()){
			reset();
		}
		return nextID();
	}
	/************************************************
	 * position
	 ************************************************/
	
	/**
	 * Return the current position of the generator.
	 */
	public int getPosition(){
		return this.position;
	}
	
	/**
	 * Checks whether a position is valid or not.
	 * 
	 * @param position
	 * 		  The position to check.
	 * @return false if the position is less than zero.
	 * 	       | if (position<0)
	 * 		   |	then result == false
	 * @return false if the binomial coeff. of the position has a greater 
	 * 		   value than Long.MAX_VALUE.
	 * 		   | if (calculateBinomialCoefficient(position)>=Long.MAX_VALUE)
	 * 		   |		then result == false
	 * @return true otherwise.
	 */
	public boolean isValidPosition(int position){
		if (position<0){
			return false;
		}
		if (calculateBinomialCoefficient(position)>=Long.MAX_VALUE){
			return false;
		}
		return true;
	}
	
	/**
	 * Sets the position at the given amount.
	 * 
	 * @param amount
	 * 		  the new amount of the position.
	 * @post the position gets set at the given amount
	 * 		 | this.position = amount
	 */
	@Model
	private void setPosition(int amount){
		this.position = amount;
	}
	
	/**
	 * A variable referencing the current position of the generator.
	 */
	private int position;
	
	/************************************************
	 * Calculation
	 ************************************************/

	/**
	 * Calculates the binomial coeff. of a given number.
	 * 
	 * @param number
	 * 		  The number of which the binomial coeff. is calculated.
	 * @return the binomial coeff. number of the given number.
	 * 		  | let sum = 0
	 * 		  | for I in 0...number
	 * 		  |		sum = sum + nChoosheK(number,i)
	 * 		  | result == sum
	 */
	@Model
	public static long calculateBinomialCoefficient(int number){
		long sum = 0;
		for (int i=0;i<=number;i++){
			sum += nChooseK(number,i);
		}
		return sum;
	}
	
	/**
	 * Calculates the ways one can arrange k objects in n places, ignoring ordering.
	 * 
	 * @param n
	 * 		  The possible places.
	 * @param k
	 * 		  the k objects.
	 * @return 0 if n is equal to k or k is zero/
	 * 		  | if (n==k||k==0)
	 * 	 	  |		then result == 1
	 * @return a factor multiplied with nChooseK
	 * 		   | let k = k -1
	 * 		   | result == (n-k)/(k+1)*nChooseK(n,k)
	 */
	@Model
	private static double nChooseK(double n,double k){
		if (n==k||k==0){
			return 1;
		}
		else {
			k = k-1;
			return (n-k)/(k+1)*nChooseK(n,k);
		}
	}
}
