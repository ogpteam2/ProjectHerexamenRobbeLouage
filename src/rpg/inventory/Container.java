package rpg.inventory;
import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.*;
import rpg.Mobile;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * A class of containers, meaning items where one can store items in.
 * 
 * @invar Each container must have valid contents
 * 		  | canHaveAsContents(contents)
 * @invar Each container must have a valid capacity.
 * 		  | isValidCapacity(getCapacity())
 * 
 * @author Robbe
 * @version 1.0
 */
abstract public class Container extends Item {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a container with given parameters.
	 * 
	 * @param weight
	 * 		  The weight of the container.
	 * @param value
	 * 		  The value of the container.
	 * @param holder
	 * 		  The owner and holder of the container.
	 * @param capacity
	 * 		  The capacity of the container.
	 * @effect initializes the container as an item with given weight,value and holder.
	 * 		   | super(weight, value,holder)
	 * @post sets the capacity of the container to the given capacity.
	 * 		 | new.getCapacity().equals(capacity)
	 */
	protected Container(Weight weight, int value, Mobile holder,Weight capacity) {
		super(weight, value,holder);
		this.capacity = capacity;
	}
	
	/**
	 * Initializes a container with given parameters.
	 * 
	 * @param weight
	 * 		  The weight of the container.
	 * @param value
	 * 		  The value of the container.
	 * @param holder
	 * 		  The owner and holder of the container.
	 * @param capacity
	 * 		  The capacity of the container.
	 * @effect initializes the container with given weight,value and capacity.
	 * 		   | this(weight, value,null,capacity)
	 */
	protected Container(Weight weight, int value,Weight capacity) {
		this(weight, value,null,capacity);
	}
	
	/************************************************
	 * Content: defensive
	 ************************************************/
	
	/**
	 * Return the number of items the container stores.
	 */
	@Raw @Basic
	public int getNbItems(){
		if (contents == null){
			return 0;
		}
		return contents.size();
	}
	
	/**
	 * Returns the item at the given index.
	 * 
	 * @param index
	 * 		  The index to search to item at.
	 * @return the item at the given index.
	 * 		   | result.equals(contents.get(index))
	 * @throws IndexOutOfBoundsException
	 * 		   if the given index is higher than the number of items.
	 * 		  | if (index>getNbItems())
	 */
	public Item getItemAt(int index) throws IndexOutOfBoundsException{
		if (index>getNbItems()){
			throw new IndexOutOfBoundsException();
		}
		return contents.get(index);
	}
	
	/**
	 * Checks whether an item can be added to this container.
	 * 
	 * @param item
	 * 		  the item to check.
	 * @return false if the item is not effective.
	 * 		   | if (item == null)
	 * 		   | 	then result == false
	 * @return false if the item is already in the container.
	 *		   | if (contents.contain(item))
	 *		   |	then result == false
	 * @return false if the item's weight plus the current weight of the contents is greater
	 * 		   than the capacity.
	 * 		   | if (item.getWeight(Unit.kg).add(getWeightOfContents(Unit.kg)).compareTo(getCapacity(Unit.kg)>0)
	 * 		   |	then result == false
	 * @return false if the holder can't add the item.
	 * 		   | if (getHolder() != null)
	 * 		   | 	then if (!getHolder().canHaveAsItem(item))
	 * 		   |			then result == false;
	 * @return false if the item is already in a container
	 * 		   | if (item.getInContainer())
	 * 		   |	then result == false
	 * @return false if the parent can't add this item.
	 * 		   | if (getParent()!=null)
	 * 		   |	then let parent = getParent()
	 * 		   |		 if (!parent.canAdd(item))
	 * 		   |			then result == false
	 * 		   |		else
	 * 		   |			parent.canAdd(item)
	 * @return false if the item has already a holder.
	 * 		   | if (item.getHolder() != null)
	 * 		   | 	then result == false
	 */
	public boolean canAdd(Item item){
		if (item == null)
			return false;
		if (contents.contains(item))
			return false;
		if (item.getWeight(Unit.kg).add(getWeightOfContents(Unit.kg)).compareTo(getCapacity(Unit.kg))>0){
			return false;
		}
		if (item.getHolder() != null)
			return false;
		if (getHolder() != null){
			if (!getHolder().canHaveAsItem(item))
				return false;
		}
		if (item.getInContainer())
			return false;
		if (getParent()!=null){
			Backpack parent = getParent();
			if (!parent.canAdd(item))
				return false;
			else
				parent.canAdd(item);
		}
		return true;
	}

	/**
	 * checks whether given content can be the contents of this container.
	 * 
	 * @param content
	 * 		  The contents to check.
	 * @return false if an item in the content is null.
	 * 		  | for (item in content)
	 * 		  |		if (item==null)
	 * 		  |			then result == false.
	 * @return false if the sum of the contens is greater than the capacity.
	 * 		  | let sum = Weight.kg_0;
	 * 		  | for (item in content)
	 * 		  |		sum.add(item.getWeight(Unit.kg))
	 * 		  |	if (sum.compareTo(getCapacity(Unit.kg))>0)
	 * 		  |		then result == false
	 */
	public boolean canHaveAsContents(ArrayList<Item> content){
		Weight sum = Weight.kg_0;
		for (Item item:content){
			if (item == null){
				return false;
			}
			else {
				sum.add(item.getWeight(Unit.kg));
			}
		}
		if (sum.compareTo(getCapacity(Unit.kg))>0)
			return false;
		return true;
	}
	
	/**
	 * Adds an item in the container.
	 * 
	 * @param item
	 * 		  The item to add.
	 * @effect adds the item to the container and sets the holder of the item 
	 * 		   to the holder of the container.
	 * 		   | if (canAdd(item))
	 * 		   | 	then contents.add(item)
	 * @throws IllegalArgumentException
	 * 		   if item can't be added.
	 * 		   | !canAdd(item)
	 */
	public abstract void addItem(Item item) throws IllegalArgumentException;
	
	/**
	 * Checks whether an item is in a container.
	 * 
	 * @param item
	 * 		  The item to check.
	 * @return false if the item is not in the contents.
	 * 		   | result == contents.contains(item)
	 */
	public boolean ItemIn(Item item){
		return contents.contains(item);
	}
	
	/**
	 * Removes an item from the container at the given index.
	 * 
	 * @param index
	 * 		  The index to remove the item at.
	 * @effect removes the item from the container and sets the  the holder of the item
	 * 		   to null and sets inContainer of the item to false.
	 * 		  | let item = contents.get(index)
	 * 		  | item.setHolder(null)
	 * 		  | contents.remove(index)
	 * 		  | item.setInContainer(false)
	 * @effect Sets the parent to null if the item at given index is a container.
	 * 		   | if (item instanceof Container)
	 * 		   |	then ((Container)item).setParent(null)
	 * @throws IllegalArgumentException
	 * 		   The index is greater than the Nb items.
	 * 		   | (index>getNbItems())
	 */
	public void removeItem(int index) throws IllegalArgumentException{
		if (index>getNbItems()){
			throw new IllegalArgumentException("index out of range.");
		}
		else {
			Item item = contents.get(index);
			item.setHolder(null);
			contents.remove(index);	
			item.setInContainer(false);
			if (item instanceof Container){
				((Container)item).setParent(null);
			}
		}
	}
	
	/**
	 * Removes an item from the container.
	 * 
	 * @param item
	 * 		  The item to remove.
	 * @effect removes the item from the container and sets the holder of the item to null.
	 * 		  | contents.remove(item)
	 * 		  | item.setHolder(null)
	 * @effect Sets the parent to null if the given item is a container.
	 * 		   | if (item instanceof Container)
	 * 		   |	then ((Container)item).setParent(null)
	 * @throws IllegalArgumentException
	 * 		  if contents doenst contains the item.
	 * 		  |  (!contents.contains(item))
	 * 		  
	 */
	public void removeItem(Item item) throws IllegalArgumentException {
		if (!contents.contains(item)){
			throw new IllegalArgumentException("item not in container.");
		}
		else {
			contents.remove(item);
			item.setHolder(null);
			item.setInContainer(false);
			if (item instanceof Container){
				((Container)item).setParent(null);
			}
		}
	}
	
	/**
	 * Returns the contents.
	 */
	public ArrayList<Item> getContents(){
		return ((ArrayList<Item>) this.contents.clone());
	}
	
	/**
	 * A variable keeping track of all items stored in the backpack.
	 */
	protected ArrayList<Item> contents = new ArrayList<Item>();
	
	/************************************************
	 * Weight
	 ************************************************/
	
	/**
	 * Returns the total weight of the container and its contents in a given unit.
	 */
	@Raw
	public abstract Weight getWeight(Unit unit);
	/**
	 * Returns the total weight of the container and its contents.
	 */
	@Raw
	public abstract Weight getWeight();
	
	/**
	 * Gets the total weight of the contents in a unit.
	 * 
	 * @param unit
	 * 		  The unit in which the contents will be expressed.
	 * @return the total weights of the contents in a given unit.
	 * 		  | let sum = Weight.kg_0
	 * 		  | for item in contents
	 * 		  | 	let sum = sum.add(item.getWeight(Unit.kg))
	 * 		  |	result.equals(sum.toUnit(unit))
	 */
	public Weight getWeightOfContents(Unit unit){
		Weight sum = Weight.kg_0;
		for (Item item:contents){
			sum = sum.add(item.getWeight(Unit.kg));
		}
		return sum.toUnit(unit);
	}
	
	/************************************************
	 * Value
	 ************************************************/
	
	/**
	 * Returns the value of this container plus the value of its contents.
	 */
	@Raw
	public abstract int getValue();
	
	/************************************************
	 * Capacity: total
	 ************************************************/
	
	/**
	 * Return the capacity of a container in a given unit.
	 * 
	 * @param unit
	 * 		  the unit in which the capacity is expressed.
	 * @return the capacity
	 * 		  | result.equals(capacity.toUnit(unit))
	 */
	@Raw 
	public Weight getCapacity(Unit unit){
		return this.capacity.toUnit(unit);
	}
	
	/**
	 * Return the capacity of a container.
	 * 
	 * @param unit
	 * 		  the unit in which the capacity is expressed.
	 * @return the capacity
	 * 		  | result.equals(capacity)
	 */
	@Raw 
	public Weight getCapacity(){
		return this.capacity;
	}
	
	/**
	 * Checks whether the given weight is valid as the capacity.
	 * 
	 * @param weight
	 * 		  the weight to check.
	 * @return true is the weight is greater than or equal to 0.
	 * 		   | result == weight.getNumeral()>=0;
	 */
	public static boolean isValidCapacity(Weight weight){
		return weight.getNumeral()>=0;
	}
		
	/**
	 * A variable referencing the capacity of the container.
	 */
	private Weight capacity;
	
	
	/************************************************
	 * Parent
	 ************************************************/
	
	/**
	 * get the parent.
	 */
	@Raw @Basic
	public Backpack getParent(){
		return this.parent;
	}
	
	/**
	 * Sets the parent
	 * @param backpack
	 * 		  The parent.
	 * @post the new parent is the given backpack
	 * 		 | new.getParent().equals(backpack)
	 */
	protected void setParent(Backpack backpack){
		this.parent = backpack;
	}
	
	/**
	 * A variable referencing the parent of this backpack.
	 */
	private Backpack parent;
}
