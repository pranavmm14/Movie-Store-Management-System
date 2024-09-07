package com.movies.model;

import java.util.Date;

public class Movie {
	private int MovieID;
	private String Title;
	private String Genre;
	private Date ReleaseDate;
	private String Director;

	public Movie(int MovieID, String Title, String Genre, Date ReleaseDate, String Director) {
		this.MovieID = MovieID;
		this.Title = Title;
		this.Genre = Genre;
		this.ReleaseDate = ReleaseDate;
		this.Director = Director;
	}

	public int getMovieID() {
		return MovieID;
	}

	public void setMovieID(int movieID) {
		MovieID = movieID;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getGenre() {
		return Genre;
	}

	public void setGenre(String genre) {
		Genre = genre;
	}

	public Date getReleaseDate() {
		return ReleaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.ReleaseDate = releaseDate;
	}

	public String getDirector() {
		return Director;
	}

	public void setDirector(String director) {
		Director = director;
	}

	@Override
	public String toString() {
		return "Movie [MovieID: " + MovieID + ", Title: " + Title + ", Genre: " + Genre + ", Release Date: "
				+ ReleaseDate + ", Director: " + Director + "]";
	}
}
