package rpg;
import be.kuleuven.cs.som.annotate.*;
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
	 */
	@Raw
	public Hero(String name, long hitpoints) throws IllegalArgumentException {
		super(name,hitpoints);
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


	

	
	
	
	
	
	
	
	
	
	
	
}
