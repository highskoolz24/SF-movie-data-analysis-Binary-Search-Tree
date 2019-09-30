package project6;

/**
 * This class stores information about a actor object
 * Stores the name of the actor
 * @author Ji Hwan Valentine Choi
 *
 */

public class Actor {
	
	private String name;
	
	/**
	 * Constructor for class Actor
	 * @param name name of the actor
	 */
	public Actor(String name) throws IllegalArgumentException {
		//check if the parameter is null
		if (name==null || name.equals(""))
			throw new IllegalArgumentException();
		else
			this.name=name;
	}
	
	/**
	 * Enables other classes to get private variable 'name'
	 * @return name name of the actor
	 */
	public String getName() {
		return name;
	}

	/**
	 * Simple representation of actor class in string
	 * @return name name of the actor
	 */
	public String toString () {
		return name;
		
	}
}
