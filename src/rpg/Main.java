package rpg;

import rpg.inventory.Anchorpoint;
import rpg.inventory.Backpack;
import rpg.inventory.Ducat;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

public class Main {
	
	public static void main(String[] args){
		Hero hero = new Hero("James",97,25);
		initializeHeroWeapons(hero);
		System.out.println("Total value of the hero is: " + ((Integer)hero.getTotalValue()).toString() + " Ducats");
		Monster monster = new Monster("Jared");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static void initializeHeroWeapons(Mobile hero){
		Weapon weapon1 = new Weapon(new Weight(1.8,Unit.kg),49);
		Weapon weapon2 = new Weapon(new Weight(1.4,Unit.kg),28);
		Purse purse1 = new Purse(new Weight(100,Unit.g),new Weight(1,Unit.kg));
		Ducat ducat1 = new Ducat();
		purse1.addItem(ducat1);
		Backpack backpack1 = new Backpack(new Weight(500,Unit.g),11,new Weight(5,Unit.kg));
		backpack1.addItem(new Ducat());
		hero.addItemAt(AnchorpointType.LEFT,weapon1);
		hero.addItemAt(AnchorpointType.RIGHT,weapon2);
		hero.addItem(purse1);
		hero.addItem(backpack1);
		hero.addItem(new Ducat());
	}
	
	private static void initializeMonsterWeapons(Mobile monster){
		monster.addItem(new Weapon(new Weight(1,Unit.kg),20));
		monster.addItem(new Ducat());
		monster.addItem(new Purse(new Weight(200,Unit.g),new Weight(2,Unit.kg)));
		monster.addItem(new Backpack(new Weight(500,Unit.g),5,new Weight(9,Unit.kg)));
		monster.addItem(new Weapon(new Weight(2,Unit.kg),40));
	}
}
