package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Start {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		createHighscore();
		showHighscore();
		// while (true) {
		// System.out.println("Highscore anzeigen [H] oder spielen [S]?");
		// String instruction = input.next().toUpperCase();
		// if (instruction.equals("H")) {
		// showHighscore();
		// } else if (instruction.equals("S")) {
		// Game game = new Game(input);
		// } else {
		// System.out.println("Ungültige Eingabe\n");
		// }
		// }
	}

	private static void showHighscore() {
		String fileName = "highscore.txt";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("'" + fileName + "' wurde nicht gefunden.");
			System.out.println("Ein neuer Highscore wird erstellt.\n");
			createHighscore();
			showHighscore();
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");

		}
	}

	private static void createHighscore() {
		String fileName = "highscore.txt";
		try {

			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write("HIGHSCORE TIC TAC TOE:");
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			bufferedWriter.write(String.format("%8s %20s %8s %8s \r\n", "Platz", "Name", "Siege", "Züge"));
			// bufferedWriter.newLine();
			bufferedWriter.write(String.format("%8s %20s %8s %8s \r\n", 1, "Moritz", 45, 5));
			bufferedWriter.write(String.format("%8s %20s %8s %8s \r\n", 2, "John", 10, 12));

			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
		}
	}
}
