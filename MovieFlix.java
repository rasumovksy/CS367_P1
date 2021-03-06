////////////////////////////////////////////////////////////////////////////////
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
// Pair Partner:     Wayne Chew
// Email:            mchew2@wisc.edu
// CS Login:         mchew
// Lecturer's Name:  Jim Skrentny
// Lab Section:      LEC-001 (77631)
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
    
    /**
     * Format a string with the proper capitalization style.
     *
     * @param name The string to format.
     * @return The formatted string.
     */
    private static String StringStyle( String name ) {
	name = name.toLowerCase();	//convert name to lower case
	String[] split = name.split(" "); //split the words
	name = "";

	//loop through the string and capitalize the first letter
	for( int i = 0; i < split.length; i++ ) {
	    split[i] = split[i].substring(0,1)
		.toUpperCase()+split[i].substring(1);
	    name = name + split[i] + " ";
	}//end for loop

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
	Iterator<String> textItr = textList.iterator();
	while ( textItr.hasNext() ) {
	    if ( name.equals( textItr.next() ) ) {
		return true;
	    }
	}
	return false;
    }
        
    /**
     * Print the entries in a list one per line, separated by a comma
     *
     * @param textList The list of strings to print.
     * @param newline Boolean to specify whether or not to print new line.
     */
    public static void PrintList( List<String> textList, Boolean newline ) {
	Iterator<String> textItr = textList.iterator();
	int currIndex = 0;
	while ( textItr.hasNext() ) {
	    currIndex++;
	    if ( currIndex == textList.size() ) {
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
	// Create instances of primary reused variables:
	// StorageList to temporarily store returned List<String>
	MovieDatabase movieDb = new MovieDatabase();
	List<String> storageList = new ArrayList<String>();
	
	// Load the data from the input file:
	Scanner inputFile = new Scanner(dataFile);
	
	//loop through the input file
	while ( inputFile.hasNext() ) {
		//Split the file line by line
	    String currLine = inputFile.nextLine();
	    //Split the line into strings at the comma
	    String delims = "[,]+";
	    String[] splitLine = currLine.split(delims);
	    //Seperate and format strings into actor and movies 
	    String actorName = StringStyle(splitLine[0].trim());
	    for ( int i = 1; i < splitLine.length; i++ ) {
		String movieName = StringStyle(splitLine[i].trim());
		//add movies and actors into database
		movieDb.addMovie(movieName);
		movieDb.addActor(actorName, movieName);
	    }
	}//end while loop
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
                String remainder = "";         //holds the remaining input
                if ( input.length() > 1 ) {    //if there is an argument
                    //trim off any leading or trailing spaces
                    remainder = StringStyle(input.substring(1).trim()); 
		    
                    switch (choice) { //the commands that have arguments
			
                    case 'c':// display cast for given movie title
			storageList = movieDb.getCast(remainder);
			//print out the cast unless storageList is null
			if ( storageList == null ) {
			    System.out.println("movie not found");
			}
			else {
			    PrintList( storageList, true );
			}
                        break;
			
                    case 'p':// print movies associated with an actor
			storageList = movieDb.getMovies(remainder);
			//print out the movies unless storageList is null
			if ( storageList == null 
			     || storageList.size() == 0 ) {
			    System.out.println("actor not found");
			}
			else {
			    PrintList( storageList, true );
			}
			break;
    
                    case 'r':// remove movie with given title
			if ( movieDb.removeMovie(remainder) ) {
			    System.out.println("movie removed");
			}
			else {
			    System.out.println("movie not found");
			}
                        break;
    
                    case 's':// Search for movies with both given actors
                        // The following reads in comma-separated sequence 
                        // of strings. If there are exactly two strings in 
			// the sequence, the strings are assigned to name1 
			// and name2. Otherwise, an error message is printed.
                        String[] tokens = remainder.split("[,]+");
                        if ( tokens.length != 2 ) {
System.out.println("need to provide exactly two names");
                        }
                        else {
                        	//Format the style of the input
                            String name1 = StringStyle(tokens[0].trim());
                            String name2 = StringStyle(tokens[1].trim());
                            
			    // Store the common movies:
			    List<String> currMovieList 
				= new ArrayList<String>();
			    
			    // Iterate over movies with 1st actor:
			    storageList = movieDb.getMovies(name1);
			    Iterator<String> currDbItr 
				= storageList.iterator();
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
			
                    case 'w':// withdraw actor from all movies
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
			// display actor information:
                    case 'd': 
			List<String> currMovieList = new ArrayList<String>();
			List<String> currActorList = new ArrayList<String>();
			List<String> largestCast = new ArrayList<String>();
			List<String> smallestCast = new ArrayList<String>();
			
			int uniqueActors = 0;
			int minActors = -1;
			int maxActors = -1;
			int totalActors = 0;
			
			int minMovies = -1;
			int maxMovies = -1;
			int totalMovies = 0;
			
			// Iterator over movies to find unique actors:
			Iterator<Movie> movieItr = movieDb.iterator();
			while ( movieItr.hasNext() ) {
			    Movie currMovie = movieItr.next();
			    if ( !Compare( currMovie.getTitle(), 
					   currMovieList ) ) {
				currMovieList.add( currMovie.getTitle() );
			    }
			    
			    List<String> currCast 
				= movieDb.getCast( currMovie.getTitle() );
			    Iterator<String> castItr = currCast.iterator();
			    // Loop over cast of current movie to find actors:
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
			    if ( actorsPerMovie <= minActors 
				 || minActors == -1 ) {
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
			    if ( actorsPerMovie >= maxActors 
				 || maxActors == -1 ) {
				if ( actorsPerMovie == maxActors ) {
				    largestCast.add( currMovie.getTitle() );
				}
				else {
				    largestCast = new ArrayList<String>();
				    largestCast.add( currMovie.getTitle() );
				}
				maxActors = actorsPerMovie;
			    }
			}// end while loop for movieItr
			
			// Iterate over unique actors, and then check
			// whether they have most or least credits:
		        Iterator<String> actorItr = currActorList.iterator();
			while ( actorItr.hasNext() ) {
			    currMovieList = 
				movieDb.getMovies( actorItr.next() );
			    int moviesPerActor = currMovieList.size();
			    totalMovies += moviesPerActor;
			    if ( moviesPerActor <= minMovies 
				 || minMovies == -1 ) {
				minMovies = moviesPerActor;
			    }
			    if ( moviesPerActor >= maxMovies 
				 || maxMovies == -1 ) {
				maxMovies = moviesPerActor;
			    }
			}// end while loop for actorItr
			
			// Print display to screen:
			System.out.printf( "Movies: %d, Actors: %d\n",
					   movieDb.size(), uniqueActors );

			if( maxActors == -1 ){ maxActors = 0; }
			if( minActors == -1 ){ minActors = 0; }
			if( maxMovies == -1 ){ maxMovies = 0; }
			if( minMovies == -1 ){ minMovies = 0; }
			
			double avgActors = ((double)totalActors /
					    ((double)movieDb.size()));
			double avgMovies = ((double)totalMovies / 
					    ((double)currActorList.size()));
			
System.out.printf( "# of actors/movie: most %d, least %d, average %d\n",
		   maxActors, minActors, Math.round(avgActors) );
System.out.printf( "# of movies/actor: most %d, least %d, average %d\n",
		   maxMovies, minMovies, Math.round(avgMovies) );
			
			System.out.print("Largest Cast: ");
                    	PrintList( largestCast, false );
			System.out.printf(" [%d]\n",maxActors);
			System.out.print("Smallest Cast: ");
                    	PrintList( smallestCast, false );
                    	System.out.printf(" [%d]\n",minActors);
			
                        break;
			
		    case 'x':// display exit and then exit program
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
}// end class
