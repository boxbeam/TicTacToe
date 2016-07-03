package redempt.tictactoe;

public enum State {
	X,
	O,
	FREE;
	
	public State getOpposite() {
		if (this == State.O) {
			return State.X;
		}
		if (this == State.X) {
			return State.O;
		}
		return null;
	}
	
}
