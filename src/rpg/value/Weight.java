package rpg.value;
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
	 * @post if the given numeral is not valid or the given
	 * 		 unit is not valid then a standard weight is initialized with
	 * 	     a numeral of 0 and a unit kg.
	 * 		 | if (!isValidNumeral(numeral) || ! isValidUnit(unit))
	 *       | 		this.getNumeral() == 0d
			 |		this.getUnit().equals(Unit.kg)
	 */
	@Raw
	public Weight(double numeral,Unit unit){
		if (!isValidNumeral(numeral) || ! isValidUnit(unit)){
			this.numeral = 0d;
			this.unit = Unit.kg;
		}
		else {
			this.numeral = numeral;
			this.unit = unit;
		}
		
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
	{
		this(numeral,Unit.kg);
	}
	
	/**
	 * Initialize a new weight with numeral zero and unit kg
	 * 
	 * @effect The new weight is initialized with the 1.0 as numeral
	 *         and the unit "kg". 
	 *         | this(1.0, Unit.kg)
	 */
	public Weight(){
		this(1.0, Unit.kg);
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
	public static boolean isValidUnit(Unit unit) {
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
	 */       
	public Weight toUnit(Unit unit)
		throws IllegalArgumentException
	{
		if (!isValidUnit(unit))
			throw new IllegalArgumentException("non effective unit");
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
	
	/**
	 * Compare this capacity amount with another weight.
	 * 
	 * @param other
	 *        The other weight to compare with this one.
	 * @return The result is equal to the comparison between this numeral
	 * 		   and the other numeral if they have the same unit
	 *         | if getUnit().equals(other.getUnit())
	 *         |	then result == Double.compare(this.getNumeral(), other.getNumeral())
	 * @return If they have a different unit the other unit is converted in this unit and
	 * 	       they are their numerals are compared.
	 * 		   | if getUnit() != other.getUnit()
	 * 	       |	then let converted = other.toUnit(this.getUnit())
	 * 		   |		result == Double.compare(this.getNumeral(), converted.getNumeral())
	 * @throws  ClassCastException
	 *         the other weight is not effective.
	 *		   | (other == null) 
	 */		
	@Override
	public int compareTo(Weight other)
		throws ClassCastException
	{
		if (other == null)
			throw new ClassCastException("Non-effective weight");
		if (getUnit() != other.getUnit()){
			Weight converted = other.toUnit(this.getUnit());
			return Double.compare(this.getNumeral(), converted.getNumeral());
		}
		return Double.compare(this.getNumeral(), other.getNumeral());
	}
	
	/**
	 * Checks whether this weight has the same value as the other one.
	 * 
	 * @param other
	 * 		  The other weight to compare this with.
	 * @return True iff this weight is equal to the other one
	 *         expressed in the unit of this weight.
	 *        | result == this.equals(other.toUnit(getUnit()) 
	 * @throws IllegalArgumentException
	 * 		   The other capacity amount is not effective.
	 * 		   | other == null
	 */
	public boolean hasSameValue(Weight other)
		throws IllegalArgumentException
	{
		if (other == null)
			throw new IllegalArgumentException("Non-effective weight");
		return this.equals(other.toUnit(this.getUnit()));
	}
	
	/**
	 * Checks whether this weight is equal to the given object.
	 * 
	 * @return True iff the given object is effective, if this weight
	 * 		   and the given object belong to the same class. and if this 
	 * 		   weight and the other object interpreted as a 
	 * 		   weight have equal numerals and equal units.
	 * 		   | if other == null
	 * 		   | 	result == false
	 * 		   | if (this.getClass() != other.getClass())
	 * 		   | 	result == false
	 * 		   | let otheramount = other
	 * 		   | 	result == (this.getNumeral() == otherAmount.getNumeral() &&
	 * 		   |				this.getUnit().equals(otherAmount.getUnit()))
	 */
	@Override
	public boolean equals(Object other){
		if (other == null)
			return false;
		if (this.getClass() != other.getClass())
			return false;
		Weight otherAmount = (Weight) other;
		return (this.getNumeral() == otherAmount.getNumeral() &&
				this.getUnit().equals(otherAmount.getUnit()));
	}
	
	/************************************************
	 * Arithmetic
	 ************************************************/

	/**
	 * Compute the sum of this weight and the given weight. 
	 * 
	 * @param other
	 * 	      the other weight that should be added to this weight.
	 * @return A new weight that has a numeral which is the sum of the 2 weights,
	 * 		   the new unit is set to this.getUnit()
	 * 		  | let newNumeral =  this.getNumeral()+other.toUnit(this.getUnit()).getNumeral()
	 * 		  | (result.getNumeral() == (newNumeral) &&
	 * 		  | 	result.getUnit().equals(this.getUnit()))
	 * @return this weight if the given weight is not effective.
	 * 		  | if (other==null)
	 * 		  |		return this
	 * 
	 */
	public Weight add(Weight other){
		if (other==null)
				return this;
		double newNumeral = this.getNumeral()+other.toUnit(this.getUnit()).getNumeral();
		return new Weight(newNumeral,this.getUnit());
	}
	
	/**
	 * Compute the difference of this weight and the given weight. 
	 * 
	 * @param other
	 * 	      the other weight that should be subtracted  to this weight.
	 * @return A new weight that has a numeral which is the difference of the 2 weights,
	 * 		   the new unit is set to this.getUnit()
	 * 		  | let newNumeral =  this.getNumeral()-other.toUnit(this.getUnit()).getNumeral()
	 * 		  | (result.getNumeral() == (newNumeral) &&
	 * 		  | 	result.getUnit().equals(this.getUnit()))
	 * @return this weight if the given weight is not effective.
	 * 		  | if (other==null)
	 *		  |		return this
	 * 
	 */
	public Weight substract(Weight other){
		if (other==null)
			return this;
		double newNumeral = this.getNumeral()-other.toUnit(this.getUnit()).getNumeral();
		return new Weight(newNumeral,this.getUnit());
	}
	
	/**
	 * Compute the product of this weight and the given weight. 
	 * 
	 * @param other
	 * 	      the other weight that should be multiplied  to this weight.
	 * @return A new weight that has a numeral which is the multiple of the 2 weights,
	 * 		   the new unit is set to this.getUnit()
	 * 		  | let newNumeral =  this.getNumeral()*other.toUnit(this.getUnit()).getNumeral()
	 * 		  | (result.getNumeral() == (newNumeral) &&
	 * 		  | 	result.getUnit().equals(this.getUnit()))
	 * @return this weight if the given weight is not effective.
	 * 		  | if (other==null)
	 *		  |		return this
	 * 
	 */
	public Weight times(Weight other){
		if (other==null)
			return this;
		double newNumeral = this.getNumeral()*other.toUnit(this.getUnit()).getNumeral();
		return new Weight(newNumeral,this.getUnit());
	}
	
	/**
	 * Compute the product of this weight and a given double. 
	 * 
	 * @param factor
	 * 	      the  factor that should be multiplied  to this weight.
	 * @return A new weight that has a numeral which is the product of the weight and the factor,
	 * 		   the new unit is set to this.getUnit()
	 * 		  | let newNumeral =  this.getNumeral()*factor
	 * 		  | (result.getNumeral() == (newNumeral) &&
	 * 		  | 	result.getUnit().equals(this.getUnit()))
	 * @return this weight if the given weight is not effective.
	 * 		  | if (other==null)
	 *		  |		return this
	 * 
	 */
	public Weight times(double factor){
		double newNumeral = this.getNumeral()*factor;
		return new Weight(newNumeral,this.getUnit());
	}
	
	/**
	 * Compute the quotient  of this weight and the given weight. 
	 * 
	 * @param other
	 * 	      the other weight that should be multiplied  to this weight.
	 * @return A new weight that has a numeral which is the multiple of the 2 weights,
	 * 		   the new unit is set to this.getUnit()
	 * 		  | let newNumeral =  this.getNumeral()/other.toUnit(this.getUnit()).getNumeral()
	 * 		  | (result.getNumeral() == (newNumeral) &&
	 * 		  | 	result.getUnit().equals(this.getUnit()))
	 * @return this weight if the given weight is not effective or the other weight has a very small 
	 * 		   numeral.
	 * 		  | if (other==null || other.getNumeral()<=10E-10)
	 *		  |		return this
	 * 
	 */
	public Weight divide(Weight other){
		if (other==null || other.getNumeral()<=10E-10) // protection against division by zero
			return this;
		double newNumeral = this.getNumeral()/other.toUnit(this.getUnit()).getNumeral();
		return new Weight(newNumeral,this.getUnit());
	}
}
