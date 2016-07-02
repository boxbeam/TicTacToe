package redempt.tictactoe.ai;

import java.io.Serializable;

import redempt.tictactoe.State;

public class Stack implements Serializable {
	
	private static final long serialVersionUID = 6304631903038523535L;
	Node[] nodes = new Node[9];
	int x;
	int y;
	State state;
	
	public Stack(int x, int y, int size, State state) {
		this.state = state;
		this.x = x;
		this.y = y;
		int[] availableX = new int[9];
		int[] availableY = new int[9];
		int pos = 0;
		for (int px = 0; px < 3; px++) {
			for (int py = 0; py < 3; py++) {
				availableX[pos] = px;
				availableY[pos] = py;
				pos++;
			}
		}
		for (int i = 0; i < size; i++) {
			nodes[i] = new Node(x, y, state);
		}
	}
	
	public Stack(State state, Node[] nodes) {
		this.state = state;
		this.nodes = nodes;
	}
	
	public Stack(State state) {
		nodes = new Node[9];
	}
	
	public int nodeCount() {
		int count = 0;
		for (Node node : nodes) {
			if (node != null) {
				count++;
			}
		}
		return count;
	}
	
	public Stack clone() {
		Node[] nodes = new Node[9];
		for (int i = 0; i < 9; i++) {
			if (this.nodes[i] != null) {
				nodes[i] = this.nodes[i].clone();
			}
		}
		return new Stack(state, nodes);
	}
	
}
