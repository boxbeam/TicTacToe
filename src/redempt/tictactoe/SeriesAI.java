package redempt.tictactoe;

public class SeriesAI extends Player {
	
	Board board;
	State team;

	public SeriesAI(State team, Board board) {
		super(team, board);
		this.team = team;
		this.board = board;
	}

	@Override
	public Piece turn() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (board.getPieces()[x][y] == State.FREE) {
					return new Piece(x, y, team);
				}
			}
		}
		return null;
	}

}
