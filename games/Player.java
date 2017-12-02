
public class Player {

	private String playerName;
	private int matchesPlayed;
	private int matchesWon;
	
	
	public Player(String name) {
		playerName = name;
		matchesPlayed = 0;
		matchesWon = 0;
	}
	
	private int getPlayerName() {
		return playerName;
	}
	
	private int getMatchesPlayed() {
		return matchesPlayed;
	}
	
	private int getMatchesWon() {
		return matchesWon;
	}
	
	private int getWinPercent() {
		return (matchesWon * 100)/matchesPlayed;
	}
	
	private void updateMatchesPlayed() {
		matchesPlayed++;
	}
	
	private void updateMatchesWon() {
		matchesWon++;
	}
}