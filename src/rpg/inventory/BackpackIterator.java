package rpg.inventory;

import java.util.ArrayList;
import java.util.Enumeration;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * An backpack iterator that iterates through all elements of a backpack.
 * implements enumeration.
 * 
 * Each iterator has a position. The position is initialized at zero.
 * You can get the next element by using the nextElement() method. This
 * method throws an exception when there are no more elements.
 * 
 * You can also reset the iterator by using reset(),
 * then the position is again set to 0.
 * 
 * When this iterator is initialized with contents the first 
 * item in the contents is given when the method nextElement()
 * is invoked.
 * 
 * @author Robbe
 * @version 1.0
 */
public class BackpackIterator implements Enumeration<Item> {
	
	/************************************************
	 * Constructor
	 ************************************************/
	
	/**
	 * Initializes a BackpackIterator with given contents.
	 * 
	 * @param contents
	 * 		  The contents to initialize the iterator with.
	 * @post the remainingItems are set to the contents.
	 * 		 | new.remainingItems.equals(contents)
	 */
	public BackpackIterator(ArrayList<Item> contents){
		this.remainingItems = contents;
	}
	
	/************************************************
	 * Enumeration
	 ************************************************/
	
	/**
	 * Checks whether the iterator has more items.
	 * 
	 * @return false if the conents are not effective
	 * 		   | if (remainingItems ==null)
	 * 		   | 	then result == false
	 * @return false if the position is greater than the remaining items' size.
	 * 		  | if (position>remainingItems.size())
	 * 		  |		then result == false
	 */
	@Override
	public boolean hasMoreElements() {
		if (remainingItems ==null){
			return false;
		}
		if (position>=remainingItems.size()){
			return false;
		}
		return true;
	}

	/**
	 * Gives the next element in the list.
	 * 
	 * @effect if the iterator has more elements then the item at the current position is given.
	 * 		   And the position is incremented.
	 * 		   | if (hasMoreElements())
	 * 		   |	then let item = remainingItems.get(position)
	 * 		   |		 position++
	 * 		   |		 result.equals(item)
	 * @throws RuntimeException
	 * 		   if the iterator has no more elements.
	 * 	       | (!hasMoreElements())
	 */
	@Override
	public Item nextElement() throws RuntimeException {
		if (hasMoreElements()){
			Item item = remainingItems.get(position);
			position++;
			return item;
		}
		else{
			throw new RuntimeException("no more elements.");
		}
	}
	
	/**
	 * Resets the iterator.
	 * 
	 * @post set position to zero.
	 * 		  | new.getPosition()==0
	 */
	public void reset(){
		position = 0;
	}
	
	/************************************************
	 * Position
	 ************************************************/
	
	public int getNbRemainingItems(){
		return remainingItems.size()-position;
	}
	
	/**
	 * Returns the current position of the iterator
	 */
	@Raw @Basic
	public int getPosition(){
		return this.position;
	}
	
	/**
	 * A variable referencing the current position of the iterator.
	 */
	private int position;
	
	/************************************************
	 * Contents
	 ************************************************/
	
	/**
	 * A variable referencing the list of items.
	 */
	private ArrayList<Item> remainingItems = new ArrayList<Item>();
		
}