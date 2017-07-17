package rpg.inventory;
import be.kuleuven.cs.som.annotate.*;
import rpg.Mobile;
import rpg.utility.IDGenerator;
import rpg.value.Unit;
import rpg.value.Weight;

public class Backpack extends Container {

	protected Backpack(Weight weight, int value, Mobile holder, Weight capacity) {
		super(weight, value, holder, capacity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItem(Item item) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Weight getWeight(Unit unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Weight getWeight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected IDGenerator getIDGenerator() {
		// TODO Auto-generated method stub
		return null;
	}



}
