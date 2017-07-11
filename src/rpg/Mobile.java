package rpg;

import be.kuleuven.cs.som.annotate.*;

/**
 * An abstract class of movable objects in the rpg.
 * 
 * @invar Each mobile must have a valid name.
 * 		  | isValidName(getName)
 * 
 * @author Robbe
 *
 */
public abstract class Mobile {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initialize a new Mobile with given name.
	 * 
	 * @param name
	 * 		  The name of the mobile.
	 * @effect The name of the mobile is set to the given name.
	 * 		   | setName(name)
	 * 
	 */
	protected Mobile(String name) throws IllegalArgumentException{
		setName(name);
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
}
