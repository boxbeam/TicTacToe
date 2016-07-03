package redempt.tictactoe;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HumanPlayer extends Player {
	
	Piece turn = null;
	private boolean nullify = true;
	private MouseAdapter mouse;
	private Board board;
	
	public HumanPlayer(State state, Board board) {
		super(state, board);
		this.board = board;
		mouse = new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getX() <= 300 && e.getY() <= 300) {
					int x = (e.getX()) / 100;
					int y = (e.getY()) / 100;
					if (board.getPieces()[x][y] == State.FREE) {
						turn = new Piece(x, y, getTeam());
					}
				}
			}
			
		};
		board.addMouseListener(mouse);
	}

	@Override
	public Piece turn() {
		if (nullify) {
			turn = null;
		}
		while (turn == null) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Piece t = turn;
		turn = null;
		nullify = true;
		return t;
	}
	
	@Override
	public void remove() {
		board.removeMouseListener(mouse);
	}

}
