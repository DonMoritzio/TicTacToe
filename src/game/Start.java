package game;

import java.util.Scanner;

public class Start {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Highscore highscore = new Highscore();
		// Neuer Highscore wird erstellt.
		highscore.readHighscore();
		// Highscore wird unter highscore.txt gesucht. Wenn nicht gefunden
		// wird ein neuer Highscore erstellt.
		while (true) {
			// Das Spiel läuft so lange, bis es gestoppt wird.
			System.out.println("Highscore anzeigen [H], spielen [S] oder beenden [B]?");
			String instruction = input.next().toUpperCase();
			if (instruction.equals("H")) {
				highscore.printHighscore();
			} else if (instruction.equals("S")) {
				Game game = new Game(input, highscore);
				highscore = game.getHighscore();
			} else if (instruction.equals("B")) {
				System.exit(0);
			} else {
				System.out.println("Ungültige Eingabe\n");
			}
		}
	}

}
