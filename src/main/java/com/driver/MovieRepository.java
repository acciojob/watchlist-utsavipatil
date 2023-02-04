package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class MovieRepository {
    private HashMap<String , Movie> movieMap;
    private HashMap<String , Director> directorMap;
    private HashMap<String , List<String>> directorMovieMap; //Pair of director name --> List of movie names

    //Initialization is very important :
    public MovieRepository() {
        this.movieMap = new HashMap<String , Movie>();
        this.directorMap = new HashMap<String , Director>();
        this.directorMovieMap = new HashMap<String , List<String>>();
    }
    public void saveMovie(Movie movie){
        movieMap.put(movie.getName() , movie);
    }
    public void saveDirector(Director director){
        directorMap.put(director.getName() , director);
    }
    public void saveMovieDirectorPair(String movie , String director){
        //Add the movie into Database before not having movie object
        if(movieMap.containsKey(movie) && directorMap.containsKey(director)){ //Before saving pair we need to store director & movie individually
            List<String> currentMovies = new ArrayList<>();
            if(directorMovieMap.containsKey(director)){
                currentMovies = directorMovieMap.get(director);
            }
            currentMovies.add(movie);
            directorMovieMap.put(director , currentMovies);
        }
    }
    public Movie findMovie(String movie){
        return movieMap.get(movie);
    }
    public Director findDirector(String director){
        return directorMap.get(director);
    }
    public List<String> findMoviesFromDirector(String director){
        return directorMovieMap.get(director);
    }
    public List<String> findAllMovies(){
        return new ArrayList<>(movieMap.keySet());
    }

    public void deleteDirector(String director){
        List<String> movies = new ArrayList<>();
        if(directorMovieMap.containsKey(director)){
            movies = directorMovieMap.get(director); //Find movie names by director from pair
            for(String movie : movies){ //Delete all movies from movieMap using movie names
                if(movieMap.containsKey(movie)){
                    movieMap.remove(movie);
                }
            }
            directorMovieMap.remove(director);
        }
        if(directorMap.containsKey(director)){ //Delete director from Director Map
            directorMap.remove(director);
        }
    }

    public void deleteAllDirector(){
        HashSet<String> movieSet = new HashSet<>();

        directorMap = new HashMap<>(); //delete director's map

        for(String director : directorMovieMap.keySet()){ //Find out all movies by all directors combined
            for(String movie : directorMovieMap.get(director)){ //Iterating list of movies by director
                movieSet.add(movie);
            }
        }
        for(String movie : movieSet){ //Delete movie from movieMap
            if(movieMap.containsKey(movie)){
                movieMap.remove(movie);
            }
        }
        directorMovieMap = new HashMap<>(); //Clear pairing
    }
}
