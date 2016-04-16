package game;

import java.util.Scanner;

public class Start {

	public static void main(String[] args) {
		//test
		Scanner input = new Scanner(System.in);
		Highscore highscore = new Highscore();
		highscore.readHighscore();
		while (true) {
			System.out.println("Highscore anzeigen [H], spielen [S] oder beenden [B]?");
			String instruction = input.next().toUpperCase();
			if (instruction.equals("H")) {
				highscore.printHighscore();
			} else if (instruction.equals("S")) {
				Game game = new Game(input, highscore);
				highscore = game.getHighscore();
			} else if (instruction.equals("B")) {
				highscore.saveHighscore();
				System.exit(0);
			} else {
				System.out.println("Ungültige Eingabe\n");
			}
		}
	}

}
