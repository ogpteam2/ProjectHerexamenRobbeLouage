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
	@Override
	public long nextID() {
		setPosition(getPosition()+1);
		return calculateBinomialCoefficient(getPosition()-1);
	}

	@Override
	public boolean hasNextID() {
		return isValidPosition(getPosition());
	}

	@Override
	public void reset() {
		setPosition(0);
		
	}

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
	
	public int getPosition(){
		return this.position;
	}
	
	public boolean isValidPosition(int position){
		if (position<0){
			return false;
		}
		if (calculateBinomialCoefficient(position)>=Long.MAX_VALUE){
			return false;
		}
		return true;
	}
	
	@Model
	private void setPosition(int amount){
		this.position = amount;
	}
	
	private int position;
	
	/************************************************
	 * Calculation
	 ************************************************/

	private static long calculateBinomialCoefficient(int number){
		long sum = 0;
		for (int i=0;i<=number;i++){
			sum += nChooseK(number,i);
		}
		return sum;
	}
	
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
