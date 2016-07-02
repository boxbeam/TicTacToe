package redempt.tictactoe;

public class Piece {
	
	private final int x;
	private final int y;
	private final State state;
	
	public Piece(int x, int y, State state) {
		this.x = x;
		this.y = y;
		this.state = state;
		if (state == State.FREE) {
			throw new IllegalArgumentException("State must be X or O");
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public State getState() {
		return state;
	}
	
}
