package rpg;
import be.kuleuven.cs.som.annotate.*;
import rpg.value.Unit;
import rpg.value.Weight;

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
	 * 		  The raw strength of the Hero.
	 */
	@Raw
	public Hero(String name, long hitpoints, double strength) throws IllegalArgumentException {
		super(name,hitpoints,strength);
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
	 * Capcity: total
	 ************************************************/
	
	/**
	 * Calculates the capacity based on the raw strength in a given unit.
	 * 
	 * @param unit
	 * 		  The unit in which the capacity is expresssed.
	 * @return weight with numeral zero and given unit if the raw strength is less than 1.
	 * 		   | if (getRawStrength() < 1)
	 * 		   |	then result.equals(Weight.kg_0.toUnit(unit))
	 * @return 
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
			capacity = calculateCapacity(strength-10,unit).getNumeral();
			capacity *= 4;
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
	

	
	
	
	
	
}
