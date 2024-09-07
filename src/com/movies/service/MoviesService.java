package com.movies.service;

import java.sql.SQLException;
import java.util.Scanner;
import com.movies.dao.MoviesDaoImpl;
import com.movies.model.Movie;

public class MoviesService {
    private static MoviesDaoImpl dao;

    public static void main(String[] args) throws SQLException {
        dao = new MoviesDaoImpl();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Movie Mania");

        int choice;

        do {
            System.out.println("Enter your choice:");
            System.out.println("1. Add Movie");
            System.out.println("2. Read All Movies");
            System.out.println("3. Update Movie");
            System.out.println("4. Delete Movie");
            System.out.println("5. Find Movie by Id");
            System.out.println("6. Find Movies of Director");
            System.out.println("7. List Action Movies");
            System.out.println("0. Exit");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add Movie
                    System.out.print("Enter Movie ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Genre: ");
                    String genre = sc.nextLine();
                    System.out.print("Enter Release Date (YYYY-MM-DD): ");
                    String releaseDateStr = sc.nextLine();
                    java.sql.Date releaseDate = java.sql.Date.valueOf(releaseDateStr);
                    System.out.print("Enter Director: ");
                    String director = sc.nextLine();

                    Movie movie = new Movie(id, title, genre, releaseDate, director);
                    dao.addMovie(movie);
                    break;

                case 2:
                    // Read All Movies
                    dao.readAllMovies();
                    break;

                case 3:
                    // Update Movie
                    System.out.print("Enter Movie ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.print("Enter new Title: ");
                    String newTitle = sc.nextLine();
                    System.out.print("Enter new Genre: ");
                    String newGenre = sc.nextLine();
                    System.out.print("Enter new Release Date (YYYY-MM-DD) or leave blank: ");
                    String newReleaseDateStr = sc.nextLine();
                    java.sql.Date newReleaseDate = null;
                    if (!newReleaseDateStr.trim().isEmpty()) {
                        newReleaseDate = java.sql.Date.valueOf(newReleaseDateStr);
                    }
                    System.out.print("Enter new Director: ");
                    String newDirector = sc.nextLine();

                    Movie updatedMovie = new Movie(updateId, newTitle, newGenre, newReleaseDate, newDirector);
                    dao.updateMovie(updatedMovie);
                    break;

                case 4:
                    // Delete Movie
                    System.out.print("Enter Movie ID to delete: ");
                    int deleteId = sc.nextInt();
                    dao.deleteMovie(deleteId);
                    break;

                case 5:
                    // Find Movie by Id
                    System.out.print("Enter Movie ID to find: ");
                    int findId = sc.nextInt();
                    String movieTitle = dao.FindMovieNameById(findId);
                    if (movieTitle != null) {
                        System.out.println("Movie Title: " + movieTitle);
                    } else {
                        System.out.println("Movie not found.");
                    }
                    break;

                case 6:
                    // Find Movies by Director
                    System.out.print("Enter Director's Name: ");
                    String directorName = sc.nextLine();
                    dao.getMoviesWithDirector(directorName);
                    break;

                case 7:
                    // List Action Movies
                    dao.listActionMovies();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 0);

        sc.close();
    }
}
