package de.karlsruhe.hhs.lbt9;

public class Main {

	public static void main(String[] args) {

		final String DB_URL = "jdbc:h2:mem:DICE_GAME";
		final String USERNAME = "";
		final String PASSWORD = "";
		Db db = new Db(DB_URL, USERNAME, PASSWORD);

		if (!db.connect()) {
			System.err.println("Failed to connect to databse. Aborting.");
			return;
		}

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
			gameFinished = game.finished();
		}

	}

}
