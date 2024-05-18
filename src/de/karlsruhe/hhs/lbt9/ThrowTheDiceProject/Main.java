package de.karlsruhe.hhs.lbt9.ThrowTheDiceProject;

// Lösung für die auf dem Word-Dokument beschriebenen Aufgaben 

public class Main {

	// 1. Entwerfen Sie eine Java-Methode deinmethodennamezumwürfeln() die bei
	// Aufruf ein entsprechendes Ergebnis zwischen eins und sechs zurückgibt
	// (return). Nutzen Sie hierfür die folgende Funktion: int meinwürfel =
	// (int)(Math.random() * 6 + 1);
	static int deinemethodennamezumwuerfeln() {
		int meinwuerfel = (int) (Math.random() * 6 + 1);
		return meinwuerfel;
	}

	static boolean pruefe(int[][] wuerfelergebnisse) {
		for (var playerRolls : wuerfelergebnisse) {
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

	static void fuellewuerfelergebnisse(int[][] ergebnisse) {
		for (var player : ergebnisse) {
			for (int i = 0; i < player.length; ++i) {
				player[i] = deinemethodennamezumwuerfeln();
			}
		}
	}

	static void ausgabe(int[][] ergebnisse) {
		StringBuilder sb = new StringBuilder();
		sb.append("Spielrunde{\n");
		for (var playerRolls : ergebnisse) {
			int sum = 0;
			sb.append("\tSpieler[");
			for (var playerRoll : playerRolls) {
				sb.append(playerRoll).append(",");
				sum += playerRoll;
			}
			sb.append("] Summe=").append(sum).append("\n");
		}
		sb.append("}\n");
		System.out.println(sb.toString());
	}

	public static void main(String[] args) {
		final int PLAYERS = 5;
		final int DICE_ROLLS = 10;

		// 2. Rufen Sie die Methode deinmethodennamezumwürfeln() zehnmal auf.
		// Die Ergebnisse schreiben Sie dabei in einem
		// Array „meinewürfelergebnisse“ ab.
		int[] meinewuerfelergebnisse = new int[DICE_ROLLS];
		for (int i = 0; i < meinewuerfelergebnisse.length; ++i) {
			meinewuerfelergebnisse[i] = deinemethodennamezumwuerfeln();
		}

		// 3. Jetzt sollen fünf Gamer den Würfel zehnmal würfeln. Die
		// Ergebnisse werden in einem 2D-Array mit
		// Namen „meine2Dwürfelergebnisse“ abgespeichert.
		int[][] meine2Dwuerfelergebnisse = new int[PLAYERS][DICE_ROLLS];

		// 4. Es sollen nun die Würfe jedes einzelnen Gamers überprüft werden,
		// ob die Summe aus allen Würfen durch 2 teilbar ist. Falls die Summe
		// nicht durch zwei teilbar ist, sollen die Würfe solange wiederholt
		// und abgespeichert werden, bis die Summe durch zwei teilbar wird.
		boolean allesummengerade = false;
		while (!allesummengerade) {
			fuellewuerfelergebnisse(meine2Dwuerfelergebnisse);
			ausgabe(meine2Dwuerfelergebnisse);
			allesummengerade = pruefe(meine2Dwuerfelergebnisse);
		}
	}

}
