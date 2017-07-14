package rpg;

import java.math.BigDecimal;
import java.math.RoundingMode;

import be.kuleuven.cs.som.annotate.*;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * An abstract class of movable objects in the rpg.
 * 
 * @invar Each mobile must have a valid name.
 * 		  | isValidName(getName)
 * @invar Each mobile must have a valid maximumHitpoints.
 * 		  | isValidMaximumHitpoints(getMaximumHitpoints())
 * @invar Each mobile must have a valid currentHitpoints.
 * 	      | canHaveAsValid(getCurrentHitpoints())
 * @invar Each Mobile must have a valid protection.
 * 	      | isValidProtection(getProtection())
 * @author Robbe
 *
 */
public abstract class Mobile {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initialize a new Mobile with given name, hitpoints and strength.
	 * 
	 * @param name
	 * 		  The name of the mobile.
	 * @param hitpoints
	 * 		  The hitpoints of the Mobile
	 * @param strength
	 * 	      The raw strength of the mobile.
	 * @effect The name of the mobile is set to the given name.
	 * 		   | setName(name)
	 * 
	 */
	@Raw
	protected Mobile(String name, long hitpoints, double strength) throws IllegalArgumentException{
		setName(name);
		setCurrentHitpoints(hitpoints);
		setMaximumHitpoints(hitpoints);
		setRawStrength(strength);
	}
	
	
	
	/************************************************
	 * Name: defensive
	 ************************************************/
	
	/**
	 * Returns the name of a Mobile.
	 */
	@Raw @Basic
	public String getName(){
		return this.name;
	}
	
	/**
	 * Checks whether a name is valid or not.
	 * 
	 * @param name
	 * 	      The name to check.
	 * @return false if the name is null, true otherwise
	 * 	       | if name == null
	 * 		   | 	result == false
	 * 		   | else 
	 * 		   | 	result == true
	 */
	public boolean isValidName(String name){
		if (name == null)
			return false;
		return true;
	} 
	
	/**
	 * Sets the name to the given name.
	 * 
	 * @param name
	 * 		  The new name of the mobile.
	 * @post  If the given name is valid the name of 
	 *        the mobile is set to the given name.
	 *        | this.name = name
	 * @throws IllegalArgumentException
	 * 		   The given name is not valid
	 * 		   | ! isValidName(name)		   
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException{
		if (!isValidName(name)){
			throw new IllegalArgumentException("Invalid Name");
		}
		else{
			this.name = name;
		}
	}
	
	/**
	 * A variable referencing the name of a mobile.
	 */
	private String name;
	
	/************************************************
	 * Hitpoints: nominal
	 ************************************************/
	
	/**
	 * Returns the current hitpoints of the Mobile.
	 */
	@Raw @Basic
	public long getCurrentHitpoints(){
		return this.currentHitpoints;
	}
	
	/**
	 * Returns the maximum hitpoints of the Mobile.
	 */
	@Raw @Basic
	public long getMaximumHitpoints(){
		return this.maximumHitpoints;
	}
	
	/**
	 * Checks whether a given hitpoints is valid as the current hitpoints.
	 * 
	 * @param current
	 * 	      The hitpoints to check whether they are valid as current hitpoints.
	 * @return true if and only if the given hitpoints lays between the maximum hitpoints
	 * 	       and 0, both included.
	 * 		  | if (current > getMaximumHitpoints() || current < 0)
	 * 		  | 	then return false
	 * 		  | else
	 * 		  | 	result == true
	 */
	public boolean canHaveAsCurrentHitpoints(long current){
		if (current > getMaximumHitpoints() || current < 0){
			return false;
		}
		return true;
	}
	
	/**
	 * Checks whether a given number can be set as the maximum hitpoints.
	 * 
	 * @param max
	 * 	      the number to set as the maximum hitpoints.
	 * @return true if and only if the given hitpoints is greater than zero and prime.
	 * 		  | if (max < 0 || !isPrime(max))
	 * 		  | 	then result == false;
	 * 		  | else
	 * 		  |		result == true
	 */
	public static boolean isValidMaximumHitpoint(long max){
		if (max < 0 || !isPrime(max)){
			return false;
		}
		return true;
	}
	
	/**
	 * Checks whether a given number is prime.
	 * 
	 * @param n
	 * 		  the number to check for primality.
	 * @return false if the given number is less than 2.
	 * 		   | if n < 2
	 * 		   | 	then result == false
	 * @return true if the given number is 2 or three
	 * 		   | if (n == 2 || n == 3)
	 * 		   | 	then result == true
	 * @return false if the given number is divisible by two or three.
	 * 		   | if (n%2 == 0 || n%3 == 0)
	 * 		   | 	then result == false
	 * @return false if the given number is divisible by a second number between 6 
	 * 		   and the root of the number, the second number is incremented six times
	 * 		   each loop, then the first number is checked against the increment and decrement of
	 * 		   the second number. True otherwise
	 * 		   | let sqrtN = sqrt(n)
	 * 		   |	for {i in 6..sqrtN, i+=6}
	 * 		   |		if n%(i-1) == 0 || n%(i+1) == 0
	 * 		   |			then return == false
	 * 		   | result == true
	 */
	public static boolean isPrime(long n) {
	    if(n < 2) return false;
	    if(n == 2 || n == 3) return true;
	    if(n%2 == 0 || n%3 == 0) return false;
	    long sqrtN = (long)Math.sqrt(n)+1;
	    for(long i = 6L; i <= sqrtN; i += 6) {
	        if(n%(i-1) == 0 || n%(i+1) == 0) return false;
	    }
	    return true;
	}
	
	/**
	 * Returns the closest prime to a given number.
	 * 
	 * @param n
	 * 		  The number of which the nearest prime will be sought. 
	 * @pre the given number must greater than 1 and less than 9223372036854775783
	 * 		(biggest prime in range of long values).
	 * 		| n > 1 && n < 9223372036854775783
	 * @return The nearest prime of a given number. If the two neighbor  
	 * 		   primes have the same distance then the highest one is chosen.
	 * 		   | let nextprime = n
	 * 		   | let previousprime = n
	 * 		   | while true
	 * 		   | 	nextPrime++
	 *         |	previousPrime--
	 *         |		if isPrime(nextPrime)
	 *         |			then return nextPrime
	 *         |		else if isPrime(previousPrime)
	 *         |			then return previousPrime
	 */
	public static long closestPrime(long n){
		long nextPrime = n;
		long previousPrime = n;
		while (true){
			nextPrime++;
			previousPrime--;
			if (isPrime(nextPrime)){
				return nextPrime;
			}
			else if (isPrime(previousPrime)){
				return previousPrime;
			}
		
		}
	}
	
	/**
	 * Sets the current number to a given amount.
	 * 
	 * @param hitpoints
	 * 		  The new currentHitpoints
	 * @pre the given amount must be a valid amount for the current hitpoints.
	 * 		| canHaveAsCurrentHitpoints(getCurrentHitpoints())
	 * @post the currenthitpoints is set to the given hitpoints
	 * 		 | (new).getCurrentHitpoints() == amount
	 */
	public void setCurrentHitpoints(long hitpoints){
		this.currentHitpoints = hitpoints;
	}
	
	/**
	 * Sets the maximum number to a given amount.
	 * 
	 * @param amount
	 * 		  The new maximumHitpoints
	 * @pre the given amount must be a valid amount for the maximum hitpoints.
	 * 		| isValidMaximumHitpoints(getMaximumHitpoints())
	 * @post the maximumthitpoints is set to the given hitpoints
	 * 		 | (new).getMaximumHitpoints() == amount
	 */
	public void setMaximumHitpoints(long amount){
		this.maximumHitpoints = amount;
	}
	
	/**
	 * Variable referencing the current hitpoints of the mobile.
	 */
	private long currentHitpoints;
	/**
	 * Variable referencing the maximum hitpoints of the mobile
	 */
	private long maximumHitpoints;
	
	/************************************************
	 * Strength and Damage: total
	 ************************************************/
	
	/**
	 * Return the raw strength of a mobile.
	 */
	@Raw @Basic
	public double getRawStrength(){
		return this.rawStrength;
	}
	
	/**
	 * Return the precision of the strength.
	 */
	@Raw @Basic @Immutable
	private static int getpPrecisionOfStrength() {
		return precisionOfStrength;
	}
	
	/**
	 * Multiplies the raw strength with a given integer.
	 * 
	 * @param integer
	 * 		  the integer that the raw strength will be multiplied with.
	 * @effect The raw strength is multiplied with an integer.
	 * 		   | setRawStrength(getRawStrength()*integer)
	 * @post If the new raw strength is more than or equal to Double.MAX_VALUE or 
	 * 		 less than or equal to Double.MIN_VALUE then the raw strength stays untouched
	 * 		 | if ((getRawStrength()*integer) >= Double.MAX_VALUE ||
	 * 		 |		(getRawStrength()*integer) <= Double.MIN_VALUE)
	 * 		 |			then (new).getRawStrength() == this.getRawStrength()
	 */
	public void multiplyRawStrength(int integer){
		if ((getRawStrength()*integer) >= Double.MAX_VALUE ||
			(getRawStrength()*integer) <= -Double.MAX_VALUE){}
		else {
			setRawStrength(getRawStrength()*integer);
		}
	}
	
	/**
	 * Returns the total damage of the mobile
	 * 
	 * @return The total damage of the Mobile.
	 * @return The totalDamage is greater or equal to zero.
	 * 		   | getTotalDamage() >= 0
	 * @return The total damage of the Mobile is the raw Strength plus a number,
	 * 		   the number is calculated in the subclasses.
	 * 		   | getTotalDamage()+number >= getRawStrength()  
	 * @note the implementation is given at the level of the subclasses.
	 */
	@Raw 
	public abstract double getTotalDamage();
	
	
	/**
	 * Rounds a double up to a given number of decimals, can handle numbers up to
	 * 		  Double.MAX_VALUE
	 * 
	 * @param value
	 * 		  The value that will be rounded.
	 * @param places
	 * 		  The total number of decimals that will remain.
	 * @return value if the given places is less than zero.
	 * 		  | if (places<0)
	 * 		  |		then result == value
	 * @return a double that is rounded to the given decimal places
	 * 		  | let bd = BigDecimal(value)
	 * 		  |	bd = bd.setScale(places,roundingMode.HALF_DOWN)
	 * 		  | result == bd.doubleValue()
	 */
	public static double round(double value, int places){
		if (places < 0){ return value;}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places,RoundingMode.HALF_UP);
		return bd.doubleValue();	
	}
	
	/**
	 * Sets the raw strength to the given amount rounded 
	 * to the precision of the strength.
	 * 
	 * @param amount
	 * 	      The new raw strength of the mobile.
	 * @effect The given value is rounded down to the strength precision.
	 * 		  | newAmount = round(amount,getpPrecisionOfStrength())
	 * @post The raw strength is set to the given amount rounded down to the strength precision.
	 * 		 | (new).getRawStrength() == newAmount
	 */
	@Model
	private void setRawStrength(double amount){
		this.rawStrength = round(amount,getpPrecisionOfStrength());
		setCapacity(calculateCapacity(getRawStrength(),Unit.kg));
	}
	
	/**
	 * A variable referencing the raw strength of a Mobile.
	 */
	private double rawStrength;
	
	/**
	 * A variable referencing the precision of the raw strength.
	 */
	private static final int precisionOfStrength = 2;
	
	
	/************************************************
	 * Capacity
	 ************************************************/
	
	
	/**
	 * Return the capacity of this mobile expressed in a given unit.
	 * 
	 * @return the capacity expresssed in a given unit.
	 * 		   | getCapacity().equals(this.capacity.toUnit(unit))
	 * @throws IllegalArgumentException
	 * 	       The unit is not effective.
	 * 		   | unit == null
	 */
	@Raw @Basic
	public Weight getCapacity(Unit unit) throws IllegalArgumentException{
		if (unit==null){
			throw new IllegalArgumentException("not effective unit.");
		}
		return this.capacity.toUnit(unit);
	}
	
	/**
	 * Calculates the capacity based on the given strength strength.
	 * 
	 * @pre  the unit must be effective
	 * 		 | unit != null
	 * @param unit
	 * 		  The capacity is expressed in this unit.
	 * @param strength
	 * 		  the strength the capacity is based on.
	 * @return the capacity of the current Mobile in a given unit
	 * @throws IllegalArgumentException
	 * 		   Unit not effective
	 * 		   | unit == null
	 */
	public abstract Weight calculateCapacity(double strength,Unit unit) throws IllegalArgumentException;
	
	/**
	 * Sets the capacity of the mobile to the given Weight.
	 * @param weight
	 * 		  The new weight of the Mobile.
	 * @post the capacity is set to the given weight.
	 * 		 | new.getCapacity(unit).equals(weight.toUnit(unit))
	 */
	private void setCapacity(Weight weight){
		this.capacity = weight;
	}
	
	/**
	 * A variable expressing the capacity of a Mobile.
	 */
	private Weight capacity;
	
	/************************************************
	 * Protection: total
	 ************************************************/
	
	/**
	 * Return the protection factor inherent to all mobiles.
	 */
	public static int getProtection(){
		return protection;
	}
	
	/**
	 * Check whether a given protection is valid as the protection
	 * 
	 * @param protection
	 * 		  the protection to check.
	 * @return True if protection is strictly positive/
	 * 	       | result == protection>0
	 */
	public static boolean isValidProtection(int protection){
		return protection>0;
	}
	
	/**
	 * A variable referencing the protection inherent to mobiles.
	 */
	private static final int protection = 10;
	
	/************************************************
	 * Anchors
	 ************************************************/
	
	
}