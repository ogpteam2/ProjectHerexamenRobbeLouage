package rpg.value;

import java.math.BigDecimal;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of weights with a given numeral and a unit.
 * 
 * @invar The numeral of each weight must be valid.
 * 		  | isValidNumeral(getNumeral())
 * @invar The unit of each weight must be a valid unit.
 * 		  | isValidUnit(getUnit())
 * @author Robbe
 * @version 1.0
 */
@Value
public class Weight implements Comparable<Weight> {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initialize the new weight with given numeral and given unit.
	 * 
	 * @param numeral
	 * 		  The numeral for this new weight.
	 * @param unit
	 * 		  The unit for this new weight.
	 * @post  The numeral of this weight is set to the given numeral 
	 * 		  | new.getNumeral() == numeral
	 * @post The unit for this new weight is the same as the given unit.
	 * 		 | new.getUnit() == unit
	 * @throws IllegalArgumentException
	 *   	   The given numeral is not effective.
	 *   	   | numeral == null
	 * @throws IllegalArgumentException
	 *    	   The given unit is not a valid unit for any weight.
	 *         | ! isValidUnit()
	 */
	@Raw
	public Weight(double numeral,Unit unit){
		if (numeral < 0.0)
			throw new IllegalArgumentException("Non-effective numeral");
		if (! isValidUnit(unit))
			throw new IllegalArgumentException("Invalid unit");
		this.numeral = numeral;
		this.unit = unit;
	}
	
	/**
	 * Initialize this new weight with given numeral and unit "kg".
	 * 
	 * @param numeral
	 * 		  The numeral for this new weight.
	 * @effect The new weight is initialized with the given numeral
	 *         and the unit "kg".
	 *         | this(numeral, Unit.kg)
	 */
	@Raw
	public Weight(double numeral)
			throws IllegalArgumentException
	{
		this(numeral,Unit.kg);
	}
	
	/**
	 * Initialize a new weight with numeral zero and unit kg
	 * 
	 * @effect The new weight is initialized with the 0.0 as numeral
	 *         and the unit "kg". 
	 *         | this(0.0, Unit.kg)
	 */
	public Weight(){
		this(0.0, Unit.kg);
	}
	
	/************************************************
	 * Numeral
	 ************************************************/
	
	/**
	 * Variable referencing a weight of 0.0 kg
	 */
	public final static Weight kg_0 = 
			new Weight(0.0,Unit.kg);
	/**
	 * Return the numeral of this capacity amount.
	 */
	@Basic @Raw @Immutable
	public double getNumeral(){
		return this.numeral;
	}
	
	/**
	 * Checks whether the given numeral is a valid numeral for any weight.
	 * 
	 * @param numeral
	 * 		  The numeral to check.
	 * @return True iff the given numeral is effective and if it has a scale of 2.
	 * 		   | result ==
	 * 		   | 	( (numeral >= 0.0)
	 */
	public static boolean isValidNumeral(double numeral){
		return (numeral >= 0.0); 
	}
	
	/**
	 * A variable that references the numeral of this weight.
	 */
	private final double numeral;
	
	
	/************************************************
	 * Unit
	 ************************************************/
	
	/**
	 * Returns the unit of this weight.
	 */
	@Basic @Raw @Immutable
	public Unit getUnit(){
		return this.unit;
	}
	
	/**
	 * Checks whether a given unit is valid for any weight.
	 * 
	 * @param unit
	 *        The unit to check
	 * @return True if the given unit is effective.
	 *         | result == (! unit == null)
	 */
	private boolean isValidUnit(Unit unit) {
		return (unit!=null);
	}
	
	/**
	 * Returns a weight that has the same value as this weight
	 * expressed in the given unit.
	 * 
	 * @param unit
	 *        The unit in which to current weight will be converted.
	 * @return The resulting weight has the given unit as its unit.
	 *         | result.getUnit() == unit
	 * @return The numeral of the resulting weight is equal to the numeral
	 * 		   of this weight multiplied with the conversion rate from the 
	 *         unit of this weight to the given unit.
	 *         | let conversionRate = this.getUnit().toUnit(unit)
	 *         |     numeralInCurreny = this.getNumeral()*(conversionRate)
	 *         | in result.getNumeral() == numeralInCurreny
	 * @throws IllegalArgumentException
	 * 		   The given unit is not effective
	 *  	   | unit == null
	 */       
	public Weight toUnit(Unit unit)
		throws IllegalArgumentException
	{
		if (unit == null)
			throw new IllegalArgumentException("Non-effective unit");
		if (this.getUnit() == unit)
			return this;
		double conversionRate = this.getUnit().toUnit(unit);
		double numeralInUnit = getNumeral()*conversionRate;
		return new Weight(numeralInUnit,unit);
	}
	
	/**
	 * variable referencing the unit of this weight.
	 */
	private final Unit unit;
	
	/************************************************
	 * Utility
	 ************************************************/
	
	/**
	 * Return the hash code for this weight.
	 */
	@Override
	public int hashCode(){
		Double wrapper = new Double(this.getNumeral());
		return wrapper.hashCode() + getUnit().hashCode();
	}
	
	/**
	 * Return a textual representation of this weight.
	 * 
	 * @return A string consisting of the textual representation
	 * 		   of the numeral of this weight, followed by
	 * 		   the textual representation of its unit, separated by a space
	 * 		   and enclosed in square brackets.
	 * 	       |result.equals("[" + getNumeral().toString() +
	 * 		   |		 " " + getUnit().toString() + "]")
	 */
	@Override
	public String toString(){
		Double wrapper = new Double(this.getNumeral());
		return "[" + wrapper.toString() + " " + getUnit().toString() + "]";
	}
	
	/************************************************
	 * Logic
	 ************************************************/
	
	@Override
	public int compareTo(Weight arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/************************************************
	 * Arithmetic
	 ************************************************/


}
