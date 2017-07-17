package rpg.inventory;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import filesystem.DiskItem;

/**
 * An backpack iterator that iterates through all elements of a backpack.
 * 		implements enumeration.
 * 
 * @author Robbe
 * @version 1.0
 */
public class BackpackIterator implements Enumeration<Item> {
	
	
	
	
	@Override
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Item nextElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/************************************************
	 * Position
	 ************************************************/
	
	/**
	 * Returns the current position of the iterator
	 */
	@Raw @Basic
	public int getPostion(){
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
	 * 
	 */
	private List<Item> remainingItems = new ArrayList<Item>();
		
}