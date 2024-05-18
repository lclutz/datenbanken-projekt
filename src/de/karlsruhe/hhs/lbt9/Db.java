package de.karlsruhe.hhs.lbt9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
	static final String QUERY = "SELECT id, first, last, age FROM Employees";

	private final String dbUrl;
	private final String username;
	private final String password;
	private Connection conn;

	Db(String dbUrl, String username, String password) {
		this.dbUrl = dbUrl;
		this.username = username;
		this.password = password;
	}
	
	public boolean connect() {
		try {
			this.conn = DriverManager.getConnection(this.dbUrl, this.username, this.password);
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	public void save(Game game) {
		
	}
}
