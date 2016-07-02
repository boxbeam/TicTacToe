package redempt.tictactoe;

import redempt.tictactoe.ai.AIController;

public class AI extends Player {
	
	public static final int WIN = 3;
	public static final int TIE = 2;
	public static final int LOSS = 1;
	AIController controller;
	AIController lastState;
	private double lastFitness = 0;
	private int turns = 0;
	private int fitnesses = 0;
	private int games = 0;
	private boolean evolve;
	Board board;

	public AI(Board board, State state, boolean evolve) {
		super(state, board);
		this.board = board;
		this.evolve = evolve;
		controller = new AIController(board, state);
		lastState = controller.clone();
	}

	@Override
	public Piece turn() {
		turns++;
		return controller.getAction();
	}
	
	@Override
	public void opponentTurn() {
		turns++;
	}
	
	@Override
	public void win(int inARow) {
		end(inARow, 1.5);
	}
	
	public void end(int inARow, double outcome) {
		games++;
		turns = 0;
		double heuristic = heuristic(turns, inARow, outcome);
		fitnesses += heuristic;
		if (games >= 10) {
			System.out.println("Average fitness for " + getTeam() + " is " + fitnesses);
			board.lines.clear();
			board.lines.add(getTeam() + " fitness: " + fitnesses);
			if (fitnesses <= lastFitness) {
				controller = lastState.clone();
				if (evolve) {
					controller.evolve();
					Main.save();
				}
			} else {
				lastState = controller.clone();
				if (evolve) {
					controller.evolve();
					Main.save();
				}
			}
			lastFitness = fitnesses;
			fitnesses = 0;
			games = 0;
		}
	}
	
	@Override
	public void tie(int inARow) {
		end(inARow, 1.2);
	}
	
	@Override
	public void loss(int inARow) {
		end(inARow, 1);
	}
	
	public static double heuristic(int turns, int inARow, double outcome) {
		return (turns + inARow) * outcome;
	}
	
}
