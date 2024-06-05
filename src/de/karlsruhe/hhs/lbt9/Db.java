package de.karlsruhe.hhs.lbt9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Wraps interaction with the SQL database for storing and retrieving game data
 */
public class Db {

	private static final String[] MIGRATIONS = { """
			CREATE TABLE "metadata" (
			   "id"        INTEGER NOT NULL UNIQUE,
			   "migration" INTEGER NOT NULL,
			   PRIMARY     KEY("id")
			)
			""",

			"""
					CREATE TABLE "games" (
					   "id"    INTEGER NOT NULL UNIQUE,
					   PRIMARY KEY("id" AUTOINCREMENT)
					)
					""",

			"""
					CREATE TABLE "players" (
					   "id"    INTEGER NOT NULL UNIQUE,
					   PRIMARY KEY("id" AUTOINCREMENT)
					)
					""",

			"""
					CREATE TABLE "rolls" (
					   "id"         INTEGER NOT NULL UNIQUE,
					   "value"      INTEGER NOT NULL,
					   "game"       INTEGER NOT NULL,
					   "player"     INTEGER NOT NULL,
					   "created_at" TIMESTAMP NOT NULL,
					   PRIMARY      KEY("id" AUTOINCREMENT),
					   FOREIGN      KEY (game) REFERENCES games(id),
					   FOREIGN      KEY (player) REFERENCES players(id)
					)
					""" };

	private Connection connection;

	Db(String dbUrl, String username, String password) throws SQLException {
		this.connection = DriverManager.getConnection(dbUrl, username, password);
		this.migrate(this.getNextMigrationIndex());
	}

	/**
	 * Get next migration index out of the metadata table
	 * 
	 * @return Next migration index to apply or 0 if no metadata table is found
	 * @throws SQLException
	 */
	private int getNextMigrationIndex() throws SQLException {
		var meta = this.connection.getMetaData();
		var resultSet = meta.getTables(null, null, "metadata", new String[] { "TABLE" });
		if (!resultSet.next()) {
			return 0;
		}

		var statement = this.connection.createStatement();
		resultSet = statement.executeQuery("SELECT migration FROM metadata WHERE id = 0 LIMIT 1");
		return resultSet.getInt(1);
	}

	/**
	 * Store next migration index in the metadata table
	 * 
	 * @param value Next migration index
	 * @throws SQLException
	 */
	private void setNextMigrationIndex(int value) throws SQLException {
		var preparedStatement = this.connection.prepareStatement("REPLACE INTO metadata VALUES (0, ?)");
		preparedStatement.setInt(1, value);
		preparedStatement.execute();
	}

	/**
	 * Get average dice roll value for a player by ID
	 * @param playerId Player ID
	 * @return Average dice roll value
	 * @throws SQLException
	 */
	public double getAverageRoll(int playerId) throws SQLException {
		final var playerAverageQuery = "SELECT AVG(value) from rolls where player = ?";
		var preparedStatement = this.connection.prepareStatement(playerAverageQuery);
		preparedStatement.setInt(1, playerId);
		final var resultSet = preparedStatement.executeQuery();
		return resultSet.getDouble(1);
	}

	/**
	 * Get the sum of all dice rolls for a player by ID
	 * @param playerId Player ID
	 * @return Sum of all dice rolls by that player
	 * @throws SQLException
	 */
	public long getSumOfAllRolls(int playerId) throws SQLException {
		final var playerSumQuery = "SELECT SUM(value) from rolls where player = ?";
		var preparedStatement = this.connection.prepareStatement(playerSumQuery);
		preparedStatement.setInt(1, playerId);
		final var resultSet = preparedStatement.executeQuery();
		return resultSet.getLong(1);
	}

	/**
	 * Apply database migrations
	 * 
	 * @param startIndex Index from which to start the migration
	 * @return true in case of success, false otherwise
	 * @throws SQLException
	 */
	private boolean migrate(int startIndex) throws SQLException {
		var previousAutoCommitMode = this.connection.getAutoCommit();
		this.connection.setAutoCommit(false);
		try {
			for (int i = startIndex; i < MIGRATIONS.length; ++i) {
				var statement = this.connection.createStatement();
				statement.execute(MIGRATIONS[i]);
			}
			setNextMigrationIndex(MIGRATIONS.length);
			this.connection.commit();
			return true;
		} catch (SQLException e) {
			this.connection.rollback();
			throw e;
		} finally {
			this.connection.setAutoCommit(previousAutoCommitMode);
		}
	}

	private int newGame() throws SQLException {
		var statement = this.connection.createStatement();
		return 0;
	}

	/**
	 * Saves a game state to the database
	 * 
	 * @param game Game to save
	 */
	public void save(Game game) throws SQLException {
		final String ROLL_STATEMENT = "INSERT INTO rolls (value, game, player, created_at) VALUES (?, ?, ?, ?)";
		var previousAutoCommitMode = this.connection.getAutoCommit();
		this.connection.setAutoCommit(false);
		try {
			var gameId = newGame();
			for (int player = 0; player < game.getPlayers(); ++player) {
				for (int roll = 0; roll < game.getRolls(); ++roll) {
					var preparedStatement = this.connection.prepareStatement(ROLL_STATEMENT);
					preparedStatement.setInt(1, game.getRoll(player, roll));
					preparedStatement.setInt(2, gameId);
					preparedStatement.setInt(3, player);
					preparedStatement.setTimestamp(4, Timestamp.valueOf(game.getRollDate(player, roll)));
					preparedStatement.execute();
				}
			}
			this.connection.commit();
		} catch (SQLException e) {
			this.connection.rollback();
			throw e;
		} finally {
			this.connection.setAutoCommit(previousAutoCommitMode);
		}
	}
}
