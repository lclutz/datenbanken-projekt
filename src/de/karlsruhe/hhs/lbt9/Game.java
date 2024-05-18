package de.karlsruhe.hhs.lbt9;

public class Game {
	
	private final int players;
	private final int rolls;
	private int[][] gameMemory;

	Game(int players, int rolls) {
		this.players = players;
		this.rolls = rolls;
		this.gameMemory = new int[this.players][this.rolls];
	}
	
	/**
	 * Get the number of players per game
	 * @return Number of players per game
	 */
	public int getPlayers() {
		return this.players;
	}
	
	/**
	 * Get the number of dice rolls per player
	 * @return Number of dice rolls per player
	 */
	public int getRolls() {
		return this.rolls;
	}

	/**
	 * Get a specific dice roll of a specific player
	 * @param player Player index
	 * @param roll Dice roll index
	 * @return dice roll value
	 */
	public int getRoll(int player, int roll) {
		return this.gameMemory[player][roll];
	}
	
	/**
	 * Set the dice roll value for a specific roll of a specific player
	 * @param player Player index
	 * @param roll Dice roll index
	 * @param value Dice roll value to set
	 * @return true in case of success, false otherwise
	 */
	public boolean setRoll(int player, int roll, int value) {
		if (0 > player || player >= this.players) {
			System.err.println("Error: invalid player id: " + player);
			return false;
		}
		
		if (0 > roll || roll >= this.rolls) {
			System.err.println("Error: invalid roll id: " + roll);
			return false;
		}
		
		this.gameMemory[player][roll] = value;
		return true;
	}
	
	/**
	 * Rolls the dice for all players
	 * @param dice Dice to roll
	 */
	public void play(Dice dice) {
		for (var player : this.gameMemory) {
			for (int i = 0; i < player.length; i++) {
				player[i] = dice.roll();
			}
		}
	}

	/**
	 * Check if the game is finished by checking if the sums of dice rolls per player are all even
	 * @return true if all the sums are even otherwise false
	 */
	boolean finished() {
		for (var playerRolls : gameMemory) {
			int sum = 0;
			for (var playerRoll : playerRolls) {
				sum += playerRoll;
			}
			if (sum % 2 != 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Game {\n");
		for (var player : this.gameMemory) {
			int sum = 0;
			sb.append("\tPlayer").append("[");
			for (var diceRoll : player) {				
				sb.append(diceRoll).append(",");
				sum += diceRoll;
			}
			sb.append("], (sum=").append(sum).append(")\n");
		}
		sb.append("}");

		return sb.toString();
	}
}
