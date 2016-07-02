package redempt.tictactoe.ai;

import java.io.Serializable;

import redempt.tictactoe.Board;
import redempt.tictactoe.State;

public class Node implements Serializable {
	
	private static final long serialVersionUID = 1976002362460001635L;
	int x;
	int y;
	int affectedx;
	int affectedy;
	double probabilityFree;
	double probabilitySelf;
	double probabilityOpponent;
	int position;
	State state;
	
	public Node(int x, int y, int affectedx, int affectedy, double probabilityFree, double probabilitySelf, double probabilityOpponent, State state) {
		this.state = state;
		this.x = x;
		this.y = y;
		this.affectedx = affectedx;
		this.affectedy = affectedy;
		this.probabilityFree = probabilityFree;
		this.probabilitySelf = probabilitySelf;
		this.probabilityOpponent = probabilityOpponent;
	}
	
	public Node clone() {
		return new Node(x, y, affectedx, affectedy, probabilityFree, probabilitySelf, probabilityOpponent, state);
	}
	
	public Node(int x, int y, State state) {
		this.state = state;
		this.x = x;
		this.y = y;
		position = Integer.parseInt(String.valueOf(Math.round(Math.random() * 2)));
		this.affectedx = Integer.parseInt(String.valueOf(Math.round(Math.random() * 2)));
		this.affectedy = Integer.parseInt(String.valueOf(Math.round(Math.random() * 2)));
		this.probabilityFree = (Math.random() * 2) - 1;
		this.probabilitySelf = (Math.random() * 2) - 1;
		this.probabilityOpponent = (Math.random() * 2) - 1;
	}
	
	public double getProbability(Board board) {
		State state = board.getPieces()[x][y];
		if (board.getPieces()[affectedx][affectedy] != State.FREE) {
			return 0;
		}
		if (state == State.FREE) {
			return probabilityFree;
		} else if (state == this.state) {
			return probabilitySelf;
		} else {
			return probabilityOpponent;
		}
	}
	
}
