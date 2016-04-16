package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Highscore {
	private LinkedList<HighscoreEntry> list;

	public Highscore() {
		list = new LinkedList<HighscoreEntry>();
	}

	public int size() {
		return list.size();
	}

	public HighscoreEntry getElem(int index) {
		return list.get(index);
	}

	// public void update(HighscoreEntry e){
	// if(list.contains(e));
	// }
	public void deleteElem(int index) {
		list.remove(index);
	}

	public void addEntry(HighscoreEntry e) {
		if (list.size() == 0) {
			list.add(e);
		}
		int j = list.size();
		for (int i = 0; i < j; i++) {
			if (list.get(i).getWins() < e.getWins()) {
				list.add(i, e);
				break;
			} else if (list.get(i).getWins() == e.getWins() && list.get(i).getMoves() > e.getMoves()) {
				list.add(i, e);
				break;
			} else if (i == list.size() - 1) {
				list.add(e);
			}
		}

	}

	public void printHighscore() {
		System.out.format("%8s %20s %8s %8s \r\n", "Platz", "Name", "Siege", "Züge");
		int s = list.size();
		if (s >= 10) {
			s = 10;
		}
		for (int i = 0; i < s; i++) {
			System.out.format("%8s", i + 1);
			list.get(i).print();
		}
	}

	public void saveHighscore() {
		String fileName = "highscore.txt";
		try {

			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int i = 0; i < list.size(); i++) {
				bufferedWriter.write(list.get(i).getName() + "\t" + list.get(i).getWins() + "\t" + list.get(i).getMoves());
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
		}
	}

	public void readHighscore() {
		String fileName = "highscore.txt";
		String line = null;
		String[] details = new String[3];
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				details = line.split("\\t");
				list.add(new HighscoreEntry(details[0], Integer.parseInt(details[1]), Integer.parseInt(details[2])));
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Highscore wurde nicht gefunden.\nEin neuer Highscore wurde erstellt.\n");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");

		}
	}
}
