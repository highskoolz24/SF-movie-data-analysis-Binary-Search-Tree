package project6;

/**
 * This class stores information about a location object 
 * stores location and fun fact
 * @author Ji Hwan Valentine Choi
 *
 */

public class Location {
	
	private String location;
	private String funFact;
	
	/**
	 * Constructor for class Location
	 * @param location name of the location
	 * @param funFact  fun fact related to the location
	 */
	public Location(String location, String funFact) throws IllegalArgumentException{
		
		//check if the location is null
		if (location == null || location.equals("")) 
			throw new IllegalArgumentException();
		else
			this.location=location;
		
		//check if the fun fact is null
		if (funFact==null || funFact.equals(""))
			this.funFact=null;
		else
			this.funFact=funFact;
	}
	
	/**
	 * Enables other classes to get private variable 'location'
	 * @return location name of the location
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Enables other classes to get private variable 'funFact'
	 * @return funFact funFact related to the location in parenthesis
	 */
	public String getFunFact() {
		if (funFact==null)
			return "";
		if (funFact.length()==0 || funFact.equals(""))
			return "";
		return " (" + funFact + ")";
	}
}
