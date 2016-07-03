package redempt.tictactoe;

import java.util.HashSet;
import java.util.Set;

public class GameManager {
	
	Player player1;
	Player player2;
	Board board;
	boolean turn = false;
	boolean lastStarter = false;
	private Winner winner = Winner.IN_PROGRESS;
	int player1inARow = 0;
	int player2inARow = 0;
	boolean stop = false;
	int games = 0;
	
	public GameManager(Board board, Player player1, Player player2) {
		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public void start() {
		Thread thread = new Thread() {
			
			@Override
			public void run() {
				board.clear();
				while (true) {
					if (stop) {
						break;
					}
					turn();
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		};
		thread.start();
	}
	
	public void turn() {
		if (winner == Winner.X) {
			if (player1.getTeam() == State.X) {
				player1.win(player1inARow);
				player2.loss(player2inARow);
			} else {
				player2.win(player2inARow);
				player1.loss(player1inARow);
			}
			end();
			return;
		}
		if (winner == Winner.O) {
			if (player1.getTeam() == State.O) {
				player1.win(player1inARow);
				player2.loss(player2inARow);
			} else {
				player2.win(player2inARow);
				player1.loss(player1inARow);
			}
			end();
			return;
		}
		if (winner == Winner.TIE) {
			player1.tie(player1inARow);
			player2.tie(player2inARow);
			end();
			return;
		}
		Piece piece = null;
		if (turn) {
			piece = player2.turn();
			player1.opponentTurn();
		} else {
			piece = player1.turn();
			player2.opponentTurn();
		}
		if (piece != null) {
			board.setPiece(piece.getX(), piece.getY(), piece.getState());
			board.repaint();
			winner = checkForWin(piece.getX(), piece.getY());
		} else {
			end();
		}
		turn = !turn;
	}
	
	public void end() {
		board.clear();
		lastStarter = !lastStarter;
		turn = lastStarter;
		winner = Winner.IN_PROGRESS;
		player1inARow = 0;
		player2inARow = 0;
		games++;
	}
	
	public Winner checkForWin(int playedx, int playedy) {
		State state = board.getPieces()[playedx][playedy];
		State[][] pieces = board.getPieces();
		for (int tx = -1; tx < 2; tx++) {
			for (int ty = -1; ty < 2; ty++) {
				if (tx == 0 && ty == 0) {
					continue;
				}
				Set<Place> places = new HashSet<Place>();
				places.add(new Place(playedx, playedy));
				for (int iteration = 1; iteration < 3; iteration++) {
					Place place = new Place(playedx + (tx * iteration), playedy + (ty * iteration));
					if (!(place.x < 0 || place.x > 2 || place.y < 0 || place.y > 2)) {
						if (pieces[place.x][place.y] == state && !places.contains(place)) {
							places.add(place);
						}
					}
					Place second = new Place(playedx - (tx * iteration), playedy - (ty * iteration));
					if (!(second.x < 0 || second.x > 2 || second.y < 0 || second.y > 2)) {
						if (pieces[second.x][second.y] == state && !places.contains(second)) {
							places.add(second);
						}
					}
				}
				if (places.size() == 2) {
					if (state == player1.getTeam()) {
						player1inARow++;
					} else {
						player2inARow++;
					}
				}
				if (places.size() >= 3) {
					if (state == State.X) {
						return Winner.X;
					}
					if (state == State.O) {
						return Winner.O;
					}
				}
			}
		}
		int amount = 0;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (pieces[x][y] != State.FREE) {
					amount++;
				}
			}
		}
		if (amount == 9) {
			return Winner.TIE;
		}
		return Winner.IN_PROGRESS;
	}
	
	
}
