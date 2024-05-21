package de.karlsruhe.hhs.lbt9;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		final String DB_URL = "jdbc:sqlite:dice_game.sqlite3";
		final String USERNAME = "";
		final String PASSWORD = "";
		Db db = new Db(DB_URL, USERNAME, PASSWORD);

		final int SIDES = 6;
		final long SEED = 0;
		Dice dice = new Dice(SIDES, SEED);

		final int PLAYERS = 5;
		final int ROLLS = 10;
		Game game = new Game(PLAYERS, ROLLS);

		boolean gameFinished = false;
		while (!gameFinished) {
			game.play(dice);
			System.out.println(game);
			db.save(game);
			gameFinished = game.finished();
		}
	}

}
