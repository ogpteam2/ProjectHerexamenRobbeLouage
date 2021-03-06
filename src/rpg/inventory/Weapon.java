package rpg.inventory;

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
 * 
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
	 * @param weight
	 * 		  The weight of the item.
	 * @param value
	 * 		  The value of the item.
	 * @param holder
	 * 		  The holder of the item.
	 * @param damage
	 * 		  The damage of the weapon.
	 * @effect the weapon is an item with given weigh,value and holder.
	 * 		   | super(weight,value,holder)
	 * @post the damage is set to the given damage.
	 * 	       | new.getDamage().equals(damage)
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
	 * @param weight
	 * 		  The weight of the item.
	 * @param value
	 * 		  The value of the item.
	 * @param damage
	 * 		  The damage of the weapon.
	 * @effect the weapon is initialized with given weight,value,damage.
	 * 		   | this(weight,value,holder,damage)
	 */
	@Raw
	public Weapon(Weight weight,int value,int damage){
		this(weight,value,null,damage);
	}
	
	/**
	 * Initializes a weapon with a weight,damage,holder.
	 * 
	 * @param weight
	 * 		  The weight of the item.
	 * @param holder
	 * 		  The holder of the item.
	 * @param damage
	 * 		  The damage of the weapon.
	 * @effect the weapon is an initialized with given weight,holder,damage.
	 * 		   | this(weight,calculateValue(damage),holder,damage)
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
	public static int getLowerboundary(){
		return lowerboundDamage;
	}
	
	/**
	 * Returns the lower boundary for the damage.
	 */
	@Raw @Basic
	public static int getUpperboundary(){
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
	public static boolean isValidDamage(int amount){
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
	/**
	 * Initializes the upper bound damage.
	 */
	static {
			upperboundDamage = 100;
	}
	
	/************************************************
	 * Value
	 ************************************************/
	
	/**
	 * Checks whether a amount is a valid value amount.
	 * 
	 * @param amount
	 * 		  The amount to check.
	 * @return true if the amount lays between zero and 200.
	 * 		   | result == amount>=0 && amount<200
	 */
	public static boolean isValidValue(int amount){
		return Item.isValidValue(amount) && amount<200;
	}

}
