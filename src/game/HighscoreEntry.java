package game;

public class HighscoreEntry {
	private String name;
	private int wins;
	private int moves;

	public HighscoreEntry(String name) {
		this.name = name;
	}

	public HighscoreEntry(String name, int wins, int moves) {
		this.name = name;
		this.wins = wins;
		this.moves = moves;
	}

	public void print() {
		System.out.format("%21s %8s %8s \r\n", name, wins, moves);
	}

	public String getName() {
		return name;
	}

	public int getWins() {
		return wins;
	}

	public int getMoves() {
		return moves;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

}
