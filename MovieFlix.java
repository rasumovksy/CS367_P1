///////////////////////////////////////////////////////////////////////////////
// 
// Title:            MovieFlix
// Files:            MovieFlix.java, MovieDatabase.java, Movie.java
// Semester:         CS302 Fall 2014
//
// Author:           Andrew Hard
// Email:            hard@wisc.edu
// CS Login:         hard
// Lecturer's Name:  Jim Skrentny
// Lab Section:      LEC-002 (77632)
//
///////////////////////////////////////////////////////////////////////////////
//
// Pair Partner:     Ming Chew
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
///////////////////////////////////////////////////////////////////////////////

import java.util.*;
import java.io.*;

/**
 * The MovieFlix class provides an interface for
 * adding and retrieving data from a MovieDatabase.
 *
 * @author Ming Chew
 * @author Andrew Hard
 */
public class MovieFlix {
    
    // public or private?
    /**
     * Format a string with the proper capitalization style.
     *
     * @param name The string to format.
     * @return The formatted string.
     */
    private static String StringStyle( String name ) {
	name = name.toLowerCase();
	String[] split = name.split(" ");
	name = "";
	for( int i = 0; i < split.length; i++ ) {
	    split[i] = split[i].substring(0,1).toUpperCase()+split[i].substring(1);
	    name = name + split[i] + " ";
	}
	name = name.trim();
	return name;
    }
    
    /**
     * Search for a String in a list of Strings.
     *
     * @param name The string to find in the list of strings.
     * @param textList The list of strings to search.
     * @return true iff the string appears in the list.
     */
    private static boolean Compare( String name, List<String> textList ) {
	Iterator<String> textItr = textList.iterator();// use iterator not for loop
	while ( textItr.hasNext() ) {
	    if ( name.equals( textItr.next() ) ) {
		return true;
	    }
	}
	return false;
    }
    
    // Merged PrintListNoLine and PrintList    
    
    /**
     * Print the entries in a list one per line, separated by a comma
     *
     * @param textList The list of strings to print.
     * @param newline Boolean to specify whether or not to print new line.
     */
    public static void PrintList( List<String> textList, Boolean newline ) {
	Iterator<String> textItr = textList.iterator();// use iterator not for loop
	int currIndex = 0;
	while ( textItr.hasNext() ) {
	    currIndex++;
	    if ( currIndex == textList.size() ) {// should this be -1?
		if( newline ) {
		    System.out.println( textItr.next() );
		}
		else {
		    System.out.print( textItr.next() );
		}
	    }
	    else {
		System.out.print( textItr.next() + ", " );
	    }
	}
    }
    
    /**
     * The main method creates an instance of MovieDatabase
     * using the provided input file, then prompts the user
     * for options to add or retrieve movie and actor data.
     *
     * @param args The actor and movie input data file.
     */
    public static void main( String[] args ) throws FileNotFoundException {
	
      	// Check whether one command-line argument given:
	if ( args.length != 1 ) {
	    System.out.println("Usage: java MovieFlix FileName");
	    System.exit(0);
	}
	
	// Check whether input file exists and is readable:
	File dataFile = new File(args[0]);
	if ( !dataFile.exists() ) {
	    System.out.println("Error: Cannot access input file");
	    System.exit(0);
	}
	////
	////
	////
	// Create instances of main variables:
	MovieDatabase movieDb = new MovieDatabase();
	List<String> storageList = new ArrayList<String>();
	
	// Load the data from the input file:
	// no need to use else statement:
	Scanner inputFile = new Scanner(dataFile);
	while ( inputFile.hasNext() ) {
	    String currLine = inputFile.nextLine();
	    String delims = "[,]+";
	    String[] splitLine = currLine.split(delims);
	    String actorName = StringStyle(splitLine[0].trim());
	    for ( int i = 1; i < splitLine.length; i++ ) {
		String movieName = StringStyle(splitLine[i].trim());
		movieDb.addMovie(movieName);
		movieDb.addActor(actorName, movieName);
	    }
	}
	inputFile.close();
	
	// for console input:
	Scanner stdin = new Scanner(System.in);
	
        boolean done = false;
        while ( !done ) {
            System.out.print("Enter option (cdprswx): ");
            String input = stdin.nextLine();

            //only do something if the user enters at least one character
            if ( input.length() > 0 ) {
                char choice = input.charAt(0); //strip off option character
                String remainder = "";         //will hold the remaining input
                if ( input.length() > 1 ) {    //if there is an argument
                    //trim off any leading or trailing spaces
                    remainder = StringStyle(input.substring(1).trim()); 
		    
                    switch (choice) { //the commands that have arguments
			
                    case 'c':
			storageList = movieDb.getCast(remainder);
			if ( storageList == null ) {
			    System.out.println("movie not found");
			}
			else {
			    PrintList( storageList, true );
			}
                        break;
			
                    case 'p':
			storageList = movieDb.getMovies(remainder);
			if ( storageList == null || storageList.size() == 0 ) {
			    System.out.println("actor not found");//should be actor not found
			}
			else {
			    PrintList( storageList, true );
			}
			break;
    
                    case 'r':
			if ( movieDb.removeMovie(remainder) ) {// no need to create Boolean
			    System.out.println("movie removed");
			}
			else {
			    System.out.println("movie not found");
			}
                        break;
    
                    case 's':
                        // The following code reads in a comma-separated sequence 
                        // of strings. If there are exactly two strings in the 
                        // sequence, the strings are assigned to name1 and name2.
                        // Otherwise, an error message is printed.
                        String[] tokens = remainder.split("[,]+");
                        if ( tokens.length != 2 ) {
                            System.out.println("need to provide exactly two names");
                        }
                        else {
                            String name1 = StringStyle(tokens[0].trim());
                            String name2 = StringStyle(tokens[1].trim());
                            
			    // Store the common movies:
			    List<String> currMovieList = new ArrayList<String>();
			    
			    // Get movies associated with 1st actor:
			    storageList = movieDb.getMovies(name1);
			    Iterator<String> currDbItr = storageList.iterator();
			    while ( currDbItr.hasNext() ) {
				String currMovie = currDbItr.next();
				// Then check whether 2nd actor also cast:
				if ( movieDb.isCast( name2, currMovie ) ) {
				    currMovieList.add(currMovie);
				}
			    }
			    if ( currMovieList.isEmpty() ) {
				System.out.println("none");
			    }
			    else {
				PrintList( currMovieList, true );
			    }
			}
                        break;
			
                    case 'w':
			if ( movieDb.removeActor(remainder) ) {
			    System.out.printf( "%s withdrawn from all movies\n", remainder );
			}
			else {
			    System.out.println("actor not found");
			}
                        break;
			
                    default: //ignore invalid commands
                        System.out.println("Incorrect command.");
                        break;
                    
                    } // end switch
                } // end if
                else { //if there is no argument
                    switch (choice) { //the commands without arguments
                    
                    case 'd': 
                        // TODO to implement this option ***
			List<String> currMovieList = new ArrayList<String>();
			List<String> currActorList = new ArrayList<String>();
			int uniqueActors = 0;
			int minActors = 0;
			int maxActors = 0;
			int totalActors = 0;
			
			int minMovies = 0;
			int maxMovies = 0;
			int totalMovies = 0;
			
			List<String> largestCast = new ArrayList<String>();
			List<String> smallestCast = new ArrayList<String>();
			
			// Why don't we just use the MovieDatabase size() for the number of movies, and then loop through the movies to determine the number of actors...
			// loop for number of unique actors:
			Iterator<Movie> movieItr = movieDb.iterator();//<-- THIS IS IMPORTANT
			while ( movieItr.hasNext() ) {
			    Movie currMovie = movieItr.next();
			    if ( !Compare( currMovie.getTitle(), currMovieList ) ) {
				currMovieList.add( currMovie.getTitle() );
			    }
			    
			    List<String> currCast = movieDb.getCast( currMovie.getTitle() );
			    Iterator<String> castItr = currCast.iterator();
			    while ( castItr.hasNext() ) {
				String currActor = castItr.next();
				if ( !Compare( currActor, currActorList ) ) {
				    uniqueActors++;
				    currActorList.add(currActor);
				}
			    }
			    int actorsPerMovie = currCast.size();
			    totalActors += actorsPerMovie;
			    
			    // Test whether currMovie has the fewest actors:
			    if ( actorsPerMovie <= minActors || minActors == 0 ) {
				if ( actorsPerMovie == minActors ) {
				    smallestCast.add( currMovie.getTitle() );
				}
				else {
				    smallestCast = new ArrayList<String>();
				    smallestCast.add( currMovie.getTitle() );
				}
				minActors = actorsPerMovie;
			    }
			    // Test whether currMovie has the most actors:
			    if ( actorsPerMovie >= maxActors || maxActors == 0 ) {
				if ( actorsPerMovie == maxActors ) {
				    largestCast.add( currMovie.getTitle() );
				}
				else {
				    largestCast = new ArrayList<String>();
				    largestCast.add( currMovie.getTitle() );
				}
				maxActors = actorsPerMovie;
			    }
			    
			}
			
			// Loop over unique actors, check if they have most or least credits:
		        Iterator<String> actorItr = currActorList.iterator();
			while ( actorItr.hasNext() ) {
			    currMovieList = movieDb.getMovies( actorItr.next() );
			    int moviesPerActor = currMovieList.size();
			    totalMovies += moviesPerActor;
			    if ( moviesPerActor <= minMovies || minMovies == 0 ) {
				minMovies = moviesPerActor;
			    }
			    if ( moviesPerActor >= maxMovies || maxMovies == 0 ) {
				maxMovies = moviesPerActor;
			    }
			}
			
			// Print display to screen:
			System.out.printf( "Movies: %d, Actors: %d\n", movieDb.size(), uniqueActors );
			double avgActors = ((double)totalActors / ((double)movieDb.size()));
			double avgMovies = ((double)totalMovies / ((double)currActorList.size()));
			System.out.printf( "# of actors/movie: most %d, least %d, average %d\n", maxActors, minActors, Math.round(avgActors) );
			System.out.printf( "# of movies/actor: most %d, least %d, average %d\n", maxMovies, minMovies, Math.round(avgMovies) );
			
			System.out.print("Largest Cast: ");
                    	PrintList( largestCast, false );
			System.out.printf(" [%d]\n",maxActors);
			System.out.print("Smallest Cast: ");
                    	PrintList( smallestCast, false );
                    	System.out.printf(" [%d]\n",minActors);
			
                        break;
			
                    case 'x':
                        done = true;
			stdin.close();
                        System.out.println("exit");
                        break;
                        
                    default:  //a command with no argument
                        System.out.println("Incorrect command.");
                        break;
                    } //end switch
                } //end else  
           } //end if
        } //end while
    } //end main

}
