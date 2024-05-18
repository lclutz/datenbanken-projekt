package de.karlsruhe.hhs.lbt9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Wraps interaction with the SQL database for storing and retrieving game data
 */
public class Db {
	static final String QUERY = "SELECT id, first, last, age FROM Employees";
	static final String SCHEMA = 
			"CREATE SCHEMA DICE_GAME" +
			"    CREATE TABLE DiceRolls (value int);" +
			"GO";

	private final String dbUrl;
	private final String username;
	private final String password;
	private Connection connection;

	Db(String dbUrl, String username, String password) {
		this.dbUrl = dbUrl;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Connects to the database with the user provided address and credentials
	 * @return true if connection was successful otherwise false
	 */
	public boolean connect() {
		try {
			this.connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Saves a game state to the database
	 * @param game Game to save
	 */
	public void save(Game game) {
		
	}
}
