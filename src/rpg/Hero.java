package rpg;
import be.kuleuven.cs.som.annotate.*;
import rpg.inventory.Anchorpoint;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.*;

/**
 * A class of heroes.
 * 
 * @author Robbe
 * @version 1.0
 */
public class Hero extends Mobile {
	
	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a new Hero with given name and hitpoints.
	 * 
	 * @param name
	 *        The name of the hero.
	 * @param hitpoints
	 * 		  The hitpoints of the Hero.
	 * @param strength
	 * 		  The raw strength of the Hero
	 * @param anchors
	 * 		  The anchors for the mobile.
	 * @effect the hero is initialized with given name, hitpoints, strength and anchors.
	 * 		   | super(name,hitpoints,strength,anchors)
	 */
	@Raw
	public Hero(String name, long hitpoints, double strength, Anchorpoint[] anchors) 
			throws IllegalArgumentException 
	{
		super(name,hitpoints,strength,anchors);
	}
	
	/**
	 * Initializes a new Hero with given name and hitpoints.
	 * 
	 * @param name
	 *        The name of the hero.
	 * @param hitpoints
	 * 		  The hitpoints of the Hero.
	 * @param strength
	 * 		  The raw strength of the Hero.
	 * @effect the hero is initialized with given name, hitpoints, strength.
	 * 		   | super(name,hitpoints,strength,null)
	 */
	@Raw
	public Hero(String name, long hitpoints, double strength) 
			throws IllegalArgumentException 
	{
		this(name,hitpoints,strength,null);
	}
	
	/************************************************
	 * Name: defensive
	 ************************************************/
	
	/**
	 * Check whether the given name is a valid one.
	 * 
	 * @return True if and only if the name is effective and it is a valid heroName
	 * 	       | result == 
	 * 		   | 	( (name != null) &&
	 *         | 	  (isValidHeroName(name) )
	 */
	@Override
	public boolean isValidName(String name){
		 return super.isValidName(name) && isValidHeroName(name);
	}
	
	/**
	 * 
	 * @param name
	 * 	      The name to check.
	 * @return true if the name consist of letters,:,',spaces and that there
	 * 		   are a maximum of 2 's and that a space is present after every colon, also
	 * 	       the name should start with a capital letter.
	 * 		   False otherwise.
	 * 		   | result ==
	 * 		   | 	name.matches("[A-Z][:'A-Za-z\\s]+") &&
	 * 		   |    getNumberOfMatches("'",name) <= 2 &&
	 * 		   | 	getNumberOfMatches(":(?!\\s)",name) == 0 
	 *          
	 */
	@Model
	private boolean isValidHeroName(String name){
		boolean nameIsValid = firstPattern.matcher(name).matches();
		int numberOfApos = getNumberOfMatches(patternApo,name);
		int colonNotSpace = getNumberOfMatches(patternColonSpace,name);
		return  nameIsValid && numberOfApos<=2 && colonNotSpace==0;
	}
	

	/**
	 * Gets the number of matches with a given pattern.
	 * 
	 * @param pattern
	 * 		  The pattern to check the string against.
	 * @param string
	 * 		  The string to check against the pattern.
	 * @return The number of matches.
	 * 	       | let count = 0
	 * 		   | 	matcher = pattern.matcher(string)
	 * 	       | 	while (matcher.find())
	 *	       |		count++
	 *		   | result == count 
	 *
	 */
	@Model
	private int getNumberOfMatches(Pattern pattern, String string){
		int count = 0;
		Matcher matcher = pattern.matcher(string);
		while (matcher.find())
		    count++;
		return count;
	}
	
	/**
	 * The first pattern to check the name against, checks if the name
	 * starts with a capital and consists of only :,letters and spaces.
	 */
	@Model
	private static final Pattern firstPattern = Pattern.compile("[A-Z][:'A-Za-z\\s]+");
	/**
	 * The second pattern to check the name against, and checks whether the name
	 * consists of '.
	 */
	@Model
	private static final Pattern patternApo = Pattern.compile("'");
	/**
	 * The third pattern to check the name against, checks that after every colon a 
	 * space is present.
	 */
	@Model
	private static final Pattern patternColonSpace = Pattern.compile(":(?!\\s)");

	/************************************************
	 * Damage: total
	 ************************************************/
	
	public  double getTotalDamage(){
		return 0;
	}



	/************************************************
	 * Capcity
	 ************************************************/
	
	/**
	 * Calculates the capacity based on the raw strength in a given unit.
	 * 
	 * @param strength
	 * 		  The capacity is calculated on this strength.
	 * @param unit
	 * 		  The unit in which the capacity is expressed.
	 * @return Weight.kg_0 if the unit is not effective
	 * 		   | if (unit == null)
	 * 		   |	then result.equals(Weight.kg_0)
	 * @return Weight with numeral zero and given unit if the raw strength is less than 1.
	 * 		   | if (getRawStrength() < 1)
	 * 		   |	then result.equals(Weight.kg_0.toUnit(unit))
	 * @return Weight with numeral ten times the strength and given unit 
	 * 		   if the raw strength is lays between 1 and 10.
	 * 		   | if (strength>10 && strength<=20)
	 * 	       |	result.equals((new Weight(capacityStrengthBetween1020(strength),Unit.kg).toUnit(unit))
	 * @return Weight with numeral four times what it would be if the strength was ten 
	 * 	      units lower.
	 * 		  | let capacity =  capacityStrengthHigherThan20(strength)
	 * 		  | result.equals((new Weight(capacity,Unit.kg).toUnit(unit)) 
	 */
	@Override
	public Weight calculateCapacity(double strength,Unit unit) {
		double capacity = 0.0;
		if (unit==null){
			return Weight.kg_0;
		}
		else if (strength < 1){
			capacity = 0;
		}
		else if (strength>=1 && strength<=10){
			capacity = strength*10;
		}
		else if (strength>10 && strength<=20){
			capacity = capacityStrengthBetween1020(strength);
		}
		else if (strength>20){
				capacity = capacityStrengthHigherThan20(strength);
		}
		return (new Weight(capacity,Unit.kg)).toUnit(unit);
	}
	
	/**
	 * Calculates the capacity if the strength lays between 10 and 20.
	 * 
	 * @pre   The strength must lay between 10 and 20.
	 * 		  | strength >10 && strength<= 20
	 * @param strength
	 * 		  the strength to calculate the capacity of
	 * @return 115,130,150,175,200,230,260,300,350 or 400 based 
	 * 		   on the strength, 115 is returned if the strength lays between
	 * 		   10.01 and 11 etc.
	 * 	       |  if (strength>10 && strength<=11)
	 * 		   | 	then result == 115
	 * 		   |  etc...
	 */
	@Model
	private double capacityStrengthBetween1020(double strength){
		if (strength>10 && strength<=11){return 115;}
		else if (strength>11 && strength<=12){return 130;}
		else if (strength>12 && strength<=13){return 150;}
		else if (strength>13 && strength<=14){return 175;}
		else if (strength>14 && strength<=15){return 200;}
		else if (strength>15 && strength<=16){return 230;}
		else if (strength>16 && strength<=17){return 260;}
		else if (strength>17 && strength<=18){return 300;}
		else if (strength>18 && strength<=19){return 350;}
		else if (strength>19 && strength<=20){return 400;}
		return 0;
	}
	
	/**
	 * Calculates the capacity if the strength is higher than 20.
	 * 
	 * @param strength
	 * 		  The strength to calculate the capacity.
	 * @pre The strength must be higher than 20.
	 * 	 	| strength > 20
	 * @return The capacity 4 times of the strength 10 units lower.
	 * 	       | let calc = strength-10
	 * 	       | let powerOf4 =  calc/10
	 * 		   | let strengthModTen = strength%10+10
	 * 	       | let baseResult = capacityStrengthBetween1020(strengthModTen)
	 * 		   | result == baseResult*(Math.pow(powerOf4, 4))
	 * @return Infinity if strength >=5097
	 * 		   | if (strength>=5097)
	 * 		   |	then result == Double.POSITIVE_INFINITY
	 */
	@Model
	private double capacityStrengthHigherThan20(double strength){
		double calc = strength-10;
		int powerOf4 = (int) calc/10;
		double strengthModTen = strength%10+10;
		double baseResult;
		if (strengthModTen == 10){
			baseResult = 100;
		}
		else{
			baseResult = capacityStrengthBetween1020(strengthModTen);
		}
		return baseResult*(Math.pow(4, powerOf4));
	}

	
	/************************************************
	 * Anchors
	 ************************************************/
	
	/**
	 * Checks whether a anchor point list is a valid one for a hero.
	 * 
	 * @return true if it a valid one for mobiles and has all the different 
	 * 		   anchor point types and if the total weight of all those items in
	 * 		   those anchorpoints is not greater than the capacity.
	 * 		   | result == super.isValidAnchorpointList(anchors) &&
	 * 		   |		   different(anchors) && 
	 * 		   | 		   totalWeight(anchors).compareTo(getCapacity(Unit.kg))<=0
	 */
	@Override
	public boolean isValidAnchorpointList(Anchorpoint[] anchors) {
		return super.isValidAnchorpointList(anchors) &&
				different(anchors) && 
				(totalWeight(anchors).compareTo(getCapacity(Unit.kg)))<=0; 
	}
	
	/**
	 * Checks whether each anchor point type is present in the given anchors and it occurs
	 * 		  once.
	 * 
	 * @param anchors
	 * 		  The anchors to check.
	 * @return false if the anchors is not effective
	 * 		   | if (anchors == null)
	 * 		   |	then result == false
	 * @return false if a type doesn't occur or twice or more.
	 * 		   | let different = new int[AnchorpointType.NbOfAnchorpointTypes()]
	 * 	       | for anchor in anchors
	 * 		   |	different[anchor.getAnchorpointType().ordinal()] += 1
	 * 		   | for i in different
	 * 		   |	if (different[i]!=1)
	 * 		   |		then result == false.
	 */
	@Model
	public boolean different(Anchorpoint[] anchors){
		if (anchors == null){
			return false;
		}
		int[] different = new int[AnchorpointType.NbOfAnchorpointTypes()];
		for (Anchorpoint anchor:anchors){
			different[anchor.getAnchorpointType().ordinal()] += 1;
		}
		for (int i:different){
			if (different[i]!=1){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Gets the total weight of a anchors.
	 * 
	 * @param anchors
	 * 		  The anchors to get the total weight of. 
	 * @return The total weight of al the items the mobile holds.
	 * 		   | let weight = Weight.kg_0
	 * 	       | for anchor in anchors
	 * 	       |	if (anchor.getAnchorpointType() != null && anchor.getItem() != null)
	 * 		   |		then weight.add(anchor.getItem().getWeight())
	 * 		   | result.equals(weight)
	 */
	@Model
	public Weight totalWeight(Anchorpoint[] anchors){
		Weight weight = Weight.kg_0;
		for (Anchorpoint anchor:anchors){
			 if (anchor.getAnchorpointType() != null && anchor.getItem() != null){
				 weight.add(anchor.getItem().getWeight());
			 }
		}
		return weight;
	}
	
	/**
	 * Generates an empty anchor points list.
	 * 
	 * @return an empty anchor points list with each anchor point once in the list.
	 * 		   | let anchors = new Anchorpoint[AnchorpointType.NbOfAnchorpointTypes()]
	 * 		   | for type in AnchorpointType.values()
	 * 		   |	 point= new Anchorpoint(type,null)
	 * 		   |	 anchors[type.ordinal()] = point
	 * 		   | result.equals(anchors)
	 */
	private Anchorpoint[] generateEmptyAnchors(){
		Anchorpoint[] anchors = new Anchorpoint[AnchorpointType.NbOfAnchorpointTypes()];
		for (AnchorpointType type:AnchorpointType.values()){
			Anchorpoint point= new Anchorpoint(type,null);
			anchors[type.ordinal()] = point;
		}
		
		return anchors;
	}
}
