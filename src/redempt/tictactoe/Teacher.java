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
				maker.student.loss(maker.manager.player2inARow);
				maker.manager.reset();
				gameNum++;
				System.out.println("Failure");
			}
		}
		turnNum++;
		return maker.turns[turnNum];
	}
	
	@Override
	public void win(int nil) {
		maker.manager.stop = true;
		System.out.println("win");
	}
	
	@Override
	public void tie(int nil) {
		maker.manager.stop = true;
		System.out.println("tie");
	}
	
	@Override
	public void loss(int nil) {
		maker.manager.stop = true;
		System.out.println("loss");
	}
	
}