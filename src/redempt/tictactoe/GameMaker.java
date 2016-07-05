package redempt.tictactoe;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameMaker extends JFrame {
	
	public Board board = new Board();
	public Board teaching = new Board();
	public GameManager manager;
	public boolean finished = false;
	public State turn = State.O;
	public Piece[] turns = new Piece[9];
	public AI student;
	public int turnNum = 0;
	
	public GameMaker() {
		GameMaker maker = this;
		this.setSize(320, 400);
		this.setTitle("Game editor");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(board);
		board.setLayout(null);
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (!finished) {
					int x = e.getX() / 100;
					int y = e.getY() / 100;
					if (board.getPieces()[x][y] == State.FREE) {
						Piece piece = new Piece(x, y, turn);
						turn = turn.getOpposite();
						board.setPiece(piece.getX(), piece.getY(), piece.getState());
						turns[turnNum] = piece;
						turnNum++;
					}
				}
			}
			
		});
		
		JButton teach = new JButton("Teach");
		teach.setSize(100, 50);
		teach.setLocation(100, 300);
		teach.setVisible(true);
		teach.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					finished = true;
					teaching.manager = manager;
					AI player1 = new AI(teaching, State.O, true);
					player1.controller = Main.o.controller.clone();
					Teacher player2 = new Teacher(State.X, maker);
					student = player1;
					manager = new GameManager(teaching, player1, player2);
					maker.remove(board);
					maker.add(teaching);
					maker.repaint();
					Main.autostop = false;
					manager.start();
				}
			}
			
		});
		board.add(teach);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public void end() {
		student = (AI) manager.player1;
		
	}
	
}
