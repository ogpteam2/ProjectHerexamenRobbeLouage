package rpg.inventory;
import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;
import rpg.Mobile;
import rpg.utility.IDGenerator;
import rpg.utility.PurseIDGenerator;
import rpg.utility.WeaponIDGenerator;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * A class of purses, ducats can be stored in purses.
 * 
 * no additional invars imposed.
 * 
 * @author Robbe
 * @version 1.0
 */
public class Purse extends Container {
	
	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a purse with given weight, holder and capacity.
	 * 
	 * @param weight
	 * 	      The weight of the purse.
	 * @param holder
	 * 		  The holder of the purse.
	 * @param capacity
	 * 		  The capacity of the purse.
	 * @effect Initializes a purse as a container with given weight, holder and capacity.
	 * 		   | super(weight, 0, holder, capacity)
	 */
	public Purse(Weight weight, Mobile holder, Weight capacity) {
		super(weight, 0, holder, capacity);
	}
	
	/**
	 * Initializes a purse with given weight and capacity.
	 * @param weight
	 * 	      The weight of the purse.
	 * @param capacity
	 * 		  The capacity of the purse.
	 * @effect Initializes a purse as a container with given weight and capacity.
	 * 		   | super(weight, 0, null, capacity)
	 */
	public Purse(Weight weight, Weight capacity){
		super(weight, 0, null, capacity);
	}

	/************************************************
	 * ID
	 ************************************************/
	
	/**
	 * Returns the generator for purses.
	 */
	@Raw @Basic @Override
	protected IDGenerator getIDGenerator() {
		return generator;
	}
	
	/**
	 * A variable referencing the generator for this class that generates IDs.
	 */
	private static PurseIDGenerator generator = new PurseIDGenerator();
	
	/************************************************
	 * Contents
	 ************************************************/
	
	/**
	 * Adds an ducat in the purse.
	 * 
	 * @param item
	 * 		  The item to add.
	 * @effect adds the item to the purse.
	 * 		   | if (canAdd(item))
	 * 		   | 	then contents.add(item)
	 * @effect is the item added will exceed the capacity of the purse.
	 * 		   The purse will be broken.
	 * 		   | if (item!=null && item instanceof Ducat)	
	 * 		   |	then let total = item.getWeight(Unit.kg).add(getWeightOfContents(Unit.kg))
	 * 		   |		 if (total.compareTo(getCapacity(Unit.kg))>0)
	 * 		   |		 	then brokenAction();
	 * @throws IllegalArgumentException
	 * 		   if item can't be added.
	 * 		   | !canAdd(item)
	 */
	@Override
	public void addItem(Item item) throws IllegalArgumentException{
		if (canAdd(item)){
			contents.add(item);
			item.setInContainer(true);
		}
		else if (item!=null && item instanceof Ducat){
			Weight total = item.getWeight(Unit.kg).add(getWeightOfContents(Unit.kg));
			if (total.compareTo(getCapacity(Unit.kg))>0){
				brokenAction();
			}
		}
		else {
			throw new IllegalArgumentException("item can't be added.");
		}
	}
	
	
	/**
	 * When the purse breaks these actions are taken.
	 * 
	 * @effect
	 * @effect
	 */
	@Model
	private void brokenAction(){
		if (getHolder() != null){
			Mobile holder = getHolder();
			Weight totalWeight = getWeight(Unit.kg);
			for (Backpack backpack:holder.findBackpacks()){
				Weight backpackWeight = backpack.getWeightOfContents(Unit.kg);
				if (totalWeight.add(backpackWeight).compareTo(backpack.getCapacity(Unit.kg))<=0){
					for (int i=0;i<getValue();i++){
						backpack.addItem(new Ducat());
					}
					break;
				}
			}
		}
		setBroken(true);
		contents.clear();
	}
	
	
	
	
	
	/**
	 * Checks whether an item can be added to the purse.
	 * 
	 * @param item
	 * 		  the item to check
	 * @return false if purse is broken.
	 * 		   | if (getBroken())
	 * 		   |	then result == false
	 * @return true if the item can be added to a container and if the item is an 
	 * 		   instance of Ducat.
	 * 		   | result == super.canAdd(item) && (item instanceof Ducat)
	 */
	@Override
	public boolean canAdd(Item item){
		if (getBroken()){
			return false;
		}
		return super.canAdd(item) && (item instanceof Ducat);
	}
	
	/**
	 * Checks whether a content can be the content of this purse.
	 * 
	 * @param content
	 * 		  The content to check.
	 * @return true if the content is valid for a container and 
	 * 		   if all the items in contents are ducats.
	 * 		   | result == super.canHaveAsContents(content)&& allDucats(content)
	 */
	@Override
	public boolean canHaveAsContents(ArrayList<Item> content){
		return super.canHaveAsContents(content)&& allDucats(content);
		
	}
	
	/**
	 * Checks whether all items in content are Ducats.
	 * 
	 * @param content
	 * 		  the content to check.
	 * @return false if one item in content is not an Ducat.
	 * 		   | for (item in content)
	 * 		   |	if (!item instanceof Ducat)
	 * 		   |		then result == false
	 */
	@Model
	private static boolean allDucats(ArrayList<Item> content){
		for (Item item:content){
			if (!(item instanceof Ducat)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Transfers the value of one purse to another one.
	 * 
	 * @param purse
	 * 		  The purse to transfer the value to.
	 * @effect if purse is effective the items are transfered.
	 * 		   | if (purse != null)
	 * 		   |	then for I in 0...getNbItems
	 * 		   |		purse.addItem(new Ducat())
	 * 		   | 	brokenAction()
	 */
	public void transfer(Purse purse){
		if (purse == null){	
		}
		else{
			for (int i=0;i<this.getNbItems();i++){
				purse.addItem(new Ducat());
			}
			brokenAction();
		}	
	}

	/**
	 * Checks whether an item is in a container.
	 * 
	 * @param item
	 * 		  The item to check.
	 * @return true if the item is in the backpack.
	 * 		   | result == contents.contains(item)
	 */
	@Override
	public boolean ItemIn(Item item){
		return contents.contains(item);
	}
	
	/************************************************
	 * Weight
	 ************************************************/
	
	/**
	 * Return the total weight of the purse in a given unit.
	 * 
	 * @param unit
	 * 		  The unit in which the weight is given.
	 * @return the own weight if the purse is broken
	 * 	       | if (getBroken())
	 * 		   |	then result.equals(getOwnWeight().toUnit(unit)
	 * @return the total weight of the purse.
	 * 	      |result.equals(getOwnWeight().add(Ducat.getDucatWeight().times(getNbItems())).toUnit(unit))
	 */
	@Override
	public Weight getWeight(Unit unit) {
		if (getBroken()){
			return getOwnWeight().toUnit(unit);
		}
		return getOwnWeight().add(Ducat.getDucatWeight().times(getNbItems())).toUnit(unit);
	}

	/**
	 * Return the total weight of the purse.
	 * 
	 * @return the own weight if the purse is broken
	 * 	       | if (getBroken())
	 * 		   |	then result.equals(getOwnWeight())
	 * @return the total weight of the purse.
	 * 	      | result.equals(getOwnWeight().add(Ducat.getDucatWeight().times(getNbItems()))
	 */
	@Override
	public Weight getWeight() {
		if (getBroken()){
			return getOwnWeight();
		}
		return getOwnWeight().add(Ducat.getDucatWeight().times(getNbItems()));
	}
	
	/************************************************
	 * Value
	 ************************************************/
	
	/**
	 * The total value of this purse.
	 * 
	 * @return 0 if the purse is broken.
	 * 		   | if (getBroken())
	 * 		   | 	then result == 0
	 * @return the total value of this purse.
	 * 		   | result == Ducat.getDucatValue()*getNbItems()
	 */
	@Override
	public int getValue() {
		if (getBroken()){
			return 0;
		}
		return Ducat.getDucatValue()*getNbItems();
	}
	
	/************************************************
	 * Broken
	 ************************************************/
	
	/**
	 * Returns the broken status.
	 */
	@Raw @Basic
	public boolean getBroken(){
		return this.broken;
	}
	
	/**
	 * Sets broken to given boolean.
	 * @param state
	 * 		  The new state.
	 * @post the new broken is set to the given state.
	 * 		 | new.getBroken() == state
	 */
	private void setBroken(boolean state){
		this.broken = state;
	}
	
	/**
	 * A variable referencing whether the purse is broken or not.
	 */
	private boolean broken = false;
	
}
