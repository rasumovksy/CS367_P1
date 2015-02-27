import java.util.*;

/**
 * The Movie class represents a single movie that keeps track of its
 * title and cast of actors.
 * 
 * @author CS 367 copyright 2014
 */
public class Movie {

    private String title;       // the movie title
    private List<String> cast;  // the list of actors in the movie
    
    /**
     * Constructs a movie with the given title and an empty cast.
     * 
     * @param title The title of this movie.
     */
    public Movie(String title) {
        this.title = title;
balbalsklasd
asdkpasjdfkas
asjdahsjl
        cast = new ArrayList<String>();
    }
    
    /**
     * Returns the title of this movie.
     * 
     * @return The title of this movie.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Returns the cast of actors in this movie.
     * 
     * @return The cast for this movie.
     */
    public List<String> getCast() {
        return cast;
    }
}
