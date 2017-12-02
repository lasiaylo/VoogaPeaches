package puzzle.chess;

public class Chess {	
	private static final int numColumns = 8;
	private static final int numRows = 8;
	
	private Board myBoard;
	private Player player1;
	private Player player2;
	
	public Chess() {
		myBoard = new Board(numColumns, numRows);
		player1 = new Player(Player1);
		player2 = new Player(Player2);
	}
	
	
	
}