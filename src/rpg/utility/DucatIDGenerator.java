package rpg.utility;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A ducat id generator that generates IDs for ducats.
 * 		Each ducat has the same ID, so this generator always generates -1L.
 * 
 * @author Robbe
 * @version 1.0
 */
public class DucatIDGenerator implements IDGenerator {

	/************************************************
	 * Constructors
	 ************************************************/
	/**
	 * Initializes the ducat generator.
	 */
	public DucatIDGenerator(){}
	
	/************************************************
	 * Generator
	 ************************************************/
	
	/**
	 * Generates the next ID.
	 * 
	 * @return the ducatID.
	 * 		   | result ==  getDucatID()
	 */
	@Override
	public long nextID() {
		return  getDucatID();
	}

	/**
	 * Checks whether the generator has an next ID.
	 * 
	 * @return true
	 * 		   | result == true
	 */
	@Override
	public boolean hasNextID() {
		return true;
	}

	/**
	 * Resets the generator.
	 * 
	 * @note this method does nothing.
	 */
	@Override
	public void reset() {}

	/**
	 * Generates an ID.
	 * 
	 * @return the ducat ID.
	 * 		   | result == getDucatID()
	 */
	@Override
	public long generateID() {
		return getDucatID();
	}
	/************************************************
	 * Value
	 ************************************************/
	
	/**
	 * Returns the ducat ID.
	 */
	@Basic
	public long getDucatID(){
		return ducatID;
	}
	
	/**
	 * A variable referencing the ducat ID.
	 */
	private static final long ducatID = -1L;
}
