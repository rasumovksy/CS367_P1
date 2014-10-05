///////////////////////////////////////////////////////////////////////////////
// 
// Main Class File:  MovieFlix.java 
// File:             MovieDatabase.java
// Semester:         CS302 Fall 2014
//
// Author:           Andrew Hard hard@wisc.edu
// CS Login:         hard
// Lecturer's Name:  Jim Skrentny 
// Lab Section:      LEC-002 (77632)
//
///////////////////////////////////////////////////////////////////////////////
//
// Pair Partner:     Ming Chew
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
///////////////////////////////////////////////////////////////////////////////

import java.util.*;

/**
 * The MovieDatabase class stores Movie objects.
 * It enables modification of the Movie cast, and
 * returns information on Movies, actors, and the
 * associations of the two.
 *
 * @author Ming Chew
 * @author Andrew Hard
 */
public class MovieDatabase {
    
    // Stores the movies contained in the database:
    private List<Movie> movies;
    
    // Iterator for the movies in the database:
    private Iterator<Movie> movieItr;
    // Iterator over the cast in the current movie:
    private Iterator<String> castItr;
    
    // Current list of movies:
    private List<String> currMovieList;
    // Current list of actors:
    private List<String> currCastList;
    
    // Current Movie object:
    private Movie currMovie;
    
    /**
     * Constructs an empty database.
     */
    public MovieDatabase() {
	movies = new ArrayList<Movie>();
    }
    
    /**
     * Add a movie with the given title t to the end
     * of the database. If a movie with the title t 
     * is already in the database, just return.
     *
     * @param t The title of the movie.
     */
    public void addMovie( String t ) {
	if ( !containsMovie(t) && t != null ) {
	    currMovie = new Movie(t);
	    movies.add(currMovie);
	}
    }
    
    /**
     * 	Add the actor with given name n to the movie 
     * with the given title t in the database. If a 
     * movie with the title t is not in the database 
     * throw a java.lang.IllegalArgumentException. If 
     * n is already in the cast of actors in the movie 
     * with title t, just return.
     *
     * @param n The name of the actor.
     * @param t The title of the movie.
     */
    public void addActor( String n, String t ) {
	if ( !containsMovie(t) ) {
	    throw new java.lang.IllegalArgumentException();
	}
	if ( !isCast(n, t) && n != null ) {
	    currCastList.add(n);
	}
    }
    
    /**
     * Remove the movie with the title t from the 
     * database. If a movie with the title t is not in 
     * the database, return false; otherwise (i.e., 
     * removal is sucessful) return true.
     *
     * @param t The title of the movie.
     * @return true iff the movie is removed. 
     */
    public boolean removeMovie(String t) {
	if ( t == null ) {
	    throw new java.lang.IllegalArgumentException();
	}
	if( !containsMovie(t) ) {
	    return false;
	}
	else {
	    Iterator<Movie> movieItr = this.iterator(); // should it just be iterator?
	    return true;
	}
    }
    
    /**
     * Return true iff a movie with the title t is in
     * the database.
     *
     * @param t The title of the movie.
     * @return true iff MovieDatabase contains the movie.
     */
    public boolean containsMovie( String t ) {
	if( t != null ) {
	    movieItr = this.iterator(); // this.iterator() ?
	    while ( movieItr.hasNext() ) {
		currMovie = movieItr.next();
		if( currMovie.getTitle().equals(t) ) {
		    return true;
		}
	    }
	}
	return false;
    }
    
    /**
     * Return true iff an actor with the name n 
     * appears in the cast for at least one movie in 
     * the database.
     *
     * @param n The name of the actor.
     * @return true iff the MovieDatabase contains the actor.
     */
    public boolean containsActor( String n ) {
	if ( n != null ) {
	    movieItr = this.iterator(); // this.iterator() ?
	    while ( movieItr.hasNext() ) {
		currMovie = movieItr.next();
		currCastList = currMovie.getCast();
		castItr = currCastList.iterator();
		while ( castItr.hasNext() ) {
		    if ( castItr.next().equals(n) ) {
			return true;
		    }
		}
	    }
	}
	return false;
    }
    
    /**
     * Returns true iff the given actor n is cast in 
     * the movie with the given title t. If a movie 
     * with the title t is not in the database, return 
     * false.
     *
     * @param n The name of the actor.
     * @param t The title of the movie.
     * @return true iff the actor n is cast in movie t.
     */
    public boolean isCast( String n, String t ) {
	if ( n != null && t != null ) {
	    currCastList = getCast(t);
	    castItr = currCastList.iterator();
	    while ( castItr.hasNext() ) {
		if ( castItr.next().equals(n) ) {
		    return true;
		}
	    }
	}
	return false;
    }
    
    /**
     * Return the cast of actors for the movie with the 
     * given title t. If a movie with the title t is not 
     * in the database, return null.
     */
    public List<String> getCast( String t ) {
	if ( containsMovie(t) ) {
	    return currMovie.getCast();
	}
	return null;
    }
    
    /** 
     * Return the list of movies in which the actor with
     * the given name n is cast. If an actor with the name 
     * n is not in the database, return null.
     */
    public List<String> getMovies( String n ) {
	currMovieList = new ArrayList<String>();
	if ( n != null && containsActor(n) ) {
	    movieItr = this.iterator(); // this.iterator() ?
	    while ( movieItr.hasNext() ) {
		currMovie = movieItr.next();
		if ( isCast( n, currMovie.getTitle() ) ) {
		    currMovieList.add( currMovie.getTitle() );
		}
	    }
	}
	return currMovieList;
    }
    
    /**
     * Return an Iterator over the Movie objects in the 
     * database. The movies should be returned in the order 
     * they were added to the database (resulting from the 
     * order in which they are in the input file).
     */
    public Iterator<Movie> iterator() {
	return movies.iterator();
    }
    
    /**
     * Return the number of movies in this database.
     */
    public int size() {
	return movies.size();
    }
    
    /**
     * Remove the actor with the given name n from the data-
     * base (i.e., remove the actor from every movie in which
     * they are cast). If an actor with the name n is not in
     * the database, return false; otherwise (i.e., the removal
     * is successful) return true.
     */
    public boolean removeActor(String n) {
	while ( containsActor(n) ) {
	    castItr.remove();//double check that remove() is implemented for ArrayList
	}
	return false;
    }
}
