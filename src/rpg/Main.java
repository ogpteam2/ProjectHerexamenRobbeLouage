package rpg;

import java.util.concurrent.ThreadLocalRandom;
import rpg.inventory.Backpack;
import rpg.inventory.Ducat;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.AnchorpointType;
import rpg.value.Unit;
import rpg.value.Weight;

public class Main {
	
	public static void main(String[] args){
		Hero hero = new Hero("James",139,25);
		initializeHeroWeapons(hero);
		System.out.println("Total value of the hero is: " + ((Integer)hero.getTotalValue()).toString() + " Ducats");
		Monster monster = new Monster("Jared",139,30,Mobile.generateAllAnchorpoints(),new Weapon(null,50));
		initializeMonsterWeapons(monster);
		System.out.println("Total value of the monster is: " + ((Integer)monster.getTotalValue()).toString() + " Ducats");
		int random = ThreadLocalRandom.current().nextInt(0,1+1);
		System.out.println("BATTLE");
		while (hero.getCurrentHitpoints()>0 && monster.getCurrentHitpoints()>0){
			if (random%2==0)
				hero.hit(monster);
			else
				monster.hit(hero);
			random++;
		}
		System.out.println("Total value of the hero is: " + ((Integer)hero.getTotalValue()).toString() + " Ducats");
		System.out.println("Total value of the monster is: " + ((Integer)monster.getTotalValue()).toString() + " Ducats");
		if (hero.getCurrentHitpoints()==0)
			System.out.println("The winner is the monster.");
		else
			System.out.println("The winnner is the hero.");
		
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
		monster.addItem(new Weapon(new Weight(2,Unit.kg),40));
		monster.addItem(new Purse(new Weight(200,Unit.g),new Weight(2,Unit.kg)));
		monster.addItem(new Backpack(new Weight(500,Unit.g),5,new Weight(9,Unit.kg)));
	}	
}