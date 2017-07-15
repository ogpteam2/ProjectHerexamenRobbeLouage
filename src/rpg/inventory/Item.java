package rpg.inventory;
import be.kuleuven.cs.som.annotate.*;
import rpg.Mobile;
import rpg.utility.IDGenerator;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * A class of items with given ID, weight, value and holder.
 * 
 * @invar Each item must have a valid weight.
 * 		  | isValidWeight(getWeight())
 * @invar Each item must have a valid value.
 * 		  | canHaveAsValue(getValue())
 * @invar Each item must have a valid Holder
 * 		  | canHaveAsHolder(getHolder())
 * @author Robbe
 * @version 1.0
 */
abstract public class Item {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes an item with given weight and value.
	 * 
	 * @param weight
	 * 		  The weight of the item.
	 * @param value
	 * 		  The value of the item.
	 * @pre The given value must be valid.
	 * 		| canHaveAsValue(value)
	 * @effect the ID is generated and set.
	 * 		   | this.ID = generateID()
	 * @post if the weight is valid then the weight of the item
	 * 		 is set to the given weight, if the given weight is not valid
	 * 		 the weight of the item is set to 1kg.
	 * 		 | if (isValidWeight(weight))
	 * 		 |	then this.weight = weight
	 * 		 | else this.weight  = new weight(1,Unit.kg)
	 * @effect The value is set to the given value.
	 * 		  | setValue(Value)
	 * @effect The holder is set to the given holder.
	 * 		  | setValue(holder)
	 */
	@Raw
	protected Item(Weight weight,int value, Mobile holder){
		this.ID = generateID();
		if (isValidWeight(weight)){
			this.weight = weight;
		}
		else{
			this.weight = new Weight(1,Unit.kg);
		}
		setValue(value);
		setHolder(holder);
		
	}
	
	/**
	 * Initializes an item with given weight and value.
	 * 
	 * @param weight
	 * 		  the weight of the item.
	 * @param value	
	 * 		  the value of the item.
	 * @effect The item is initialized with the given weight and value,
	 * 		   the holder is set to null.
	 * 		   | this(weight,value,null)
	 */
	protected Item(Weight weight, int value){
		this(weight,value,null);
	}
	
	/************************************************
	 * ID
	 ************************************************/
	
	/**
	 * Return the ID of this item.
	 */
	@Raw @Basic 
	public long getId(){
		return this.ID;
	}
	
	/**
	 * Returns the static IDGenerator for a specific item.
	 * 
	 * @note The static IDGenerator is implemented in each subclass.
	 */
	@Raw
	protected abstract IDGenerator getIDGenerator();
	
	/**
	 * Generates an ID in accordance with the item type's ID specification.
	 * 
	 * @return an ID based on the ID generator inherent to each item type.
	 * 		   | result == getIDGenerator().generateID()
	 */
	private long generateID(){
		return getIDGenerator().generateID();
	}
	
	/**
	 * A variable referencing the ID of the item.
	 */
	private final long ID;
	
	/************************************************
	 * Weight
	 ************************************************/
	
	/**
	 * Return the weight of an item in a given unit.
	 * 
	 * @param unit
	 * 		  The unit to convert the weight in.
	 * @return the weight of this item in a given unit.
	 * 		   | result.equals(this.weight.toUnit(unit))
	 * @return the weight in the unit of the item if unit is not effective.
	 * 		   | if (unit == null)
	 * 		   | result.equals(this.weight)
	 */
	@Raw @Basic
	public Weight getWeight(Unit unit){
		return this.weight.toUnit(unit);
	}
	
	/**
	 * Return the weight of an item.
	 */
	@Raw @Basic
	public Weight getWeight(){
		return this.weight;
	}
	
	/**
	 * Checks whether a weight is valid for an item.
	 * 
	 * @param weight
	 * 		  The weight to check.
	 * @return false if the weight is not effective.
	 * 		   | if (weight == false)
	 * 		   | 	then result == false
	 * @return true if the numeral of the weight is greater than zero.
	 * 		   | result == (weight.getNumeral()>0)
	 */
	public static boolean isValidWeight(Weight weight){
		if (weight==null){
			return false;
		}
		return weight.getNumeral()>0;
	}
	

	
	/**
	 * A variable referencing the weight of the item.
	 */
	private final Weight weight;
	
	/************************************************
	 * Value: nominal
	 ************************************************/
	
	/**
	 * Returns the value of this item. 
	 */
	@Raw @Basic
	public int getValue(){
		return this.value;
	}
	
	/**
	 * Checks whether a amount is a valid valueamount.
	 * 
	 * @param amount
	 * 		  The amount to check.
	 * @return false if the amount is less than zero, true otherwise.
	 * 		   | if (amount<0)
	 *		   |	then result == false
	 *		   | result == true
	 */
	public boolean canHaveAsValue(int amount){
		if (amount<0)
			return false;
		return true;
	}
	
	/**
	 * Sets the value of this item to the given amount.
	 * 
	 * @param amount
	 * 		  the new amount of this item.
	 * @pre The given amount must be a valid value.
	 * 		| canHaveAsValue(amount)
	 * @effect the new value of this item is the given amount.
	 * 		   | this.value = amount
	 */
	protected void setValue(int amount){
		this.value = amount;
	}
	
	/**
	 * A variable referencing the value of the item expressed in Ducats.
	 */
	private int value;
	
	/************************************************
	 * Holder: total
	 ************************************************/
	
	/**
	 * Returns the holder of this item.
	 */
	@Raw @Basic
	public Mobile getHolder(){
		return this.holder;
	}
		
	/**
	 * Checks whether the bidirectional relation is consistent.
	 * 
	 * @return true if the holder is not effective
	 * 		  | if (getHolder()==null)
	 * 		  |		then result == true
	 * @return true if the holder is effective and the holder references to this item.
	 * 		   | if (getHolder() != null)
	 * 		   |	then for types in AnchorpointType.values()
	 * 		   |		if (getHolder().getItemAt(type) != null)
	 * 		   |			then if (getHolder().getItemAt(type) == this)
	 * 		   |					result == true
	 * 		   |	result == false
	 */
	public boolean hasProperHolder(){
		if (getHolder() != null){
			for (AnchorpointType type:AnchorpointType.values()){
				if (getHolder().getItemAt(type) != null){
					if (getHolder().getItemAt(type) == this){
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Checks whether this item can have the given holder as holder.
	 * 
	 * @param holder
	 * 		  The holder to check.
	 * @return false if the holder is effective and the weight of this item plus
	 * 		   the holder's current weight is greater than his capacity, true otherwise.
	 * 		   | if (holder != null)
	 * 		   |	then let capacity = holder.getCapacity()
	 * 		   |		if ((getWeight(Unit.kg).add(holder.getTotalWeight(Unit.kg))).compareTo(capacity)>=1)
	 * 		   |			then result == false
	 * 		   | result == true
	 */
	public boolean canHaveAsHolder(Mobile holder){
		if (holder != null){
			Weight capacity = holder.getCapacity();
			if ((getWeight(Unit.kg).add(holder.getTotalWeight(Unit.kg))).compareTo(capacity)>=1){
				return false;
			}
		}
		return true;
	}
	

	/**
	 * Sets the holder og this item to the given holder.
	 * 
	 * @param holder
	 * 		  The holder to set as new holder of this item.
	 * @post if the holder is a valid holder then the holder is set to the given holder.
	 * 		 | if (canHaveAsHolder(holder))
	 * 		 |		then new.getHolder() = holder
	 */
	public void setHolder(Mobile holder){
		if (canHaveAsHolder(holder))
			this.holder = holder;
	}
	
	/**
	 * A variable referencing the holder of this item.
	 */
	private Mobile holder;
	
}
