package project6;

import java.util.Collections;
import java.util.Iterator;

/**
 * This class provides methods that finds the matching data and also provides a tree to store the data 
 * searched by title and by actors
 * @author Ji Hwan Valentine Choi
 *
 */
public class MovieList extends BST<Movie>{
	
	/**
	 * Constructor for class MovieList 
	 */
	public MovieList() {
		super();
	}

	/**
	 * Search if there is matching movie data by title
	 * @param keyword that the user entered to compare
	 * @return a tree of movies that are matching
	 * @return null when no movies stored in the tree
	 */
	public MovieList getMatchingTitles (String keyword) {
		
		MovieList movieList= new MovieList();
		
		//check if the user input is null or an empty string
		if (keyword == null|| keyword.equals(""))
			return null;
		
		Iterator<Movie> iter= this.iterator();
		
		//iterates through the whole movie list and find the matching data
		while(iter.hasNext()) {
			Movie movieTmp = iter.next();
			String titleTmp = movieTmp.getTitle().toLowerCase();
			
			//if there is a matching data, store it in the tree to return 
			if(titleTmp.contains(keyword.toLowerCase()))
				movieList.add(movieTmp);
		}
		//if there is no movie stored, return null
		if(movieList.size()==0)
			return null;
		return movieList;
	}
	
	/**
	 * Search if there is matching movie data by title
	 * @param keyword that the user entered to compare
	 * @return a tree of movies that are matching
	 * @return null when no movies stored in the tree
	 */
	public MovieList getMatchingActor (String keyword) {
		MovieList movieList = new MovieList();
		
		//check if the user input is null or an empty string
		if (keyword == null|| keyword.equals(""))
			return null;

		Iterator<Movie> iter= iterator();
		
		//iterates through the whole movie list and find the matching data
		while(iter.hasNext()) {
			Movie movieTmp = iter.next();
			String actor1Tmp = movieTmp.getActor1().getName().toLowerCase();
			
			//if there is a matching data, store it in the tree to return 
			if(actor1Tmp.contains(keyword.toLowerCase()))
				movieList.add(movieTmp);
			
			else if (movieTmp.getActor2() != null && movieTmp.getActor2().getName().toLowerCase().contains(keyword.toLowerCase()))
				movieList.add(movieTmp);
			
			else if (movieTmp.getActor3() != null && movieTmp.getActor3().getName().toLowerCase().contains(keyword.toLowerCase()))
				movieList.add(movieTmp);
		}
		//if there is no movie stored, return null
		if(movieList.size()==0)
			return null;
		return movieList;
	}
	
	/**
	 * toString method that stacks the matching movies' title and return
	 * @return list of titles that are stored in the matching movie list
	 */
	public String toString() {
		String movieNames="";
		
		Iterator<Movie> iter = this.iterator();
		
		while(iter.hasNext()) {
			movieNames+=iter.next().getTitle();
			if(iter.hasNext())
				movieNames+="; ";
		}
		return movieNames;
	}
	
}
