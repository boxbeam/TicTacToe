package redempt.tictactoe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Board extends JPanel implements Serializable {
	
	private static final long serialVersionUID = -5210369600155619519L;
	private State[][] pieces = new State[3][3];
	public final transient Object lock = new Object();
	public List<String> lines = new ArrayList<>();
	
	public Board() {
		this.setSize(500, 500);
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		clear();
	}
	
	public void clear() {
		for (int x = 0; x < 3; x++) {
			pieces[x] = new State[3];
			for (int y = 0; y < 3; y++) {
				pieces[x][y] = State.FREE;
			}
		}
		repaint();
	}
	
	public void setPiece(int x, int y, State state) {
		if (state == null) {
			state = State.FREE;
		}
		pieces[x][y] = state;
		repaint();
	}
	
	public State[][] getPieces() {
		return pieces;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawLine(100, 0, 100, 300);
		g.drawLine(0, 100, 300, 100);
		g.drawLine(200, 0, 200, 300);
		g.drawLine(0, 200, 300, 200);
		g.setFont(new Font(g.getFont().getName(), 1, 90));
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				State piece = pieces[x][y];
				if (piece == State.X) {
					g.setColor(Color.RED);
					g.drawString("X", (x * 100) - 80 + 100, (y * 100) - 20 + 100);
				}
				if (piece == State.O) {
					g.setColor(Color.BLUE);
					g.drawString("O", (x * 100) - 80 + 100, (y * 100) - 20 + 100);
				}
			}
		}
		int position = 1;
		List<String> lines;
		synchronized (lock) {
			lines = new ArrayList<>(this.lines);
		}
		for (String string : lines) {
			g.setColor(Color.BLACK);
			g.setFont(new Font(g.getFont().getName(), 1, 12));
			g.drawString(string, 10, 300 + (position * 20));
			position++;
		}
	}
	
}
