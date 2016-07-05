package redempt.tictactoe;

public class Teacher extends Player {
	
	private int turnNum = 0;
	private int gameNum = 0;
	private GameMaker maker;
	
	public Teacher(State team, GameMaker maker) {
		super(team, maker.teaching);
		this.maker = maker;
	}

	@Override
	public Piece turn() {
		while (maker.turns[turnNum].getState() != getTeam()) {
			turnNum++;
		}
		
		if (gameNum >= 1000) {
			maker.manager.stop = true;
		}
		
		if (turnNum != 0) {
			int x = maker.turns[turnNum - 1].getX();
			int y = maker.turns[turnNum - 1].getY();
			if (maker.teaching.getPieces()[x][y] != maker.turns[turnNum - 1].getState()) {
				if (getTeam() == State.O) {
					maker.manager.winner = Winner.O;
				} else {
					maker.manager.winner = Winner.X;
				}
				gameNum++;
			}
		}
		turnNum++;
		return maker.turns[turnNum];
	}
	
	@Override
	public void win(int nil) {
		maker.manager.stop = true;
	}
	
	@Override
	public void tie(int nil) {
		maker.manager.stop = true;
	}
	
	@Override
	public void loss(int nil) {
		maker.manager.stop = true;
	}
	
	public void end() {
		maker.manager.stop = true;
	}
	
}
