package de.karlsruhe.hhs.lbt9;

public class Game {

	final int GAMERS = 5;
	final int ROUNDS = 10;

	private int[][] results;

	Game() {
		this.results = new int[GAMERS][ROUNDS];
	}

	int wuerfeln() {
		int n = (int) (Math.random() * 6 + 1);
		return n;
	}

	public void play() {
		for (int e = 0; e < this.results.length; e++) {
			for (int i = 0; i < results[e].length; i++) {
				results[e][i] = wuerfeln();
			}
		}
	}
	
	boolean allSumsEven() {
		for (int gamer = 0; gamer < this.results.length; gamer++) {
			int sum = 0;
			
			for (int result = 0; result < results[gamer].length; result++) {
				sum += results[gamer][result];
			}
			
			if (sum % 2 != 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		final String DB_URL = "jdbc:h2:mem:DICE_GAME";
		final String USERNAME = "";
		final String PASSWORD = "";
				
		Db db = new Db(DB_URL, USERNAME, PASSWORD);
		if (db.connect()) {
			System.out.println("connected");
		} else {
			System.out.println("not connected");
		}
	}
}
