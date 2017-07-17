package rpg.inventory;
import java.util.concurrent.ThreadLocalRandom;

import be.kuleuven.cs.som.annotate.*;
import rpg.Mobile;
import rpg.utility.IDGenerator;
import rpg.utility.WeaponIDGenerator;
import rpg.value.Weight;

/**
 * A class of weapons with ID , given weight, value and holder.
 * 
 * @invar Each weapon must have a valid damage.
 * 		  | isValidDamage(getDamage())
 * @author Robbe
 * @version 1.0
 */
public class Weapon extends Item {

	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initializes a weapon with a weight,value,holder.
	 * 
	 * @effect the weapon is an item with given weigh,value and holder.
	 * 		   | super(weight,value,holder)
	 * @post the damage is set to the given damage.
	 * 
	 */
	@Raw
	public Weapon(Weight weight,int value,Mobile holder, int damage) {
		super(weight,value,holder);
		this.damage = damage;
		
	}
	
	/**
	 * Initializes a weapon with a weight,value,holder.
	 * 
	 * @effect the weapon is an item with given weight,value,damage.
	 * 		   | super(weight,value,holder,damage)
	 */
	@Raw
	public Weapon(Weight weight,int value,int damage){
		this(weight,value,null,damage);
	}
	
	
	
	
	/**
	 * Initializes a weapon with a weight,damage,holder.
	 * 
	 * @effect the weapon is an item with given weight,holder,damage.
	 * 		   | super(weight,calculateValue(damage),holder,damage)
	 */
	@Raw
	public Weapon(Weight weight,Mobile holder,int damage){
		this(weight,calculateValue(damage),holder,damage);
	}
	
	/**
	 * Initializes a weapon with given weight and damage.
	 * 
	 * @param weight
	 * 		  the weight of the weapon
	 * @param damage
	 * 		  The damage of the weapon.
	 * @effect initializes the weapon with given weight and damage.
	 * 		   | this(weight,calculateValue(damage),null,damage)
	 */
	public Weapon(Weight weight,int damage){
		this(weight,calculateValue(damage),null,damage);
	}
	
	/************************************************
	 * ID
	 ************************************************/
	
	/**
	 * Returns the generator for weapons.
	 */
	@Raw @Basic @Override
	protected IDGenerator getIDGenerator() {
		return generator;
	}
	
	/**
	 * A variable referencing the generator for this class that generates IDs.
	 */
	private static WeaponIDGenerator generator = new WeaponIDGenerator();
	
	/************************************************
	 * Damage: nominal
	 ************************************************/
	
	/**
	 * Returns the damage.
	 */
	@Raw @Basic
	public int getDamage(){
		return this.damage;
	}
	
	/**
	 * Returns the lower boundary for the damage.
	 */
	@Raw @Basic
	public int getLowerboundary(){
		return lowerboundDamage;
	}
	
	/**
	 * Returns the lower boundary for the damage.
	 */
	@Raw @Basic
	public int getUpperboundary(){
		return upperboundDamage;
	}
	
	/**
	 * Checks whether a given amount is a valid damage.
	 * 
	 * @param amount
	 * 		  the amount to check.
	 * @return true if the amount lays between the maximum and miminmum boundary,
	 * 		   and is a multiple by 7.
	 * 		   | result == amount>=getLowerboundary() && amount<=getUpperboundary() 
	 *		   |	&& amount%7==0
	 */
	public boolean isValidDamage(int amount){
		return amount>=getLowerboundary() && amount<=getUpperboundary() 
				&& amount%7==0;
	}
	
	/**
	 * Calculates the value based on the damage.
	 * 
	 * @param damage
	 * 		  The damage to calculate the value of.
	 * @return the value.
	 * 		   | if (damage*2>=200)
	 * 		   |	then result == 200
	 * 		   | else
	 * 		   |	result == damage*2
	 */
	private static int calculateValue(int damage){
		if (damage*2>=200){
			return 200;
		}
		else{
			return damage*2;
		}
	}
	
	/**
	 * Sets the damage to the given amount.
	 * 
	 * @param amount
	 * 		  the new amount.
	 * @pre   the given amount must be a legal one.
	 * 		  | isValidDamage(amount)
	 * @post the damage is set to the given amount
	 * 		 | new.getDamage() == amount
	 * @effect the new value is also set.
	 * 		   | setValue(calculateValue(getDamage()))
	 */
	public void setDamage(int amount){
		this.damage = amount;
		setValue(calculateValue(getDamage()));
	}
	
	/**
	 * generates a random number for the upperbound.
	 * 
	 * @return a random number between 0 and int.MAX_VALUE
	 * 		   | result == ThreadLocalRandom.current().nextInt(0,Integer.MAX_VALUE)
	 */
	private static int upperboundRandom(){
		return ThreadLocalRandom.current().nextInt(0,Integer.MAX_VALUE);
	}
	
	/**
	 * A variable referencing the damage of the weapon
	 */
	private int damage;
	/**
	 * A variable referencing the lower bound for the damage.
	 */
	private final static int lowerboundDamage = 1;
	/**
	 * A variable referencing the upper bound for the damage.
	 */
	private final static int upperboundDamage;
	
	static {
		if (true){
			upperboundDamage = 100;
		}
		else{
			upperboundDamage = upperboundRandom();
			}
	}
	
	/************************************************
	 * Value
	 ************************************************/
	
	/**
	 * Checks whether a amount is a valid valueamount.
	 * 
	 * @param amount
	 * 		  The amount to check.
	 * @return true if the amount lays betwenn zero and 200.
	 * 		   | result == super.canHaveAsValue(amount) && amount<200
	 */
	@Override
	public boolean canHaveAsValue(int amount){
		return super.canHaveAsValue(amount) && amount<200;
	}

	
	
}
