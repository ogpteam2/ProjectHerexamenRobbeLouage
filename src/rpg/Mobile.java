package rpg;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Item;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * An abstract class of movable objects in the rpg.
 * 
 * @invar Each mobile must have a valid name. 
 *        | isValidName(getName)
 * @invar Each mobile must have a valid maximumHitpoints. 
 *        | isValidMaximumHitpoints(getMaximumHitpoints())      
 * @invar Each mobile must have a valid currentHitpoints. 
 *        | canHaveAsValid(getCurrentHitpoints())   
 * @invar Each Mobile must have a valid protection. 
 *        | isValidProtection(getProtection())
 * @invar The number of items must be valid for each mobile. 
 *        | canHaveAsNbItems(getNbItems)  
 * @invar Each mobile can have each of its items at its anchorpoints. 
 * 		  | for each I in anchorpoints:
 *        | 	canHaveItemAt(getItemAt(I))
 * @invar Each mobile should have valid anchorpointsList 
 *        | isValidAnchorpointList(anchors)
 * @author Robbe
 *
 */
public abstract class Mobile {

	/************************************************
	 * Constructors
	 ************************************************/

	/**
	 * Initialize a new Mobile with given name, hitpoints and strength and anchor point list.
	 * 
	 * @param name
	 *            The name of the mobile.
	 * @param hitpoints
	 *            The hitpoints of the Mobile
	 * @param strength
	 *            The raw strength of the mobile.
	 * @param anchors
	 * 		  The anchors for the mobile.
	 * @effect The name of the mobile is set to the given name. 
	 * 		  | setName(name)
	 * @effect Sets the currentHitpoints and maximum hitpoints to the given hitpoints.
	 * 		   | setCurrentHitpoints(hitpoints)
	 * 	       | setMaximumHitpoints(hitpoints)
	 * @effect Sets the strength to the given strength
	 * 		   | setRawStrength(strength)
	 * @effect the anchors is set to the given anchors if the given anchors is valid, and all
	 * 		 the items will have as holder this mobile.
	 * 	     | if (this.isValidAnchorpointList(anchors))
	 * 		 |		then new.anchors.equals(anchors)
	 * 		 | 		for (anchor in anchors)
	 * 		 |			if (anchor.getItem()!=null)
	 * 		 |				then anchor.getItem().setHolder(this)
	 */
	@Raw
	protected Mobile(String name, long hitpoints, double strength
			,Anchorpoint[] anchors) throws IllegalArgumentException {
		setName(name);
		setCurrentHitpoints(hitpoints);
		setMaximumHitpoints(hitpoints);
		setRawStrength(strength);
		if (this.isValidAnchorpointList(anchors)){
			this.anchors = generateAnchorpoints();
			for (Anchorpoint anchor:anchors){
				if (anchor.getItem()!=null){
					anchor.getItem().setHolder(this);
				}
			}
			this.anchors = anchors;
		}
		else{
			this.anchors = generateAnchorpoints();
		}
	}
	
	/**
	 * Initializes a mobile with no items.
	 * 
	 * @param name
	 *            The name of the mobile.
	 * @param hitpoints
	 *            The hitpoints of the Mobile
	 * @param strength
	 *            The raw strength of the mobile.
	 * @effect the mobile is initialized with given name, hitpoints and strength.
	 * 		   | this(name,hitpoints,strength,null)
	 */
	@Raw
	public Mobile(String name,long hitpoints,double strength){
		this(name,hitpoints,strength,null);
	}

	/************************************************
	 * Name: defensive
	 ************************************************/

	/**
	 * Returns the name of a Mobile.
	 */
	@Raw
	@Basic
	public String getName() {
		return this.name;
	}

	/**
	 * Checks whether a name is valid or not.
	 * 
	 * @param name
	 *            The name to check.
	 * @return false if the name is null, true otherwise 
	 * 		   | if name == null 
	 *         |	result == false
	 *         | else 
	 *         | 	result == true
	 */
	public boolean isValidName(String name) {
		if (name == null)
			return false;
		return true;
	}

	/**
	 * Sets the name to the given name.
	 * 
	 * @param name
	 *            The new name of the mobile.
	 * @post If the given name is valid the name of the mobile is set to the
	 *       given name. 
	 *       | this.name = name
	 * @throws IllegalArgumentException
	 *        The given name is not valid 
	 *        | ! isValidName(name)
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException {
		if (!isValidName(name)) {
			throw new IllegalArgumentException("Invalid Name");
		} else {
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
	@Raw
	@Basic
	public long getCurrentHitpoints() {
		return this.currentHitpoints;
	}

	/**
	 * Returns the maximum hitpoints of the Mobile.
	 */
	@Raw
	@Basic
	public long getMaximumHitpoints() {
		return this.maximumHitpoints;
	}

	/**
	 * Checks whether a given hitpoints is valid as the current hitpoints.
	 * 
	 * @param current
	 *        The hitpoints to check whether they are valid as current
	 *        hitpoints.
	 * @return true if and only if the given hitpoints lays between the maximum
	 *         hitpoints and 0, both included. 
	 *         | if (current > getMaximumHitpoints() || current < 0)  
	 *         | 	then return false 
	 *         | else
	 *         | 	result == true
	 */
	public boolean canHaveAsCurrentHitpoints(long current) {
		if (current > getMaximumHitpoints() || current < 0) {
			return false;
		}
		return true;
	}

	/**
	 * Checks whether a given number can be set as the maximum hitpoints.
	 * 
	 * @param max
	 *        the number to set as the maximum hitpoints.
	 * @return true if and only if the given hitpoints is greater than zero and
	 *         prime. 
	 *         | if (max < 0 || !isPrime(max)) 
	 *         | 	then result == false; 
	 *         | else 
	 *         | 	result == true
	 */
	public static boolean isValidMaximumHitpoint(long max) {
		if (max < 0 || !isPrime(max)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks whether a given number is prime.
	 * 
	 * @param n
	 *        the number to check for primality.
	 * @return false if the given number is less than 2. 
	 * 		   | if n < 2 
	 * 		   | 	then result == false      
	 * @return true if the given number is 2 or three 
	 * 		   | if (n == 2 || n == 3) 
	 * 		   |	then result == true      
	 * @return false if the given number is divisible by two or three. 
	 * 		   | if (n%2== 0 || n%3 == 0) 
	 *         | 	then result == false
	 * @return false if the given number is divisible by a second number between
	 *         6 and the root of the number, the second number is incremented
	 *         six times each loop, then the first number is checked against the
	 *         increment and decrement of the second number. True otherwise 
	 *         | let sqrtN = sqrt(n) 
	 *         | for {i in 6..sqrtN, i+=6} 
	 *         | 	if (n%(i-1) == 0 || n%(i+1) == 0 )
	 *         | 	then return == false 
	 *         | result == true
	 */
	public static boolean isPrime(long n) {
		if (n < 2)
			return false;
		if (n == 2 || n == 3)
			return true;
		if (n % 2 == 0 || n % 3 == 0)
			return false;
		long sqrtN = (long) Math.sqrt(n) + 1;
		for (long i = 6L; i <= sqrtN; i += 6) {
			if (n % (i - 1) == 0 || n % (i + 1) == 0)
				return false;
		}
		return true;
	}

	/**
	 * Returns the closest prime to a given number.
	 * 
	 * @param n
	 *       The number of which the nearest prime will be sought.
	 * @pre the given number must greater than 1 and less than
	 *      9223372036854775783 (biggest prime in range of long values). 
	 *      | n > 1 && n < 9223372036854775783  
	 * @return The nearest prime of a given number. If the two neighbor primes
	 *         have the same distance then the highest one is chosen. 
	 *         | let nextprime = n
	 *         | let previousprime = n 
	 *         | while true 
	 *         | nextPrime++
	 *         | previousPrime-- 
	 *         | if isPrime(nextPrime) 
	 *         | 	then return nextPrime
	 *         | else if isPrime(previousPrime) 
	 *         | 	then return previousPrime
	 */
	public static long closestPrime(long n) {
		long nextPrime = n;
		long previousPrime = n;
		while (true) {
			nextPrime++;
			previousPrime--;
			if (isPrime(nextPrime)) {
				return nextPrime;
			} else if (isPrime(previousPrime)) {
				return previousPrime;
			}

		}
	}

	/**
	 * Sets the current number to a given amount.
	 * 
	 * @param hitpoints
	 *            The new currentHitpoints
	 * @pre the given amount must be a valid amount for the current hitpoints. 
	 * 		| canHaveAsCurrentHitpoints(getCurrentHitpoints())
	 * @post the currenthitpoints is set to the given hitpoints 
	 * 		| (new).getCurrentHitpoints() == amount
	 *       
	 */
	public void setCurrentHitpoints(long hitpoints) {
		this.currentHitpoints = hitpoints;
	}

	/**
	 * Sets the maximum number to a given amount.
	 * 
	 * @param amount
	 *            The new maximumHitpoints
	 * @pre the given amount must be a valid amount for the maximum hitpoints. 
	 * 		|isValidMaximumHitpoints(getMaximumHitpoints())
	 *      
	 * @post the maximumthitpoints is set to the given hitpoints 
	 *       | (new).getMaximumHitpoints() == amount
	 */
	public void setMaximumHitpoints(long amount) {
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
	@Raw
	@Basic
	public double getRawStrength() {
		return this.rawStrength;
	}

	/**
	 * Return the precision of the strength.
	 */
	@Raw
	@Basic
	@Immutable
	private static int getpPrecisionOfStrength() {
		return precisionOfStrength;
	}

	/**
	 * Multiplies the raw strength with a given integer.
	 * 
	 * @param integer
	 *            the integer that the raw strength will be multiplied with.
	 * @effect The raw strength is multiplied with an integer. 
	 *         |setRawStrength(getRawStrength()*integer)
	 * @post If the new raw strength is more than or equal to Double.MAX_VALUE
	 *       or less than or equal to Double.MIN_VALUE then the raw strength
	 *       stays untouched 
	 *       | if ((getRawStrength()*integer) >=Double.MAX_VALUE || 
	 *       | (getRawStrength()*integer) <= -Double.MAX_VALUE)
	 *       |  	 then (new).getRawStrength() == this.getRawStrength()
	 *       
	 */
	public void multiplyRawStrength(int integer) {
		if ((getRawStrength() * integer) >= Double.MAX_VALUE || (getRawStrength() * integer) <= -Double.MAX_VALUE) {
		} else {
			setRawStrength(getRawStrength() * integer);
		}
	}

	/**
	 * Returns the total damage of the mobile
	 * 
	 * @return The total damage of the Mobile.
	 * @return The totalDamage is greater or equal to zero. 
	 * 		   | getTotalDamage()>= 0        
	 * @return The total damage of the Mobile is the raw Strength plus a number,
	 *         the number is calculated in the subclasses. 
	 *         | getTotalDamage()+number >= getRawStrength()
	 * @note the implementation is given at the level of the subclasses.
	 */
	@Raw
	public abstract double getTotalDamage();

	/**
	 * Rounds a double up to a given number of decimals, can handle numbers up
	 * to Double.MAX_VALUE
	 * 
	 * @param value
	 *            The value that will be rounded.
	 * @param places
	 *            The total number of decimals that will remain.
	 * @return value if the given places is less than zero. 
	 * 		   | if (places<0) 
	 *         |	then result == value
	 * @return a double that is rounded to the given decimal places 
	 * 		   | let bd = BigDecimal(value)
	 *         | bd = bd.setScale(places,roundingMode.HALF_UP)
	 *         | result == bd.doubleValue()        
	 */
	public static double round(double value, int places) {
		if (places < 0) {
			return value;
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * Sets the raw strength to the given amount rounded to the precision of the
	 * strength.
	 * 
	 * @param amount
	 *            The new raw strength of the mobile.
	 * @effect The given value is rounded down to the strength precision. 
	 *         | newAmount = round(amount,getpPrecisionOfStrength())
	 * @post The raw strength is set to the given amount rounded down to the
	 *       strength precision. 
	 *       | (new).getRawStrength() == Amount
	 */
	@Model
	private void setRawStrength(double amount) {
		this.rawStrength = round(amount, getpPrecisionOfStrength());
		setCapacity(calculateCapacity(getRawStrength(), Unit.kg));
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
	 *         | getCapacity().equals(this.capacity.toUnit(unit))
	 * @throws IllegalArgumentException
	 *             The unit is not effective. 
	 *         | unit == null
	 */
	@Raw
	@Basic
	public Weight getCapacity(Unit unit) throws IllegalArgumentException {
		if (unit == null) {
			throw new IllegalArgumentException("not effective unit.");
		}
		return this.capacity.toUnit(unit);
	}

	/**
	 * Return the capacity of this mobile.
	 */
	@Raw
	@Basic
	public Weight getCapacity() {
		return this.capacity;
	}

	/**
	 * Calculates the capacity based on the given strength strength.
	 * 
	 * @param unit
	 *            The capacity is expressed in this unit.
	 * @param strength
	 *            the strength the capacity is based on.
	 * @pre the unit must be effective 
	 * 		| unit != null
	 * @return the capacity of the current Mobile in a given unit
	 * @throws IllegalArgumentException
	 *             Unit not effective 
	 *         | unit == null
	 */
	public abstract Weight calculateCapacity(double strength, Unit unit) throws IllegalArgumentException;

	/**
	 * Sets the capacity of the mobile to the given Weight.
	 * 
	 * @param weight
	 *            The new weight of the Mobile.
	 * @post the capacity is set to the given weight. 
	 *       | new.getCapacity(unit).equals(weight.toUnit(unit))
	 */
	private void setCapacity(Weight weight) {
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
	public static int getProtection() {
		return protection;
	}

	/**
	 * Check whether a given protection is valid as the protection
	 * 
	 * @param protection
	 *            the protection to check.
	 * @return True if protection is strictly positive.
	 * 		   | result == protection>0
	 */
	public static boolean isValidProtection(int protection) {
		return protection > 0;
	}

	/**
	 * A variable referencing the protection inherent to mobiles.
	 */
	private static final int protection = 10;

	/************************************************
	 * Anchors
	 ************************************************/

	/**
	 * Returns the number of items in the mobile has.
	 * 
	 * @return the number of items the mobile has. 
	 * 		   | let sum = 0 
	 * 		   | for anchor in anchors 
	 *         | if (anchor.getAnchorpointType() != null) 
	 *         | 	 if (anchor.getItem() != null)
	 *         | 		then sum++ 
	 *         | result == sum
	 */
	public int getNbItems() {
		int sum = 0;
		for (Anchorpoint anchor : anchors) {
			if (anchor.getAnchorpointType() != null) {
				if (anchor.getItem() != null) {
					sum++;
				}  
			}
		}
		return sum;
	}
	
	/**
	 * Checks whether a number is valid as the maximum items a mobile can have.
	 * 
	 * @param number
	 * 		  The number to check.
	 * @return true if the number is less or equal to number of different anchorpointtypes,
	 * 		   and greater than zero.
	 * 		   | result == number>=0 &&number<=AnchorpointType.NbOfAnchorpointTypes() 
	 */
	public boolean canHaveAsNbItems(int number){
		return number>=0 &&number<=AnchorpointType.NbOfAnchorpointTypes();
	}
	
	/**
	 * Gets an item at a given anchor point type.
	 * 
	 * @param type
	 *            the anchor point type.
	 * @return null if the given anchor point type is null. 
	 * 		   | if (type==null) 
	 *         | 	then result == false
	 * @return null if the anchor point type is null in the anchors.
	 * 		  | if (anchors[type.ordinal()]..getAnchorpointType()==null)
	 *        | 	result == null
	 * @return null if there is no item at the given anchor point type 
	 * 		  | if (anchors[type.ordinal()].getItem()==null)
	 *        | 	result == null
	 * @return The item at the given anchor point type. 
	 *         | result.equals(anchors[type.ordinal()].getItem())
	 * 
	 */
	public Item getItemAt(AnchorpointType type) {
		if (type == null)
			return null;
		else if (anchors[type.ordinal()].getAnchorpointType() == null)
			return null;
		else if (anchors[type.ordinal()].getItem() == null)
			return null;
		else {
			return anchors[type.ordinal()].getItem();
		}
	}

	/**
	 * Return the total weight of his items at his anchorpoints.
	 * 
	 * @param unit
	 *            the unit of the total weight.
	 * @return null if the given unit is null. 
	 * 		   | if (unit==null) 
	 * 		   | 	then result == null   
	 * @return The total weight of al the items the mobile holds.
	 * 		   | let weight = Weight.kg_0
	 * 	       | for anchors in anchor
	 * 	       |	if (anchor.getAnchorpointType() != null && anchor.getItem() != null)
	 * 		   |		then weight = weight.add(anchor.getItem().getWeight(Unit.kg))
	 * 		   | result.equals(weight)
	 */
	public Weight getTotalWeight(Unit unit) {
		if (unit==null){
			return null;
		}
		
		Weight weight = Weight.kg_0;
		for (Anchorpoint anchor:anchors){
			 if (anchor.getAnchorpointType() != null && anchor.getItem() != null){
				 weight = weight.add(anchor.getItem().getWeight(Unit.kg));	 
			 }
		}
		return weight.toUnit(unit);
	}
	
	/**
	 * Gets the total weight of a anchors.
	 * 
	 * @param anchors
	 * 		  The anchors to get the total weight of. 
	 * @return The total weight of al the items the mobile holds, in kg.
	 * 		   | let weight = Weight.kg_0
	 * 	       | for anchor in anchors
	 * 	       |	if (anchor.getAnchorpointType() != null && anchor.getItem() != null)
	 * 		   |		then weight = weight.add(anchor.getItem().getWeight(Unit.kg))
	 * 		   | result.equals(weight)
	 */
	public static Weight totalWeight(Anchorpoint[] anchors){
		Weight weight = Weight.kg_0;
		for (Anchorpoint anchor:anchors){
			 if (anchor.getAnchorpointType() != null && anchor.getItem() != null){
				 weight = weight.add(anchor.getItem().getWeight(Unit.kg));
			 }
		}
		return weight;
	}
	
	/**
	 * Returns a list with fee anchor points types.
	 * 
	 * @return the free anchor point types of this mobile.
	 * 		   | let free = new ArrayList<AnchorpointType>()
	 *		   | for anchor in anchors
	 *		   | 	if (anchor.getAnchorpointType() != null && anchor.getItem() == null)
	 *		   |		then free.add(anchor.getAnchorpointType())
	 */
	public ArrayList<AnchorpointType> getFreeAnchorpoints(){
		ArrayList<AnchorpointType> free = new ArrayList<AnchorpointType>();
		for (Anchorpoint anchor:anchors){
			if (anchor.getAnchorpointType() != null && anchor.getItem() == null){
				free.add(anchor.getAnchorpointType());
			}
		}
		return free;
	}
	
	/**
	 * Checks whether an item is valid at a given anchor point type.
	 * 
	 * @param type
	 * 		  The type to check.
	 * @param item
	 * 		  The item to check.
	 * @return false if the type is not effective.
	 * 		  | if (type == null)
	 * 		  |		then result == false
	 * @return false if the item is not null but the item weighs more than the capacity.
	 * 		  | if (item != null && (item.getWeight(Unit.kg)).compareTo(getCapacity(Unit.kg))>0)
	 * 		  | 	then result == false
	 * @return true otherwise.
	 */
	public boolean canHaveAsItemAt(AnchorpointType type, Item item){
		if (type == null)
			return false;
		else if (item != null  
				&& (item.getWeight(Unit.kg)).compareTo(getCapacity(Unit.kg))>0){
			return false;
		}
		return true;
	}
	

	/**
	 * Checks whether the link with items is consistent for all items.
	 * 
	 * @return false if one of the items can't be an item at a given position or 
	 * 		   if one of the items doesn't reference back to this mobile.
	 * 		   | for anchor in anchors
	 * 		   |	if (!canHaveAsItemAt(anchor.getAnchorpointType(),anchor.getItem()))
	 * 		   |		then result == false
	 * 		   |	if (anchor.getItem().getHolder() != this)
	 * 		   |		then result == false
	 * @return true otherwise.
	 */
	public boolean hasProperItems() {
		for (Anchorpoint anchor:anchors){
			if (!canHaveAsItemAt(anchor.getAnchorpointType(),anchor.getItem()))
				return false;
			if (anchor.getItem().getHolder() != this)
				return false;
		}
		return true;
	}

	/**
	 * checks whether a anchorpoint list is valid.
	 * 
	 * @param anchors
	 * 		  The list to check.
	 * @return false if the anchors is not effective.
	 * 		   | if (anchors == null)
	 * 	 	   |	then result == false
	 * @return false if the given anchors its length is different than the number
	 * 		   of different anchor point types.
	 * 		   | if (anchors.length!=AnchorpointType.NbOfAnchorpointTypes())
	 * 	       |	return false
	 * @return false if different is false.
	 * 		   | if (!different(anchors))
	 * 		   |	then result == false
	 * @return  false if the total weight of the given anchors is more than the capacity.
	 * 		   | if (totalWeight(anchors).compareTo(getCapacity(Unit.kg))>0)
	 * 		   | 	then result == false
	 */
	public boolean isValidAnchorpointList(Anchorpoint[] anchors) {
		if (anchors == null){
			return false;
		}
		if (anchors.length!=AnchorpointType.NbOfAnchorpointTypes()){
			return false;
		}
		if (!different(anchors))
			return false;
		if (totalWeight(anchors).compareTo(getCapacity(Unit.kg))>0){
			return false;
		}
		return true;
	}
	/**
	 * Checks whether each anchor point type occurs at most once in the given anchors.
	 * 
	 * @param anchors
	 * 		  The anchors to check.
	 * @return false if the anchors is not effective
	 * 		   | if (anchors == null)
	 * 		   |	then result == false
	 * @return false if the anchors' length is greater than the amount of different types.
	 * 		   | if (anchors.length>AnchorpointType.NbOfAnchorpointTypes())
	 * 		   |	then return false
	 * @return false if a type occurs more than once.
	 * 		   | let diff = new int[AnchorpointType.NbOfAnchorpointTypes()]
	 * 		   | for (anchor in anchors)
	 * 		   |	if (anchor.getAnchorpointType() != null)
	 * 		   |		then diff[anchor.getAnchorpointType().ordinal()] += 1
	 * 		   | for (int i:diff)
	 * 		   |	if (i>1)
	 * 		   |		then result == false
	 */
	@Model
	public boolean different(Anchorpoint[] anchors){
		if (anchors == null){
			return false;
		}
		if (anchors.length>AnchorpointType.NbOfAnchorpointTypes()){
			return false;
		}
		int[] diff = new int[AnchorpointType.NbOfAnchorpointTypes()];
		for (Anchorpoint anchor:anchors){
			if (anchor.getAnchorpointType() != null){
				diff[anchor.getAnchorpointType().ordinal()] += 1;
			}
		}
		for (int i:diff){
			if (i>1){
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether an item can be set at a given anchor point type.
	 * 
	 * @param type
	 * 	      The anchor point type to set the given item at.
	 * @param item
	 * 		  the item to set at the given anchor point type.
	 * @return false if the given anchor point type or item is null.
	 * 	       | if (type == null || item == null)
	 * 		   |	then result == false.
	 * @return false if the given anchorpoint is not a legal one for this mobile.
	 * 		   | (anchors[type.ordinal()] == null)
	 *         |	then result == false
	 * @return  false if there is already an item at the given anchor point type.
	 * 	       | if (anchors[type.ordinal].getItem() != null)
	 * 		   | 	then result == false
	 * @return false if the weight of the item plus the current weight would exceed the capacity.
	 * 		   | let newWeight = getTotalWeight(Unit.kg).add(item.getWeight(Unit.kg))
	 * 		   | if ( (newWeight.compareTo(getCapacity(Unit.kg)) > 0) )
	 * 		   | 	then result == false
	 * @return false if the item already has a holder.
	 * 	       | if (item.getHolder() != null)
	 * 		   | 	then result == false
	 * @return false if item is already in anchors.
	 * 		   | if (checkItemInAnchors(item))
	 * 		   |	then result == false
	 * @return true otherwise.
	 */
	public boolean canAddItemAt(AnchorpointType type, Item item) {
		if (type == null){
			return false;
		}
		else if (anchors[type.ordinal()] == null)
			return false;
		else if (item == null){
			return false;
		}
		else if (anchors[type.ordinal()].getItem() != null){
			return false;
		}
		Weight newWeight = getTotalWeight(Unit.kg).add(item.getWeight(Unit.kg));
		if ( (newWeight.compareTo(getCapacity(Unit.kg)) > 0) ) {
			return false;
		}
		else if (item.getHolder() != null){
			return false;
		}
		else if (checkItemInAnchors(item)){
			return false;
		}
		return true;
	}
	
	/**
	 * Checks whether an item is already in the anchors.
	 * 
	 * @param item
	 * 		  The item to search.
	 * @return true if the item is in the anchors.
	 * 		  | for (anchor in anchors)
	 * 		  |		if (anchor.getAnchorpointType() != null && anchor.getItem()!=null)
	 * 		  |			then if (anchor.getItem().equals(item))
	 * 		  |				return true
	 * @return false if item is not effective.
	 * 		  | (item == null)
	 * 		  |	return false.
	 */
	public boolean checkItemInAnchors(Item item){
		if (item == null)
			return false;
		for (Anchorpoint anchor:anchors){
			if (anchor.getAnchorpointType() != null && anchor.getItem()!=null){
				if (anchor.getItem().equals(item)){
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * Adds an item at a given anchor point type.
	 * 
	 * @param type
	 * 		  The type to which the item is attached.
	 * @param item
	 * 		  The item to attach to the given type.
	 * @effect Sets the item at the given anchor point, if the item can be added,
	 * 		   also the holder of the item is set to this mobile.
	 * 		   | if (canAddItemAt(type,item))
	 * 		   |	then anchors[type.ordinal()].setItem(item)
	 */
	public void addItemAt(AnchorpointType type, Item item) {
		if (canAddItemAt(type,item)){
			item.setHolder(this);
			anchors[type.ordinal()].setItem(item);
			
		}
	}

	/**
	 * Removes the item at the given anchor point type.
	 * 
	 * @param type
	 * 		  The item at this type will be removed.
	 * @effect sets the item at the given type at null, and removes the reference the 
	 * 		   item makes to this mobile, if the type is effective.
	 * 		   | if type != null
	 * 		   | 	then anchors[type.ordinal()].getItem().setHolder(null)
	 * 		   | 		 anchors[type.ordinal()].setItem(null)
	 *
	 */
	public void removeItemAt(AnchorpointType type) {
		if (type != null){
			anchors[type.ordinal()].getItem().setHolder(null);
			anchors[type.ordinal()].setItem(null);
		}
	}
	
	/**
	 * Generates anchorpoints if no valid list is given in the constructor.
	 * 
	 * @return a valid anchorpointslist for the mobile.
	 * @note the implementation is given in each subclass.
	 */
	public Anchorpoint[] generateAnchorpoints(){
		return null;
	}
	
	public Anchorpoint[] getAnchors(){
		return anchors.clone();
	}
	
	/**
	 * A variable referencing the anchors of this mobile. Each mobile has a certain number
	 * of anchorpoints, these are stored in this variable.
	 */
	private Anchorpoint[] anchors = new Anchorpoint[AnchorpointType.NbOfAnchorpointTypes()];
	
	/************************************************
	 * Hit
	 ************************************************/
}