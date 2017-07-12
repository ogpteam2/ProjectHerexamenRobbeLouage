package rpg.inventory;
import be.kuleuven.cs.som.annotate.*;

abstract public class Item {

	protected Item(long id){
		this.id = id;
	}
	
	public long getId(){
		return this.id;
	}
	
	private long id;
}
