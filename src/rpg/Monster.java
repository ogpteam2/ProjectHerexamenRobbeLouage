package rpg;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import be.kuleuven.cs.som.annotate.*;
import rpg.inventory.Anchorpoint;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * A class of monsters.
 * 
 * @author Robbe
 * @version 1.0
 */
public class Monster extends Mobile {

	/************************************************
	 * Constructors
	 ************************************************/
	/**
	 * Initializes a new monster with given namen hitpoints and strength.
	 * 
	 * @param name
	 *        The name of the monster.
	 * @param hitpoints
	 * 		  The hitpoints of the monster.
	 * @param strength
	 * 		  The raw strength of the monster.
	 */
	@Raw
	public Monster(String name, long hitpoints,double strength, Anchorpoint[] anchors) {
		super(name,hitpoints, strength ,anchors);
		this.nbOfAnchorpoints = decideNbOfAnchorpoint(anchors);
	}
		


	/**
	 * Initializes a new monster with given namen hitpoints and strength.
	 * 
	 * @param name
	 *        The name of the monster.
	 * @param hitpoints
	 * 		  The hitpoints of the monster.
	 * @param strength
	 * 		  The raw strength of the monster.
	 */
	@Raw
	public Monster(String name, long hitpoints,double strength) {
		super(name,hitpoints, strength);
		

	}

	/************************************************
	 * Name: defensive
	 ************************************************/
	
	/**
	 * Check whether the given name is a valid one.
	 * 
	 * @return True if and only if the name is effective and it is a valid monsterName
	 * 	       | result == 
	 * 		   | 	( (name != null) &&
	 *         | 	  (isValidMonsterName(name) )
	 */
	@Override
	public boolean isValidName(String name){
		 return super.isValidName(name) && isValidMonsterName(name);
	}
	
	
	/**
	 * 
	 * Checks whether the name is a valid monster name
	 * 
	 * @param name
	 * 	      The name to check.
	 * @return true if the name matches with the pattern.
	 * 		   | result == name.matches("[A-Z]['A-Za-z\\s]+")
	 */
	private boolean isValidMonsterName(String name){
		return firstPattern.matcher(name).matches();
	}
	
	/**
	 * The  pattern to check the name against, checks if the name
	 * starts with a capital and consists of only ',letters and spaces.
	 */
	@Model
	private static final Pattern firstPattern = Pattern.compile("[A-Z]['A-Za-z\\s]+");
	
	/************************************************
	 * Damage: total
	 ************************************************/
	
	public double getTotalDamage(){
		return 0;
	}


	/************************************************
	 * Capacity
	 ************************************************/
	
	/**
	 * Calculates the damage for a monster based on a given strength, in a given unit.
	 * 
	 * @param strength
	 * 		  The capacity is calculated on this strength.
	 * @param unit
	 * 		  The unit in which the capacity is expressed.
	 * @return Weight.kg_0 if the unit is not effective
	 * 		   | if (unit == null)
	 * 		   |	then result.equals(Weight.kg_0)
	 * @return a weight with 9 times the strength as numeral and the given unit as unit.
	 * 		   | if (strength>0)
	 * 	       |	then result.equals((new Weight(strength*10,Unit.kg).toUnit(unit))
	 */
	@Override
	public Weight calculateCapacity(double strength,Unit unit) {
		if (unit == null){
			return Weight.kg_0;
		}
		if (strength>0){
			return (new Weight(strength*9,Unit.kg)).toUnit(unit);
		}
		return Weight.kg_0.toUnit(unit);
	}

	/************************************************
	 * Anchors
	 ************************************************/
	
	/**
	 * Return the number of anchor points this monster can use.
	 */
	@Raw @Basic
	public int getNbOfAnchorpoints(){
		return nbOfAnchorpoints;
	}
	
	/**
	 * Checks whether a monster can have the given nb items.
	 * 
	 * @return true if the nb is less or equal to the NbOfAnchorpoints
	 * 		   | result == nb<=getNbOfAnchorpoints()
	 */
	@Override
	public boolean canHaveAsNbItems(int nb){
		return nb<=getNbOfAnchorpoints();
	}
	
	/**
	 * Checks whether a anchorpointlist is valid for a monster
	 * 
	 * @return true if it is a valid anchor for a mobile.
	 * 		  | super.isValidAnchorpointList(anchors)
	 */
	@Override
	public boolean isValidAnchorpointList(Anchorpoint[] anchors) {
		return super.isValidAnchorpointList(anchors);
	}

	
	/**
	 * Generates the number of availabe anchor point types for a monster.
	 * 
	 * @return a number between zero and to amount of different types.
	 * 		   | ThreadLocalRandom.current().nextInt(0, AnchorpointType.NbOfAnchorpointTypes()+1)
	 */
	public static int generateNbOfAnchorpoints(){
		return ThreadLocalRandom.current().nextInt(0, AnchorpointType.NbOfAnchorpointTypes()+1);
	}
	
	/**
	 * Generates the available anchorpoints for the monster if no valid anchorpoint list is given.
	 * @return
	 */
	@Override
	public Anchorpoint[] generateAnchorpoints(){
		int differentTypes = AnchorpointType.NbOfAnchorpointTypes();
		Anchorpoint[] anchors  = new Anchorpoint[differentTypes];
		int available = getNbOfAnchorpoints();
		int[] ints = new Random().ints(0,differentTypes).distinct().limit(available).toArray();
		for (int i:ints){
			Anchorpoint point= new Anchorpoint(AnchorpointType.getTypeFromOrdinal(i),null);
			anchors[i] = point;
		}
		return anchors;
	}
	
	/**
	 * Return the available number of anchorpointypes in the given anchors.
	 * 
	 * @param anchors
	 * 		  to available anchorpoint types in this anchors.
	 * @return the number of available anchorpointtypes in this anchors.
	 * 		   | let sum = 0
	 * 	       | for (anchor in anchors)
	 * 		   |	if (anchor.getAnchorpointType() != null)
	 * 		   |		then sum++
	 * 		   | return sum
	 */
	private int decideNbOfAnchorpoint(Anchorpoint[] anchors) {
		int sum = 0;
		for (Anchorpoint anchor:anchors){
			if (anchor.getAnchorpointType() != null){
				sum++;
			}
		}
		return sum;
	}
	
	/**
	 * A variable referencing how many anchorpoints are available for this monster.
	 */
	private int nbOfAnchorpoints = generateNbOfAnchorpoints();
}
