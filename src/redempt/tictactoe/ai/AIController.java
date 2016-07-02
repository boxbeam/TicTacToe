package redempt.tictactoe.ai;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import redempt.tictactoe.Board;
import redempt.tictactoe.Piece;
import redempt.tictactoe.Place;
import redempt.tictactoe.State;

public class AIController implements Serializable {

	private static final long serialVersionUID = 3797854507235806221L;
	Stack[][] stacks = new Stack[3][3];
	public Board board;
	State state;
	
	public AIController(Board board, State state) {
		this.state = state;
		this.board = board;
		for (int x = 0; x < stacks.length; x++) {
			stacks[x] = new Stack[3];
			for (int y = 0; y < stacks[x].length; y++) {
				stacks[x][y] = new Stack(x, y, 1, state);
			}
		}
	}
	
	public AIController(Stack[][] stacks, Board board, State state) {
		this.stacks = stacks;
		this.board = board;
		this.state = state;
		for (int x = 0; x < stacks.length; x++) {
			stacks[x] = new Stack[3];
			for (int y = 0; y < stacks[x].length; y++) {
				stacks[x][y] = new Stack(x, y, 1, state);
			}
		}
	}
	
	public void neutralize() {
		stacks = new Stack[3][3];
		for (int x = 0; x < stacks.length; x++) {
			stacks[x] = new Stack[3];
			for (int y = 0; y < stacks[x].length; y++) {
				stacks[x][y] = new Stack(state);
			}
		}
	}
	
	public AIController clone() {
		return new AIController(stacks.clone(), board, state);
	}
	
	public void evolve() {
		int x = Integer.parseInt(String.valueOf(Math.round(Math.random() * 2)));
		int y = Integer.parseInt(String.valueOf(Math.round(Math.random() * 2)));
		Stack stack = stacks[x][y];
		int action = Integer.parseInt(String.valueOf(Math.round(Math.random() * 4)));
		int position = Integer.parseInt(String.valueOf(Math.round(Math.random() * (stack.nodeCount() - 1))));
		if (stack.nodeCount() >= 9) {
			action = 0;
		}
		if (stacks[x][y].nodeCount() == 0) {
			action = 1;
		}
		if (position < 0) {
			position = 0;
		}
		if (action == 0 || action == 1) {
			stack.nodes[stack.nodeCount()] = new Node(stack.x, stack.y, state);
		} else if (action == 2) {
			stack.nodes[position] = null;
		} else if (action == 3 || action == 4) {
			List<Integer> nodes = new ArrayList<>();
			for (int pos = 0; pos < 9; pos++) {
				if (stack.nodes[pos] != null) {
					nodes.add(pos);
				}
			}
			position = nodes.get((int) (Math.random() * nodes.size()));
			int reroll = Integer.parseInt(String.valueOf(Math.round(Math.random() * 2)));
			if (reroll == 0) {
				stack.nodes[position].probabilityFree = (Math.random() * 2) - 1;
			} else if (reroll == 1) {
				stack.nodes[position].probabilitySelf = (Math.random() * 2) - 1;
			} else if (reroll == 2) {
				stack.nodes[position].probabilityOpponent = (Math.random() * 2) - 1;
			}
		}
	}
	
	public Piece getAction() {
		double[][] probability = new double[3][3];
		List<Place> probabilities = new ArrayList<>();
		for (int x = 0; x < probability.length; x++) {
			probability[x] = new double[3];
			for (int y = 0; y < probability.length; y++) {
				probability[x][y] = 1;
				if (board.getPieces()[x][y] == State.FREE) {
					for (Node node : stacks[x][y].nodes) {
						if (node != null) {
							probability[x][y] += node.getProbability(board);
						}
					}
					long max = Math.round(probability[x][y] * 10);
					if (max < 0) {
						max = 0;
					}
					for (int times = 0; times < max; times++) {
						probabilities.add(new Place(x, y));
					}
				}
			}
		}
		int position = Integer.parseInt(String.valueOf(Math.round(Math.random() * (probabilities.size() - 1))));
		if (probabilities.size() == 0) {
			return null;
		}
		Place place = probabilities.get(position);
		return new Piece(place.x, place.y, state);
	}
	
	
	@Override
	public String toString() {
		try {
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(bytestream);
			stream.writeObject(this);
			stream.flush();
			String string = Base64.getEncoder().encodeToString(bytestream.toByteArray());
			return string;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static AIController fromString(String string) {
		try {
			ByteArrayInputStream bytestream  = new ByteArrayInputStream(Base64.getDecoder().decode(string));
			ObjectInputStream stream = new ObjectInputStream(bytestream);
			AIController controller = (AIController) stream.readObject();
			return controller;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
