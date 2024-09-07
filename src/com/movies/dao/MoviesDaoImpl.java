package com.movies.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

import com.movies.model.Movie;

public class MoviesDaoImpl implements MoviesDao {

	@Override
	public Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "SYS as SYSDBA";
		String pwd = "abcd";
		return DriverManager.getConnection(url, user, pwd);
	}

	@Override
	public void addMovie(Movie movie) {
		Connection con = null;
		CallableStatement cst = null;

		try {
			// Establish connection to the database
			con = getConnection();

			// SQL for calling the stored procedure
			String sql = "{call add_movie(?, ?, ?, ?, ?)}";
			cst = con.prepareCall(sql);

			// Set input parameters for the stored procedure
			cst.setInt(1, movie.getMovieID());

			cst.setString(2, movie.getTitle());
			cst.setString(3, movie.getGenre());
			cst.setDate(4, new java.sql.Date(movie.getReleaseDate().getTime()));
			cst.setString(5, movie.getDirector());

			// Execute the stored procedure
			cst.executeUpdate();
			System.out.println("Movie added to the store successfully.");
		} catch (SQLException e) {
			// Handle SQL exceptions
			System.err.println("SQL error occurred: " + e.getMessage());
			if (e.getErrorCode() == 20001) {
				System.err.println("Duplicate ID error!");
			} else {
				System.err.println("SQL Error Code: " + e.getErrorCode());
			}
		} finally {
			// Clean up resources
			try {
				if (cst != null)
					cst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void readAllMovies() throws SQLException {
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;

		try {
			// Establish connection to the database
			con = getConnection();

			// Prepare the SQL call to the stored procedure
			String sql = "{call read_all_movies(?)}";
			cst = con.prepareCall(sql);

			// Register the OUT parameter that will hold the cursor (result set)
			cst.registerOutParameter(1, Types.REF_CURSOR);

			// Execute the stored procedure
			cst.execute();

			// Get the cursor (result set) from the OUT parameter
			rs = (ResultSet) cst.getObject(1);

			// Loop through the result set and print movie details
			while (rs.next()) {
				int movieID = rs.getInt(1);
				String title = rs.getString(2);
				String genre = rs.getString(3);
				Date releaseDate = null;
				try {
					releaseDate = rs.getDate(4);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				String director = rs.getString(5);

				System.out.println("MovieID: " + movieID);
				System.out.println("Title: " + title);
				System.out.println("Genre: " + genre);
				System.out.println("Release Date: " + releaseDate);
				System.out.println("Director: " + director);
				System.out.println("-----------------------------");
			}
		} catch (SQLException e) {
			// Handle SQL exceptions
			System.err.println("SQL error occurred: " + e.getMessage());
			throw e; // rethrow the exception after logging it
		} finally {
			// Clean up resources
			try {
				if (rs != null)
					rs.close();
				if (cst != null)
					cst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateMovie(Movie movie) throws SQLException {
		Connection con = null;
		CallableStatement cst = null;

		try {
			con = getConnection();
			String sql = "{call update_movie(?, ?, ?, ?, ?)}";
			cst = con.prepareCall(sql);

			cst.setInt(1, movie.getMovieID());
			cst.setString(2, movie.getTitle());
			cst.setString(3, movie.getGenre());

			// Handle possible null releaseDate
			if (movie.getReleaseDate() != null) {
				cst.setDate(4, (java.sql.Date) movie.getReleaseDate());
			} else {
				cst.setNull(4, java.sql.Types.DATE);
			}

			cst.setString(5, movie.getDirector());

			cst.executeUpdate();
			System.out.println("Movie updated successfully");
		} catch (SQLException e) {
			System.err.println("SQL error occurred: " + e.getMessage());
			if (e.getErrorCode() == 20001) {
				System.err.println("Duplicate ID error!");
			} else {
				System.err.println("SQL Error Code: " + e.getErrorCode());
			}
		} finally {
			try {
				if (cst != null)
					cst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteMovie(int movieId) throws SQLException {
		Connection con = null;
		CallableStatement cst = null;

		try {
			// Establish connection to the database
			con = getConnection();

			// Prepare the call to the stored procedure
			String sql = "{call delete_movie(?)}";
			cst = con.prepareCall(sql);

			// Set the input parameter for the stored procedure
			cst.setInt(1, movieId);

			// Execute the procedure
			cst.executeUpdate();
			System.out.println("Movie deleted successfully.");
		} catch (SQLException e) {
			// Handle SQL exceptions
			System.err.println("SQL error occurred: " + e.getMessage());
			throw e; // rethrow the exception after logging it
		} finally {
			// Clean up resources
			try {
				if (cst != null)
					cst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String FindMovieNameById(int movieID) throws SQLException {
		Connection con = null;
		CallableStatement cst = null;
		String movieTitle = null;

		try {
			// Establish connection to the database
			con = getConnection();

			// Prepare the call to the stored procedure
			String sql = "{call read_one_movie(?, ?, ?, ?, ?)}";
			cst = con.prepareCall(sql);

			// Register the OUT parameters
			cst.registerOutParameter(2, java.sql.Types.VARCHAR); // Title
			cst.registerOutParameter(3, java.sql.Types.VARCHAR); // Genre
			cst.registerOutParameter(4, java.sql.Types.DATE); // ReleaseDate
			cst.registerOutParameter(5, java.sql.Types.VARCHAR); // Director

			// Set the input parameter for the stored procedure
			cst.setInt(1, movieID);

			// Execute the procedure
			cst.execute();

			// Retrieve the output parameters
			movieTitle = cst.getString(2); // Get movie title

			// Optionally handle null cases or other details here
			if (movieTitle == null) {
				System.out.println("No movie found with the provided ID.");
			}
		} catch (SQLException e) {
			// Handle SQL exceptions
			System.err.println("SQL error occurred: " + e.getMessage());
			throw e; // rethrow the exception after logging it
		} finally {
			// Clean up resources
			try {
				if (cst != null)
					cst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return movieTitle;
	}

	@Override
	public void getMoviesWithDirector(String directorName) throws SQLException {
		Connection con = null;
		CallableStatement cst = null;
		ResultSet rs = null;

		try {
			// Establish connection to the database
			con = getConnection();

			// Prepare the call to the function
			String sql = "{ ? = call get_movies_by_director(?) }";
			cst = con.prepareCall(sql);

			// Register the OUT parameter for the REF CURSOR
			cst.registerOutParameter(1, Types.REF_CURSOR);
			cst.setString(2, directorName);

			// Execute the function
			cst.execute();

			// Retrieve the REF CURSOR
			rs = (ResultSet) cst.getObject(1);

			// Process the result set
			while (rs.next()) {
				int movieID = rs.getInt("MovieID");
				String title = rs.getString("Title");
				String genre = rs.getString("Genre");
				Date releaseDate = rs.getDate("ReleaseDate");
				String director = rs.getString("Director");
				System.out.printf("ID: %d, Title: %s, Genre: %s, Release Date: %s, Director: %s%n",
						movieID, title, genre, releaseDate, director);
			}
		} catch (SQLException e) {
			// Handle SQL exceptions
			System.err.println("SQL error occurred: " + e.getMessage());
			throw e; // rethrow the exception after logging it
		} finally {
			// Clean up resources
			try {
				if (rs != null)
					rs.close();
				if (cst != null)
					cst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void listActionMovies() throws SQLException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// Establish connection to the database
			con = getConnection();

			// Create a statement to execute the view query
			stmt = con.createStatement();

			// Query the action_movies view
			String sql = "SELECT * FROM action_movies";
			rs = stmt.executeQuery(sql);

			// Process the result set
			while (rs.next()) {
				int movieID = rs.getInt("MovieID");
				String title = rs.getString("Title");
				Date releaseDate = rs.getDate("ReleaseDate");
				String director = rs.getString("Director");
				System.out.printf("ID: %d, Title: %s, Release Date: %s, Director: %s%n",
						movieID, title, releaseDate, director);
			}
		} catch (SQLException e) {
			// Handle SQL exceptions
			System.err.println("SQL error occurred: " + e.getMessage());
			throw e; // rethrow the exception after logging it
		} finally {
			// Clean up resources
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
