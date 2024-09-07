package com.movies.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.movies.model.Movie;

public interface MoviesDao {
    
	public Connection getConnection()throws SQLException;
    public void addMovie(Movie movie) throws SQLException ;
	public void readAllMovies() throws SQLException;
	public void updateMovie(Movie movie) throws SQLException;
	public void deleteMovie(int MovieID) throws SQLException;
	public String FindMovieNameById(int MovieID) throws SQLException;
    public void getMoviesWithDirector(String directorName) throws SQLException;
    public void listActionMovies() throws SQLException;
}
