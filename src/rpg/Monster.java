package rpg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import be.kuleuven.cs.som.annotate.*;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Weapon;
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
	public Monster(String name, long hitpoints,double strength, Anchorpoint[] anchors,
			Weapon claws) {
		super(name,hitpoints, strength ,anchors);
		this.nbOfAnchorpoints = decideNbOfAnchorpoint(getAnchors());
		this.claws = claws;
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
	public Monster(String name, long hitpoints,double strength,Weapon claws) {
		this(name,hitpoints, strength,null,claws);
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
		this(name,hitpoints, strength,null,null);

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
		double total = 0;
		if (getClaws()!=null){
			total = getClaws().getDamage();
		}
		double sub = (int)(getRawStrength() - 5)/3;
		double result = total +sub;
		if (result<=0){
			return 0;
		}
		return result;
	}
	
	public Weapon getClaws(){
		return this.claws;
	}
	
	private final Weapon claws;


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
	 * 
	 * @return
	 */
	@Override
	public Anchorpoint[] generateAnchorpoints(){
		int differentTypes = AnchorpointType.NbOfAnchorpointTypes();
		Anchorpoint[] anchors  = new Anchorpoint[differentTypes];
		int available = generateNbOfAnchorpoints();
		ArrayList<Integer> randomList = random(0,differentTypes,available);
		for (Integer i=0;i<differentTypes;i++){
			if (randomList.contains(i)){
				anchors[i] = new Anchorpoint(AnchorpointType.getTypeFromOrdinal(i));
			}
			else{
				anchors[i] = new Anchorpoint();
			}
		}
		return anchors;
	}
	
	/**
	 * Generates unique random integers between min and max.
	 * 
	 * @param min
	 * 		  The min boundary
	 * @param max
	 * 		  the max boundary
	 * @param number
	 * 		  the number of unique numbers
	 * @pre the number must be  lay between (max-min) and 0.
	 * 		| number>=0 && number<(max-min)
	 * @return a arraylist of unique random integers.
	 * 		  | let  list = new ArrayList<Integer>()
	 * 		  |	for I in min...max
	 * 		  |		list.add(I)
	 * 		  | Collections.shuffle(list)
	 * 		  |	for I in 0...number
	 * 		  |		numbers.add(list.get(i))
	 * 		  | result.equals(numbers)
	 */
	private static ArrayList<Integer> random(int min,int max,int number){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=min;i<max+1;i++){
			list.add(new Integer(i));
		}
		Collections.shuffle(list);
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (int i=0;i<number;i++){
			numbers.add(list.get(i));
		}
		return numbers;
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
	private int decideNbOfAnchorpoint(Anchorpoint[] anchors){
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
	private int nbOfAnchorpoints;
	
	/************************************************
	 * Hit
	 ************************************************/
	
	
	@Override
	protected int random0to100(){
		int random = ThreadLocalRandom.current().nextInt(1,100);
		if (random>getCurrentHitpoints()){
			return (int)getCurrentHitpoints();
		}
		return random;
	}


	@Override
	protected void collectTreasures(Mobile other) {
	}



	@Override
	protected void heal(){}
}
