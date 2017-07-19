package rpg.inventory;
import be.kuleuven.cs.som.annotate.*;
import rpg.Mobile;
import rpg.utility.BackpackIDGenerator;
import rpg.utility.IDGenerator;
import rpg.utility.PurseIDGenerator;
import rpg.value.Unit;
import rpg.value.Weight;

public class Backpack extends Container {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a backpack with given weight,value,holder and capacity.
	 * 
	 * @param weight
	 * 	 	  the weight of the backpack.
	 * @param value
	 * 		  The value of the backpack.
	 * @param holder
	 * 		  The holder of the backpack.
	 * @param capacity
	 * 		  The capacity of the backpack.
	 * @effect initializes the backpack as a container with given weight,value,holder and
	 * 		   capacity.
	 * 		   | super(weight, value, holder, capacity)
	 */
	public Backpack(Weight weight, int value, Mobile holder, Weight capacity) {
		super(weight, value, holder, capacity);
	}

	/**
	 * Initializes a backpack with given weight,value and capacity.
	 * 
	 * @param weight
	 * 	 	  the weight of the backpack.
	 * @param value
	 * 		  The value of the backpack.
	 * @param capacity
	 * 		  The capacity of the backpack.
	 * @effect initializes the backpack as a container with given weight,value and
	 * 		   capacity.
	 * 		   | super(weight, value, null, capacity)
	 */
	public Backpack(Weight weight, int value, Weight capacity) {
		super(weight, value, null, capacity);
	}
	
	
	/************************************************
	 * ID
	 ************************************************/
	
	/**
	 * Returns the generator for backpacks.
	 */
	@Override
	protected IDGenerator getIDGenerator() {
		return generator;
	}

	/**
	 * A variable referencing the generator for this class that generates IDs.
	 */
	private static BackpackIDGenerator generator = new BackpackIDGenerator();


	/************************************************
	 * Contents
	 ************************************************/
	
	/**
	 * Return the number of items the container stores.
	 * 
	 * @return the nb of items in the container.
	 *         | let sum = 0
	 *         | let it = new BackpackIterator(contents)
	 *         | while (it.hasMoreElements())
	 *         | 	let current = it.nextElement()
	 *         |	if (current instanceof Backpack)
	 *         |		then let backpack = (Backpack) current
	 *         |			 let sum += backpack.getContents().size() + 1
	 *         |			 backpack.getNbItems()
	 *         |	else if (current instanceof Purse)
	 *         |		let purse = (Purse) current
	 *         |		let sum += purse.getNbItems() + 1
	 *         |	else
	 *         |		sum++
	 *         | result == sum
	 */
	@Override
	public int getNbItems(){
		if (contents == null){
			return 0;
		}
		int sum = 0;
		BackpackIterator it = new BackpackIterator(contents);
		while (it.hasMoreElements()){
			Item current = it.nextElement();
			if (current instanceof Backpack){
				Backpack backpack = (Backpack) current;
				sum += backpack.getNbItems()+1;
			}
			else if (current instanceof Purse){
				Purse purse = (Purse) current;
				sum += purse.getNbItems() + 1;
			}
			else {
				sum++;
			}
		}
		return sum;
	}
	
	/**
	 * Adds an item to the contents.
	 * 
	 * @effect if item can be added to the contents, it will be added.	
	 * 		   and the holder of the item will be set to this holder.
	 * 		   | if (canAdd(item))
	 * 		   |	then contents.add(item)
	 * 		   |		item.setHolder(getHolder())
	 * 		   |		item.setInContainer(true)
	 * @effect If the item is a container the parent will be set to this backpack
	 * 		   | if (canAdd(item))
	 * 		   |	then if (item instanceof Container)
	 * 	       |		then ((Container)item).setParent(this)
	 * @throws IllegalArgumentException
	 * 		   the item can't be added.
	 * 	       | (!canAdd(item))
	 */
	@Override
	public void addItem(Item item) throws IllegalArgumentException {
		if (canAdd(item)){
			item.setHolder(this.getHolder());
			contents.add(item);
			item.setInContainer(true);
			if (item instanceof Container){
				((Container)item).setParent(this);
			}
		}
		else {
			throw new IllegalArgumentException("item can't be added");
		}
	}	
	
	/**
	 * Return an item with a certain ID. If two or more items have the same ID,
	 * the item with the lesser index get chosen.
	 * 
	 * @param id
	 * 		  The item with this id will be found.
	 * @return the first item with given ID.
	 * 		   | let it = getIterator()
	 * 		   | while (it.hasMoreElements())
	 * 		   |	let item = it.nextElement()
	 * 		   |	if (item.getId()==id)
	 * 		   |		then result==equals(item)
	 * @throws IllegalArgumentException
	 * 		   If there is no item in the backpack with such an ID.
	 */
	public Item getItemWithID(long id) throws IllegalArgumentException{
		BackpackIterator it = getIterator();
		while (it.hasMoreElements()){
			Item item = it.nextElement();
			if (item.getId()==id){
				return item;
			}
		}
		throw new IllegalArgumentException("No item in backpack with such an ID.");
	}
	
	/**
	 * Checks whether an item is in a backpack.
	 * 
	 * @param item
	 * 		  The item to check.
	 * @return true if the item is in the backpack.
	 * 		   | let subresult = fasle;
	 * 		   | let it = getIterator()
	 * 	       | while (it.hasMoreElements())
	 * 		   | 	let current = it.nextElement()
	 * 		   |	if (current instanceof Backpack)
	 * 		   |		then let backpack = (Backpack) current
	 * 		   |		subresult =  backpack.ItemIn(item)
	 * 		   |	else if (current instanceof Purse)
	 * 		   |		then let purse = (Purse) current
	 * 		   |		subresult = (purse.ItemIn(item))
	 * 		   |	else
	 * 		   |		if (item.equals(current))
	 * 		   |			then subresult ==true
	 * 		   |	if (subresult)
	 * 		   |		then result ==  subresult
	 * @return true if the item given is the backpack.	
	 * 		   | if (item.equals(this))
	 * 		   |	then result == true.
	 */
	@Override
	public boolean ItemIn(Item item){
		if (item.equals(this)){
			return true;
		}
		boolean result = false;
		BackpackIterator it = getIterator();
		while (it.hasMoreElements()){
			Item current = it.nextElement();
			if (current instanceof Backpack){
				Backpack backpack = (Backpack) current;
				result = backpack.ItemIn(item);
			}
			else if (current instanceof Purse){
				Purse purse = (Purse) current;
				result = purse.ItemIn(item);
			}
			else {
				if (item.equals(current)){
					result = true;
				}
			}
			if (result == true){
				return result;
			}
		}
		return false;
	}
	
	/**
	 * Gets an iterator with the given contents.
	 * 
	 * @return the iterator
	 * 		   | result.equals(new BackpackIterator(contents))
	 */
	public BackpackIterator getIterator(){
		return new BackpackIterator(contents);
	}
	/************************************************
	 * Weight
	 ************************************************/
	
	/**
	 * Return the total weight of the backpack and its contents.
	 * 
	 * @param unit
	 * 		  The unit of the weight.
	 * @return the total weight.
	 * 		   | let it = getIterator()
	 * 		   | sum = Weight.kg_0
	 * 		   |  while (it.hasMoreElements())
	 * 		   |	let sum = sum.add(it.nextElement().getWeight(Unit.kg))
	 * 		   | sum = getOwnWeight().add(sum)
	 * 		   | result.equals(sum.toUnit(unit))
	 */
	@Override
	public Weight getWeight(Unit unit) {
		BackpackIterator it = getIterator();
		Weight sum = Weight.kg_0;
		while (it.hasMoreElements()){
			sum = sum.add(it.nextElement().getWeight(Unit.kg));
		}
		sum = getOwnWeight().add(sum);
		return sum.toUnit(unit);
	}


	/**
	 * Return the total weight of the backpack and its contents.
	 * 
	 * @return the total weight.
	 * 		   | let it = getIterator()
	 * 		   | sum = Weight.kg_0
	 * 		   |  while (it.hasMoreElements())
	 * 		   |	let sum = sum.add(it.nextElement().getWeight(Unit.kg))
	 * 		   | sum = getOwnWeight().add(sum)
	 * 		   | result.equals(sum)
	 */
	@Override
	public Weight getWeight() {
		BackpackIterator it = getIterator();
		Weight sum = Weight.kg_0;
		while (it.hasMoreElements()){
			sum = sum.add(it.nextElement().getWeight(Unit.kg));
		}
		sum = getOwnWeight().add(sum);
		return sum;
	}
	
	/************************************************
	 * Value
	 ************************************************/
	
	/**
	 * Return the total value of the backpack.
	 * 
	 * @return the total value of the backpack.
	 * 	       | let it = getIterator()
	 * 	       | let sum = 0
	 * 		   | while (it.hasMoreElements())
	 * 		   | 	let sum += it.nextElement().getValue()
	 * 		   | let sum += getOwnValue()
	 * 		   | result == sum
	 */
	@Override
	public int getValue() {
		BackpackIterator it = getIterator();
		int sum = 0;
		while (it.hasMoreElements()){
			sum += it.nextElement().getValue();
		}
		sum += getOwnValue();
		return sum;
	}
	
}
