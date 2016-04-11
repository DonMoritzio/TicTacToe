package game;

import java.util.Scanner;

public class Game {
	private String namePlayerX = "XX";
	private String namePlayerO = "OO";
	private boolean isTurnPlayerX; //
	private boolean gameover = false;
	private int[][] field = new int[4][4];
	private int turn = 0;
	private String move;
	// Eine 0 auf dem Spielfeld bedeutet, dass noch nichts gesetzt wurde
	// Eine 1 auf dem Spielfeld bedeutet, dass Kreuz gesetzt wurde
	// Eine -1 auf dem Spielfeld bedeutet, dass Kreis gesetzt wurde

	public Game() {
		Scanner input = new Scanner(System.in);
		initializeField(); // Das Spielfeld wird mit Nullen aufgefüllt.
		// inputNames(input);
		isTurnPlayerX = getRandomBoolean();
		// System.out.println(namePlayerX + " " + namePlayerO + " " +
		// isTurnPlayerX);
		while (!gameover) {
			turn++;
			if (isTurnPlayerX) {
				System.out.println(namePlayerX + ", Du bist an der Reihe:");
			} else {
				System.out.println(namePlayerO + ", Du bist an der Reihe:");
			}
			//Testeingabe
			field[0][0] = 1; turn++;
			field[0][1] = -1; turn++;
			field[1][0] = 1; turn++;
			//field[2][0] = 1;
			// field[3][0] = 1;
			outputField();
			inputMove(input);

			check();

			isTurnPlayerX = !isTurnPlayerX;
			if (turn >= 15) {
				gameover = true;
				outputField();
				System.out.println("Unentschieden");
			}
		}
		input.close();
	}

	private void initializeField() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				field[i][j] = 0;
			}
		}
	}

	private void check() {
		// horizontal
		for (int i = 0; i < 4; i++) {
			if (field[i][0] == 0) {
				// System.out.println("Continue auf " + i);
				continue;

			}
			for (int j = 1; j < 4; j++) {
				if (field[i][0] != field[i][j]) {
					// System.out.println("Horbreak " + j);
					break;
				}
				if (j >= 3) {
					//System.out.println("GewonnenHor");
					gameover = true;
					outputField();
					winMessage();
					
				}
			}
		}

		// vertical
		for (int i = 0; i < 4; i++) {
			if (field[0][i] == 0) {
				// System.out.println("VerContinue auf " + i);
				continue;
			}
			for (int j = 1; j < 4; j++) {
				if (field[0][i] != field[j][i]) {
					// System.out.println("Verbreak " + j);
					break;
				}
				if (j >= 3) {
					//System.out.println("GewonnenVertical" + i + " " + j);
					gameover = true;
					outputField();
					winMessage();
					
				}
			}
		}
	}

	private void winMessage() {
		if (gameover && isTurnPlayerX) {
			System.out.println(namePlayerX + ", Du hast das Spiel gewonnen!");
		} else if (gameover && !isTurnPlayerX) {
			System.out.println(namePlayerO + ", Du hast das Spiel gewonnen!");
		}
	}

	private void inputMove(Scanner input) {

		do {
			System.out.print("\nBitte gib deinen Zug ein: ");
			move = input.next();
		} while (!validMove(move));
		System.out.println("---------------------------------------");

	}

	private boolean validMove(String move) {
		if (move.length() == 2) {
			String letter;
			int j;
			try {
				letter = move.substring(0, 1).toLowerCase();
				j = Integer.parseInt(move.substring(1)) - 1;
			} catch (NumberFormatException e) {
				System.out.println("Ungültiger Zug!");
				return false;
			}
			int i = -1;
			switch (letter) {
			case "a":
				i = 0;
				break;
			case "b":
				i = 1;
				break;
			case "c":
				i = 2;
				break;
			case "d":
				i = 3;
				break;
			default:
				i = -1;
				break;
			}
			// System.out.println(i+" "+j);
			if (i < 4 && j < 4 && field[i][j] == 0) {
				if (isTurnPlayerX) {
					field[i][j] = 1;
				} else {
					field[i][j] = -1;
				}
				return true;
			}
		}
		System.out.println("Ungültiger Zug!");
		return false;

	}

	private void inputNames(Scanner input) {
		System.out.print("Bitte gebe den Namen von Kreuz ein: ");
		namePlayerX = input.next();
		System.out.print("Bitte gebe den Namen von Kreis ein: ");
		namePlayerO = input.next();
		System.out.println("DAS SPIEL BEGINNT...");
	}

	private boolean getRandomBoolean() {
		return Math.random() < 0.5;
	}

	public void outputField() {
		System.out.format("%n%10s%4s%4s%4s%n", "1", "2", "3", "4");
		System.out.format("%24s%n", "+---+---+---+---+");
		for (int i = 0; i < 4; i++) {
			outputLine(i);
		}
	}

	private void outputLine(int row) {
		String r = "";
		String symbol = "";
		switch (row) {
		case 0:
			r = "A";
			break;
		case 1:
			r = "B";
			break;
		case 2:
			r = "C";
			break;
		case 3:
			r = "D";
			break;
		}
		System.out.format("%6s%2s", r, "|");
		for (int i = 0; i < 4; i++) {
			// System.out.println(row + " " + i);
			if (field[row][i] == -1) {
				symbol = "O";
			} else if (field[row][i] == 1) {
				symbol = "X";
			} else {
				symbol = "";
			}
			System.out.format("%2s%2s", symbol, "|");
		}
		System.out.format("%n%24s%n", "+---+---+---+---+");
	}

}
