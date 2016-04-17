package game;

import java.util.Scanner;

public class Game {
	private String namePlayerX = "XX";
	private String namePlayerO = "OO";
	private boolean isTurnPlayerX; //
	private boolean gameover;
	private boolean session = true;
	private int[][] field = new int[4][4];
	private int turn;
	private String move;
	private String instruction;
	private boolean instructionValid;
	private Highscore score;
	private Scanner input;

	// Eine 0 auf dem Spielfeld bedeutet, dass noch nichts gesetzt wurde
	// Eine 1 auf dem Spielfeld bedeutet, dass Kreuz gesetzt wurde
	// Eine -1 auf dem Spielfeld bedeutet, dass Kreis gesetzt wurde

	public Game(Scanner input, Highscore score) {
		this.input = input;
		this.score = score;
		inputNames(input);

		isTurnPlayerX = getRandomBoolean();
		// Startspieler wird zufällig ausgewählt

		run();
	}

	public void run() {
		while (session) {
			// Eine Session besteht aus den gleichen Spielern, kann aber mehrere
			// Spiele beinhalten.

			gameover = false;
			turn = 0;

			initializeField();
			// Das Spielfeld wird mit Nullen aufgefüllt.

			System.out.println("\nDAS SPIEL BEGINNT...\n");
			while (!gameover) {
				turn++;

				if (isTurnPlayerX) {
					System.out.println(namePlayerX + ", Du bist an der Reihe:");
				} else {
					System.out.println(namePlayerO + ", Du bist an der Reihe:");
				}
				// Testeingabe
				field[0][0] = 1;
				turn++;
				// field[0][1] = -1;
				turn++;
				field[2][1] = 1;

				field[2][2] = -1;
				turn++;
				field[3][0] = 1;

				field[3][1] = -1;
				turn++;
				field[1][2] = 1;
				field[1][1] = -1;
				// field[3][0] = 1;

				outputField();
				inputMove(input);

				checkWin();
				checkDraw2();
				// Überprüft, ob Spiel gewonnen

				isTurnPlayerX = !isTurnPlayerX;
				// if (turn >= 15) {
				// // Wenn alle Felder belegt sind und es keinen Gewinner gibt
				// // -> unentschieden
				// gameover = true;
				// outputField();
				// System.out.println("UnentschiedenKo");
				// }
			}
			// Wenn das Spiel beendet ist, wird Interaktion vom Spieler
			// gefordert.
			instructionValid = false;
			while (!instructionValid) {
				System.out.println("\nMöchtet Ihr eine Revanche [R] ein Neues Spiel [N] oder Beenden [B]?");
				instruction = input.next().toUpperCase();
				if (instruction.equals("B")) {
					instructionValid = true;
					input.close();
					System.exit(0);
				} else if (instruction.equals("N")) {
					instructionValid = true;
					session = false;
				} else if (instruction.equals("R")) {
					instructionValid = true;
				} else {
					System.out.println("Ungültige Eingabe");
				}
			}
		}
	}

	private void checkDraw() {
		boolean draw = true;
		int setTo; // 1 = Kreuz, -1 = Kreis, 0 = ungesetzt
		for (int i = 0; i < 4; i++) {
			setTo = 0;
			if (!draw) {
				break;
			}
			for (int j = 0; j < 4; j++) {
				if (field[i][j] == 1 && setTo == 0) {
					setTo = 1;
					// Wenn Kreuz zum ersten Mal in der Reihe vorkommt,
					// wird setTo auf Kreuz gesetzt.
				} else if (field[i][j] == 1 && setTo == -1) {
					break;
					// Wenn Kreuz vorkommt, aber schon Kreis vorher in der Reihe
					// vorhanden war, kann in dieser Reihe kein Gewinn mehr
					// erzielt werden.
				} else if (field[i][j] == -1 && setTo == 0) {
					setTo = -1;
					// Wenn Kreis zum ersten Mal in der Reihe vorkommt,
					// wird setTo auf Kreis gesetzt.
				} else if (field[i][j] == -1 && setTo == 1) {
					break;
					// Wenn Kreis vorkommt, aber schon Kreuz vorher in der Reihe
					// vorhanden war, kann in dieser Reihe kein Gewinn mehr
					// erzielt werden.
				}
				if (j >= 3) {
					draw = false;
					System.out.println("kein");
					break;
				}
			}
		}

		if (draw) {
			gameover = true;
			System.out.println("Unentschieden");
		}

	}

	private void checkDraw2() {
		boolean Xhor;
		boolean Ohor;
		boolean Xver;
		boolean Over;
		boolean draw = true;
		boolean diag1 = false;
		boolean diag2 = false;
		for (int i = 0; i < 4; i++) {
			if (!draw) {
				break;
			}
			Ohor = false;
			Xhor = false;
			Over = false;
			Xver = false;
			// if(field[i][i] == )
			for (int j = 0; j < 4; j++) {
				if (field[i][j] == -1) {
					Ohor = true;
				} else if (field[i][j] == 1) {
					Xhor = true;
				}
				if (field[j][i] == -1) {
					Over = true;
				} else if (field[j][i] == 1) {
					Xver = true;
				}

				if (j >= 3 && !(Ohor && Xhor)) {
					draw = false;
				} else if (j >= 3 && !(Over && Xver)) {
					draw = false;
				} else if (j >= 3 && (diag1 || diag2)) {

				}
			}
		}
		if (draw) {
			System.out.println("unentschieden");
		}
	}

	private static boolean getRandomBoolean() {
		return Math.random() < 0.5;
	}

	public Highscore getHighscore() {
		return score;
	}

	private void initializeField() {
		// Alle Felder werden auf 0 gesetzt.
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				field[i][j] = 0;
			}
		}
	}

	private void checkWin() {
		// horizontal
		for (int i = 0; i < 4; i++) {
			if (field[i][0] == 0) {
				continue;
				// Wenn im ersten Feld nichts gesetzt wird, ist in dieser
				// Reihe kein Gewinn mehr möglich
			}
			for (int j = 1; j < 4; j++) {
				if (field[i][0] != field[i][j]) {
					break;
					// Wenn auf den folgenden Feldern nicht das gleiche Symbol
					// liegt, ist auch kein Gewinn in der Reihe mehr möglich
				}
				if (j >= 3) {
					// Wenn alle vier Felder in der Reihe das gleiche Symbol
					// haben, hat ein Spieler gewonnen.
					gameover = true;
					outputField();
					winMessage();
				}
			}
		}

		// vertikal
		for (int i = 0; i < 4; i++) {
			if (field[0][i] == 0) {
				continue;
				// Wenn im ersten Feld nichts gesetzt wird, ist in dieser
				// Spalte kein Gewinn mehr möglich
			}
			for (int j = 1; j < 4; j++) {
				if (field[0][i] != field[j][i]) {
					break;
					// Wenn auf den folgenden Feldern nicht das gleiche Symbol
					// liegt, ist auch kein Gewinn in der Spalte mehr möglich
				}
				if (j >= 3) {
					// Wenn alle vier Felder in der Spalte das gleiche Symbol
					// haben, hat ein Spieler gewonnen.
					gameover = true;
					outputField();
					winMessage();

				}
			}
		}
		// diagonal Nr. 1
		if (field[0][0] != 0) {
			// Wenn das Feld links-oben nicht besetzt ist, ist diagonal
			// kein Gewinn mehr möglich
			for (int j = 1; j < 4; j++) {
				if (field[0][0] != field[j][j]) {
					break;
					// Wenn auf den folgenden Feldern nicht das gleiche Symbol
					// liegt, ist auch kein Gewinn in der Diagonale mehr möglich
				}
				if (j >= 3) {
					// Wenn alle vier Felder in der Diagonale das gleiche Symbol
					// haben, hat ein Spieler gewonnen.
					gameover = true;
					outputField();
					winMessage();
				}
			}

		}
		// diagonal Nr. 2
		if (field[0][3] != 0) {
			// Wenn das Feld rechts-oben nicht besetzt ist, ist diagonal
			// kein Gewinn mehr möglich
			for (int j = 1; j < 4; j++) {
				if (field[0][3] != field[j][3 - j]) {
					break;
					// Wenn auf den folgenden Feldern nicht das gleiche Symbol
					// liegt, ist auch kein Gewinn in der Diagonale mehr möglich
				}
				if (j >= 3) {
					// Wenn alle vier Felder in der Diagonale das gleiche Symbol
					// haben, hat ein Spieler gewonnen.
					gameover = true;
					outputField();
					winMessage();
				}
			}
		}
	}

	private void winMessage() {
		drawLine();
		System.out.println("### HERZLICHEN GLÜCKWUNSCH " + winningPlayer().toUpperCase() + " DU HAST GEWONNEN! ###");
		drawLine();
		updateScore();
	}

	public void updateScore() {
		String winner = winningPlayer();
		if (score.size() == 0) {
			HighscoreEntry e = new HighscoreEntry(winner, 1, turn);
			score.addEntry(e);
		} else {
			for (int i = 0; i < score.size(); i++) {
				if (score.getElem(i).getName().equals(winner)) {
					// Wenn der Name des Gewinners schon im Highscore vorhanden ist
					// wird sein Highscore geupdatet.
					int w = score.getElem(i).getWins();
					w++;
					int m = score.getElem(i).getMoves();
					m += turn;
					score.deleteElem(i);
					// Der alte Eintrag wird gelöscht
					HighscoreEntry e = new HighscoreEntry(winner, w, m);
					score.addEntry(e);
					// und der veränderte hinzugefügt
					if (score.indexOf(e) == 0 && i != 0) {
						// Falls der veränderte Eintrag nun auf Position 0 und der
						// alte Eintrag nicht auf Position 0 stand
						drawLine();
						System.out.println("### GLÜCKWUNSCH DU HAST DEN HIGHSCORE GEKNACKT ###");
						drawLine();
					}
					break;
				} else if (i == score.size() - 1) {
					HighscoreEntry e = new HighscoreEntry(winner, 1, turn);
					score.addEntry(e);
					break;
				}
			}
		}
		score.printHighscore();
		score.saveHighscore();
	}

	private String winningPlayer() {
		if (gameover && isTurnPlayerX) {
			return namePlayerX;
		} else if (gameover && !isTurnPlayerX) {
			return namePlayerO;
		} else {
			return "";
		}
	}

	private void inputMove(Scanner input) {
		// Es wird so lange ein Zug eingegeben, bis dieser gültig ist.
		do {
			System.out.print("\nBitte gib deinen Zug ein: ");
			move = input.next();
		} while (!validMove(move));
		drawLine();
	}

	public static void drawLine() {
		System.out.println("---------------------------------------------------------------");
	}

	private boolean validMove(String move) {
		// Besteht die Eingabe nicht aus genau zwei Zeichen und einem Buchstaben als
		// erstes Zeichen und einer Zahl als zweites Zeichen, wird die Eingabe
		// wiederholt.
		if (move.length() == 2) {
			String letter;
			int j;
			try {
				letter = move.substring(0, 1).toLowerCase();
				j = Integer.parseInt(move.substring(1)) - 1;
			} catch (NumberFormatException e) {
				System.out.println("Ungültiger Zug!");
				drawLine();
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
			if (i >= 0 && i < 4 && j < 4 && field[i][j] == 0) {
				// Wenn Buchstabe und Zahl gültig und das Feld noch frei ist
				// wird das Feld mit Kreis bzw. Kreuz belegt.
				if (isTurnPlayerX) {
					field[i][j] = 1;
				} else {
					field[i][j] = -1;
				}
				return true;
			}
		}
		System.out.println("Ungültiger Zug!");
		drawLine();
		return false;

	}

	private void inputNames(Scanner input) {
		System.out.print("Bitte gebe den Namen von Kreuz ein: ");
		namePlayerX = input.next();
		System.out.print("Bitte gebe den Namen von Kreis ein: ");
		namePlayerO = input.next();
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
