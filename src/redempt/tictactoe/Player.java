package redempt.tictactoe;

public abstract class Player {
	
	private final State team;
	
	 public Player(State team, Board board) {
		 this.team = team;
	 }
	 
	 public State getTeam() {
		 return team;
	 }
	 
	 public void win(int inARow) {
		 
	 }
	 
	 public void loss(int inARow) {
		 
	 }
	 
	 public void tie(int inARow) {
		 
	 }
	 
	 public void opponentTurn() {
		 
	 }
	 
	 public abstract Piece turn();
	 
	 public void remove() {
		 
	 }
	 
}
