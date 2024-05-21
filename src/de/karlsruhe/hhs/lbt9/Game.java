package de.karlsruhe.hhs.lbt9;

import java.time.LocalDateTime;

public class Game {
	
	private final int players;
	private final int rolls;
	private int[][] diceRolls;
	private LocalDateTime[][] diceRollDates;

	Game(int players, int rolls) {
		this.players = players;
		this.rolls = rolls;
		this.diceRolls = new int[this.players][this.rolls];
		this.diceRollDates = new LocalDateTime[this.players][this.rolls];
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
	 * @return Dice roll value
	 */
	public int getRoll(int player, int roll) {
		return this.diceRolls[player][roll];
	}
	
	/**
	 * Get the date of a dice roll of a specific player
	 * @param player Player index
	 * @param roll Dice roll index
	 * @return Date when the dice roll happened
	 */
	public LocalDateTime getRollDate(int player, int roll) {
		return this.diceRollDates[player][roll];
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
		
		this.diceRolls[player][roll] = value;
		this.diceRollDates[player][roll] = LocalDateTime.now();
		return true;
	}
	
	/**
	 * Rolls the dice for all players
	 * @param dice Dice to roll
	 */
	public void play(Dice dice) {
		for (int player = 0; player < this.players; ++player) {
			for (int roll = 0; roll < this.rolls; ++roll) {
				this.setRoll(player, roll, dice.roll());
			}
		}
	}

	/**
	 * Check if the game is finished by checking if the sums of dice rolls per player are all even
	 * @return true if all the sums are even otherwise false
	 */
	boolean finished() {
		for (var playerRolls : diceRolls) {
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
		for (var player : this.diceRolls) {
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
