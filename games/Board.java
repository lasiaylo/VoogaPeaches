

public class Board {

	private final int NUM_KINGS = 1;
	private final int NUM_QUEENS = 1;
	private final int NUM_ROOKS = 2;
	private final int NUM_BISHOPS = 2;
	private final int NUM_KNIGHTS = 2;
	private final int NUM_PAWNS = 8;

	
	private boolean[][] occupied;
	
	public Board(int columns, int rows) {
		occupied = new boolean[columns][rows];
	}
	
	
	private void makePieces() {
		
		
	}
	
	
}
