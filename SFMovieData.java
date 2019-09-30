package project6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class is the main program
 * It reads file, prompts user input, and handles errors, print results
 * 
 * @author Ji Hwan Valentine Choi
 *
 */

public class SFMovieData {

	public static void main(String args[]) {
		
		//verify that the command line argument exists 
		if (args.length == 0) {
			System.err.println("Usage Error: the program expects file name as an argument.\n");
			System.exit(1);
		}
		
		//verify that command line argument contains a name of an existing file 
		File movieFile = new File(args[0]); 
		if (!movieFile.exists()){
			System.err.println("Error: the file "+movieFile.getAbsolutePath()+" does not exist.\n");
			System.exit(1);
		}
		if (!movieFile.canRead()){
			System.err.println("Error: the file "+movieFile.getAbsolutePath()+
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		//open the file for reading
		Scanner movieReader=null;
		
		try {
			movieReader = new Scanner (movieFile) ;
			
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file "+movieFile.getAbsolutePath()+
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		//read the content of the file and save the data in a list of movies
		MovieList list = new MovieList(); 
		ArrayList<String> line = null;
		Actor actorTmp1 = null;
		Actor actorTmp2 = null;
		Actor actorTmp3 = null;
		String location = null;
		String funFact = null;
		Location loc = null;
		Movie movieTmp = null;
		
		int row = 0;
		while (movieReader.hasNextLine()) {
			
			String eachLine =movieReader.nextLine();
			//split each row
			line = splitCSVLine(eachLine);
			try{if(row!=0) { //do not consider the first (header) row
				//store data when there are three actors
					if (line.size()==11) {
						//get actors from the line which is splited from the csv file, and store
						actorTmp1 = new Actor(line.get(8));
						actorTmp2 = new Actor(line.get(9));
						actorTmp3 = new Actor(line.get(10));
						//get location and fun fact, and store in a variable
						location = line.get(2);
						funFact=line.get(3);
						//if location is null, don't store in the whole movie list
						if (location==null || location.equals("")) {
							continue;
						}
						else
							loc = new Location(location, funFact);
						//make a movie using the variables as parameter
						movieTmp = new Movie(line.get(0),Integer.parseInt(line.get(1)),line.get(6),line.get(7),actorTmp1,actorTmp2,actorTmp3);
						//add location to the movie
						movieTmp.addLocation(loc);
						//if the tree is empty, add the movie
						if(list.isEmpty())
							list.add(movieTmp);
						//add location to existing movie, if there is duplicate movie
						else if(list.contains(movieTmp)) {
							list.get(movieTmp).addLocation(loc);
						}
						//add movie, if there is no duplicate movie in the tree
						else {
							list.add(movieTmp);
						}
					}
					//store data when there are one or two actors (similar with above)
					else if(line.size()==10) {
						if (line.get(9).equals("")){
							actorTmp1 = new Actor(line.get(8));
							actorTmp2 = null;
							actorTmp3 = null;
							location = line.get(2);
							funFact=line.get(3);
							if (location==null || location.equals(""))
								continue;
							else
								loc = new Location(location, funFact);
							movieTmp = new Movie(line.get(0),Integer.parseInt(line.get(1)),line.get(6),line.get(7),actorTmp1,actorTmp2,actorTmp3);
							movieTmp.addLocation(loc);
							if(list.isEmpty())
								list.add(movieTmp);
							else if(list.contains(movieTmp)) {
								list.get(movieTmp).addLocation(loc);
							}
							else {
								list.add(movieTmp);
							}
						}
						else {
							actorTmp1 = new Actor(line.get(8));
							actorTmp2 = new Actor(line.get(9));
							actorTmp3 = null;
							location = line.get(2);
							funFact=line.get(3);
							if (location==null || location.equals(""))
								continue;
							else
								loc = new Location(location, funFact);
							movieTmp = new Movie(line.get(0),Integer.parseInt(line.get(1)),line.get(6),line.get(7),actorTmp1,actorTmp2,actorTmp3);
							movieTmp.addLocation(loc);
							if(list.isEmpty())
								list.add(movieTmp);
							else if(list.contains(movieTmp)) {
								list.get(movieTmp).addLocation(loc);
							}
							else {
								list.add(movieTmp);
							}
						}
					}
				}
				
				row++;
			}catch(IllegalArgumentException e) {}
			catch(IndexOutOfBoundsException i) {continue;}
			catch(NoSuchElementException ex){
			//caused by an incomplete or miss-formatted line in the input file	
				continue; 	
			}
		}
		
		//interactive mode
		
		//open scanner for user input
		Scanner userIn = new Scanner(System.in);
		String userValue;
		
		System.out.println("Search the database by matching keywords to titles or actor names.");
		System.out.println("To search for matching titles, enter");
		System.out.println("title KEYWORD");
		System.out.println("To search for matching actor names, enter");
		System.out.println("actor KEYWORD");
		System.out.println("To finish the program, enter");
		System.out.println("quit");
		System.out.println("");
		System.out.println("");
		
		do {//iterate through the program without termination until user types "quit"
			MovieList userSel;

			System.out.println("");
			System.out.println("Enter your search query:");
			System.out.println("");

				userValue= userIn.nextLine();
				//verifies the user input if it followed the instruction above
				String userInput= entryVerifier(userValue);
			
			switch(userInput) { 
			
			//search by title
			case "title" :
				String userValue1=capitalization(userValue.replace("title ",""));
				userSel = list.getMatchingTitles(userValue1);
				System.out.println("");
				//Empty list due to no matching data
				if (userSel==null) {
					System.out.println("No matches found.");
				}
				else {
					//print the list of movies which is searched
					Iterator<Movie> result = userSel.iterator();
					while(result.hasNext()) {
						System.out.println(result.next());
					}
				}
				break;
				
			//search by actor
			case "actor" :
				String userValue2=capitalization(userValue.replace("actor ",""));
				//iterate through the whole movie list to find matching movie
				userSel = list.getMatchingActor(userValue2);
				System.out.println("");
				//Empty list due to no matching data
				if (userSel==null) {
					System.out.println("No matches found.");
				}
				else {
					//print the list of movies which is searched
					Iterator<Movie> result = userSel.iterator();
					while(result.hasNext()) {
						System.out.println(result.next());
					}
				}
				break;
				
			//program termination
			case "quit":
				userIn.close();
				System.exit(1);
				
			//error message when the user enters wrong input
			case "wrong" :
				System.err.println("Wrong entry. Try again");
				break;
				
			} 
		}while(userValue!="wrong");
	}
	
	/**
	 * Capitalizes the parameter 
	 * 
	 * @param word a string to capitalize 
	 * 
	 * @return Capitalized word
	 */
	
	public static String capitalization (String word) {
		String wordArray[]=word.split("\\s");
		String wordSum="";
		for (String i:wordArray) {
			String head=i.substring(0,1);
			String tails=i.substring(1);
			wordSum+=head.toUpperCase()+tails+" ";
		}
		return wordSum.trim();
	}
	
	/**
	 * Verifies if the user entered a input within the possible selections
	 * 
	 * @param userInTmp the user input
	 * 
	 * @return returns selected input and "wrong" when input entered is not correct
	 */
	public static String entryVerifier(String userInTmp){
		
		String userInLower =  userInTmp.toLowerCase();
		
		if (userInLower.contains("actor")) {
			return "actor";
		}else if(userInLower.contains("title")) {
			return "title";
		}else if(userInLower.contains("quit")) {
			return "quit";
		}else {
			return "wrong";
		}
	}

	/*
	 * Splits the given line of CSV file according to commas and double quotes
	 * 
	 * @author Joanna Klukowska
	 * 
	 * @param textLine a line of text to be passed
	 * 
	 * @return an ArrayList Object containing all individual entries found on that line
	 */
	public static ArrayList<String> splitCSVLine(String textLine) {
		if (textLine == null) return null;
			ArrayList<String> entries = new ArrayList<String>();
			int lineLength = textLine.length();
			StringBuffer nextWord = new StringBuffer();
			char nextChar;
			boolean insideQuotes = false;
			boolean insideEntry = false;
			
			//iterate over characters in the textLine
			for (int i = 0; i <lineLength; i++) {
				nextChar = textLine.charAt(i);
				
				//handle smart quotes as well as regular quotes
				if (nextChar == '"' || nextChar == '\u201c' || nextChar == '\u201d') {
					//change insideQuotes flag when nextChar is a quote
					if(insideQuotes) {
						insideQuotes = false;
						insideEntry = false;
					}else {
						insideQuotes = true;
						insideEntry = true;
					}
				} else if (Character.isWhitespace(nextChar)) {
				  if (insideQuotes || insideEntry) {
				  // add it to the current entry
					  nextWord.append(nextChar);
				  }else { //skip all space between entries
					  continue;
				  }
				} else if (nextChar == ',') {
					  if (insideQuotes) { //comma inside an entry
						  nextWord.append(nextChar);
					  }	  else { //end of entry found
						  	insideEntry = false;
						  	entries.add(nextWord.toString());
						  	nextWord = new StringBuffer();
					  }
				}	else {
					//add all other characters to the nextWord
					nextWord.append(nextChar);
					insideEntry = true;
				}
			}
			//add the last word (assuming not empty)
			//trim the white space before adding to the list
			if (!nextWord.toString().equals("")) {
				entries.add(nextWord.toString().trim());
			}
			
			return entries;
			 
	}
}

