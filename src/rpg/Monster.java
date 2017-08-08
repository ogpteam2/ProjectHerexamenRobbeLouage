package rpg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import be.kuleuven.cs.som.annotate.*;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Ducat;
import rpg.inventory.Item;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * A class of monsters, with name, hitpoints, strength, anchors and claws.
 * 
 * @author Robbe
 * @version 1.0
 */
public class Monster extends Mobile {

	/************************************************
	 * Constructors
	 ************************************************/
	/**
	 * Initializes a new monster with given name, hitpoints, strength, anchors and claws.
	 * 
	 * @param name
	 *        The name of the monster.
	 * @param hitpoints
	 * 		  The hitpoints of the monster.
	 * @param strength
	 * 		  The raw strength of the monster.
	 * @param anchors
	 * 		  A list of anchors to attach to the montser.
	 * @param claws
	 * 		  The claws of the monster.
	 * @effect the monster gets initialized as a mobile with given name, hitpoints,
	 * 		   strength and anchors.
	 * 		   | super(name,hitpoints, strength ,anchors)
	 * @post the nbOfAnchorpoints gets set.
	 * 		   | new.getNbOfAnchorpoints() == decideNbOfAnchorpoint(getAnchors())
	 * @post the claws get set.
	 * 	     | new.getClaws().equals(claws)
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
	 * @param claws
	 * 		  The claws of the monster.
	 * @effect the mosnter gets initialized as a monster with given name hitpoints strength
	 * 		   and claws.
	 * 		  | this(name,hitpoints, strength,null,claws)
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
	 * @effect the monster gets initialized as a monster with given name,hitpoints strength.
	 * 		  | this(name, hitpoints, strength,null,null)
	 */
	@Raw
	public Monster(String name, long hitpoints,double strength) {
		this(name,hitpoints, strength,null,null);

	}
	
	/**
	 * 
	 * @param name
	 *        The name of the monster.
	 * @param hitpoints
	 * 		  The hitpoints of the monster.
	 * @param claws
	 * 		  The claws of the monster.
	 * @effect the monster gets initialized as a monster with given name,hitpoints and claws.
	 * 		  | this(name, hitpoints, 10,null,null)
	 * @effect standard weapons are added to this monster.
	 * 		   | this.addStandardWeapons()
	 */
	@Raw
	public Monster(String name, long hitpoints,Weapon claws) {
		this(name,hitpoints, 10,null,claws);
		this.addStandardWeapons();
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
	private static boolean isValidMonsterName(String name){
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
	
	/**
	 * Calculates the total damage of this monster.
	 * 
	 * @return the total damage of this mobile.
	 *         | let total = 0
	 *         | if (getClaws()!=null)
	 *         |	then total = getClaws().getDamage()
	 *         | let sub = (getRawStrength() - 5)/3
	 *         | let damage = total + sub
	 *         | result == damage
	 * @return 0 if the damage is less than 0.
	 * 		   | if (damage<0)
	 * 		   |	then result == 0
	 */
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
	
	/**
	 * Returns the claws of this monster.
	 */
	@Raw @Basic
	public Weapon getClaws(){
		return this.claws;
	}
	
	/**
	 * A variable referencing the claws of the monster.
	 */
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
	 * Checks whether a anchorpointlist is valid for a monster
	 * 
	 * @return false if the anchors is not effective.
	 * 		   | if (anchors == null)
	 * 	 	   |	then result == false
	 * @return false if the given anchors its length is different than the number
	 * 		   of different anchor point types.
	 * 		   | if (anchors.length!=AnchorpointType.NbOfAnchorpointTypes())
	 * 	       |	return false
	 * @return false if different is false.
	 * 		   | if (!different(anchors))
	 * 		   |	then result == false
	 * @return  false if the total weight of the given anchors is more than the capacity.
	 * 		   | if (totalWeight(anchors).compareTo(getCapacity(Unit.kg))>0)
	 * 		   | 	then result == false
	 * @return true otherwise.
	 */
	@Override
	public boolean canHaveAsAnchorpointList(Anchorpoint[] anchors) {
		return super.canHaveAsAnchorpointList(anchors);
	}

	
	/**
	 * Generates the number of availabe anchor point types for a monster.
	 * 
	 * @return a number between zero and 5.
	 * 		   | let random = new int[] {0,0,1,1,2,2,3,3,3,3,4,4,4,4,4,5,5,5,5,5}
	 * 		   | let random2 = ThreadLocalRandom.current().nextInt(0,random.length)
	 * 		   | result == random[random2]
	 */
	public static int generateNbOfAnchorpoints(){
		int[] random = new int[] {0,0,1,1,2,2,3,3,3,3,4,4,4,4,4,5,5,5,5,5};
		int random2 = ThreadLocalRandom.current().nextInt(0,random.length);
		return random[random2];
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
	 * Adds tree standard weapons to the mobile.
	 * 
	 * @effect tree Items are added.
	 * 		   | this.addItem(new Weapon(new Weight(1,Unit.kg),10))
	 * 		   | this.addItem(new Ducat())
	 * 		   | this.addItem(new Purse(new Weight(500,Unit.g),new Weight(500,Unit.g)))
	 */
	@Model
	private void addStandardWeapons(){
		this.addItem(new Weapon(new Weight(1,Unit.kg),10));
		this.addItem(new Ducat());
		this.addItem(new Purse(new Weight(500,Unit.g),new Weight(500,Unit.g)));
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
	@Model
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
	@Model
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
	
	/**
	 * Generates a random number between 1 and 100 but if the number if
	 * higher than the current hitpoints, the currenthitpoints are chosen.
	 * 
	 *  @return a number between 0 and 100.
	 *  		| let random = ThreadLocalRandom.current().nextInt(1,100)
	 *  		| if (random>getCurrentHitpoints())
	 *  		|	 then result == getCurrentHitpoints()
	 *  		| result == random
	 */
	@Override
	protected int random0to100(){
		int random = ThreadLocalRandom.current().nextInt(1,100);
		if (random>getCurrentHitpoints()){
			return (int)getCurrentHitpoints();
		}
		return random;
	}

	/**
	 * Collects treasures from another mobile.
	 * 
	 * @effect A random number between 0 and 3 is chosen.
	 * 		  | let  random = ThreadLocalRandom.current().nextInt(0,3+1)
	 * @effect if random is 0 or 1 take ducats and purses from other mobile.
	 * 		   | if (random == 0 || random == 1)
	 * 		   |	then takeDucatsAndPurses(other)
	 * @effect if random is 2 take random items from other.
	 * 		   | if (random == 2)
	 * 		   |	then switchItems(other)
	 * @effect if random is 3 then switch item with other.
	 * 		   | if (random == 3)
	 * 		   |	then switchItems(other)
	 */
	@Override
	protected void collectTreasures(Mobile other) {
		int random = ThreadLocalRandom.current().nextInt(0,3+1);
		if (random == 0 || random == 1){
			takeDucatsAndPurses(other);
		}
		else if (random == 2){
			takeRandom(other);
		}
		else if (random == 3){
			switchItems(other);
		}
			
	}

	/**
	 * Takes the ducats and purses of a mobile until the anchorpoints are full.
	 * 
	 * @param other
	 * 		  The other mobile to take the ducats and purses of.
	 * @effect the ducats and purses of another mobile will be added to this monster.
	 * 		   | let acnhorpoinTypes = AnchorpointType.values()
	 * 	       | let otherFree = other.getFreeAnchorpoints()
	 * 		   |	then for type in acnhorpoinTypes
	 * 		   |		  	if (!otherFree.contains(type))
	 * 		   |				then let item = other.getItemAt(type)
	 * 	       |			    	 other.removeItemAt(type)
	 * 		   |					 if (this.getFreeAnchorpoints().size()>0)
	 * 		   |				     	 then if (item instanceof Purse || item instanceof Ducat)
	 * 		   |						 		then this.addItem(item)
	 * 		   |				     		  else if (item instanceof Weapon)
	 * 		   |						  	  		then item = null
	 */
	@Model
	private void takeDucatsAndPurses(Mobile other){
		AnchorpointType[] acnhorpoinTypes = AnchorpointType.values();
		ArrayList<AnchorpointType> otherFree = other.getFreeAnchorpoints();
			for (AnchorpointType type:acnhorpoinTypes){
				if (!otherFree.contains(type)){
					Item item = other.getItemAt(type);
					other.removeItemAt(type);
					if (this.getFreeAnchorpoints().size()>0){
					if (item instanceof Purse || item instanceof Ducat){
						this.addItem(item);
					}
					else if (item instanceof Weapon){
						item = null;
					}
				}
			}
		}
	}
	
	/**
	 * Takes random items from a another mobile.
	 * 
	 * @param other
	 * 		  The other mobile to take items from.
	 * @effect takes random items from another mobile.
	 * 		   | let acnhorpoinTypes = AnchorpointType.values()
	 * 	       | let otherFree = other.getFreeAnchorpoints()
	 * 		   |	then for type in acnhorpoinTypes
	 * 		   |		  	if (!otherFree.contains(type))
	 * 		   |				then let item = other.getItemAt(type)
	 * 	       |			    	 other.removeItemAt(type)
	 * 	  	   |    			     if (this.getFreeAnchorpoints().size()>0)
	 * 		   |						then if (type.ordinal()%2==0)
	 * 		   |					 		 	 then this.addItem(item)
	 * 		   |					 		 else if (item instanceof Weapon)
	 * 		   |						  	  	 then item = null
	 */
	@Model
	private void takeRandom(Mobile other){
		AnchorpointType[] acnhorpoinTypes = AnchorpointType.values();
		ArrayList<AnchorpointType> otherFree = other.getFreeAnchorpoints();
			for (AnchorpointType type:acnhorpoinTypes){
				if (!otherFree.contains(type)){
					Item item = other.getItemAt(type);
					other.removeItemAt(type);
					if (this.getFreeAnchorpoints().size()>0){
					if (type.ordinal()%2==0){
						this.addItem(item);
						}
					else if (item instanceof Weapon){
						item = null;
						}
					}
				}
			}
	}
	
	/**
	 * Switches certain items with another mobile.
	 * 
	 * @param other
	 * 		  The other mobile to switch items with.
	 * @effect some items of the other mobile will be added to this mobile.
	 * 		   | let acnhorpoinTypes = AnchorpointType.values()
	 * 	       | let otherFree = other.getFreeAnchorpoints()
	 * 		   | let thisFree = this.getFreeAnchorpoints()
	 * 		   | for type in acnhorpoinTypes
	 * 		   | 	let item = other.getItemAt(type)
	 * 		   |	other.removeItemAt(type)
	 * 		   |	if (!otherFree.contains(type) && !thisFree.contains(type))
	 * 		   |		then this.removeItemAt(type)
	 * 		   |		then this.addItemAt(type,item)
	 * 		   |	else if (item instanceof Weapon)
	 * 		   |			then item = null
	 */
	@Model
	private void switchItems(Mobile other){
		AnchorpointType[] acnhorpoinTypes = AnchorpointType.values();
		ArrayList<AnchorpointType> otherFree = other.getFreeAnchorpoints();
		ArrayList<AnchorpointType> thisFree = this.getFreeAnchorpoints();
		for (AnchorpointType type:acnhorpoinTypes){
			Item item = other.getItemAt(type);
			other.removeItemAt(type);
			if (!otherFree.contains(type) && !thisFree.contains(type)){
				this.removeItemAt(type);
				this.addItemAt(type,item);
			}
			else if (item instanceof Weapon){
				item = null;
			}
		}
	}

	/**
	 * Heals the monster for 0.
	 * 
	 * @effect the monster gets healed for an amount of 0.
	 * 		   | setCurrentHitpoints(getCurrentHitpoints())
	 */
	@Override
	@Model
	protected void heal(){}
}
