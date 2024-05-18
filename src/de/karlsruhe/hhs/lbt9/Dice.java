package de.karlsruhe.hhs.lbt9;

import java.util.Random;

/**
 * Provides an n sided dice for random dice rolls
 */
public class Dice {

	private final int sides;
	private Random random;
	
	Dice(int sides, long randomSeed) {
		this.sides = sides;
		this.random = new Random(randomSeed);
	}
	
	/**
	 * Rolls an n sided dice
	 * @return a number in the range [1, n] evenly distributed
	 */
	public int roll() {
		// +1 because nextInt is in the range of [0, this.sides)
		return this.random.nextInt(this.sides) + 1;
	}
}
