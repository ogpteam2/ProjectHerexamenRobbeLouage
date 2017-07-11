package rpg.value;

import java.math.BigDecimal;

import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration introducing different units of mass used to express an
 * amount of mass.
 * 		In its current form, the class only supports the kilogram,
 * 		the gram and the pound(lbs).
 * 
 * @version 1.0
 * @author Robbe
 *
 */
@Value
public enum Unit {
	
	kg("kilogram"), g("gram"),lbs("pound");    
	
	/**
	 * Initialize this unit with the given unit.
	 *
	 * @param unit
	 * 		  The unit for this new unit.
	 * @post The unit for this new unit is equal to the given unit.
	 * 		 | new.getUnit() == unit	
	 */
	@Raw
	private Unit(String unit){
		this.unit = unit;
	}
	
	/**
	 * Return the unit for this unit.
	 */
	@Basic @Raw @Immutable
	public String getUnit(){
		return this.unit;
	}
	
	/**
	 * Return the precision for units.
	 */
	@Basic @Raw @Immutable
	public int getPrecision(){
		return precision;
	}
	
	/**
	 * Return the value of 1 unit to the other unit.
	 * 
	 * @param other
	 * 		  The unit to convert to.
	 * @return The resulting conversion rate is positive.
	 * 		   | result.signum == 1 
	 * @return If the unit is the same as the other
	 * 		   1d is returned.
	 * 		   | if (this == other)
	 *         | then (result == 1d)
	 * @return If the unit is not the same as the other the 
	 * 		   resulting conversion rate has the precision as 
	 * 		   established by the unit context.
	 * 	       | if (this != other)
	 * 		   | then (result.precision() ==
	 * 		   | 			unitContext.getPrecision())
	 * @return The resulting conversion rate is the inverse of the unit
	 * 		   conversion rate from the other unit to this unit.
	 * 		   | result.equals
	 * 		   |	(1/(other.toUnit(this).unitContext))
	 * @throws IllegalArgumentException
	 * 		   The given unit is not effective
	 * 		   | (other == null)
	 */
	public double toUnit(Unit other) throws IllegalArgumentException {
		if (other==null){
			throw new IllegalArgumentException("ineffective unit");
		}
		if (conversionRates[this.ordinal()][other.ordinal()] == 0.0){
			conversionRates[this.ordinal()][other.ordinal()] =
					1/(conversionRates[other.ordinal()][this.ordinal()]);
		}
		return Math.round(conversionRates[this.ordinal()][other.ordinal()]*100.0)/100.0;
	}
	


	/**
	 * Variable referencing a two-dimensional array registering
	 * conversion rates between units. The first level is indexed
	 * by the ordinal number of the unit of the unit to convert from; 
	 * the second number to convert to is used to index the second level.
	 */
	private static double[][] conversionRates = 
			new double[3][3];
	
	static {
		// Initialization of the upper part of the conversion table.
		// Other conversions are computed and registered the first time 
		// they are queried.
		conversionRates[kg.ordinal()][kg.ordinal()]=
				1d;
		conversionRates[kg.ordinal()][g.ordinal()]=
				1000d;
		conversionRates[kg.ordinal()][lbs.ordinal()]=
				2.20462262185d;
		conversionRates[g.ordinal()][g.ordinal()]=
				1d;
		conversionRates[g.ordinal()][lbs.ordinal()]=
				0.00220462262d;
		conversionRates[lbs.ordinal()][lbs.ordinal()]=
				1d;
	}
	
	/**
	 * A variable that stores the precision of the units.
	 */
	private static final int precision = 2;
	
	/**
	 * A variable storing the unit of this unit.
	 */
	private final String unit;
	
}
