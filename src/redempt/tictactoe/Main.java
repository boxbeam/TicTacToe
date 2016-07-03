package redempt.tictactoe;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import redempt.tictactoe.ai.AIController;
import redempt.tictactoe.scheduler.Task;

public class Main {
	
	public static AI o;
	public static Board board;
	public static Player x;
	public static boolean autostop = true;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(400, 450);
		frame.setTitle("Tic tac toe");
		board = new Board();
		x = new AI(board, State.X, false);
		((AI) x).controller.neutralize();
		o = new AI(board, State.O, true);
		o.controller.neutralize();
		GameManager manager = new GameManager(board, o, x);
		board.manager = manager;
		frame.add(board);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		File file = new File("save");
		if (file.exists()) {
			load();
		}
		board.setLayout(null);
		JButton turn = new JButton();
		turn.setSize(100, 50);
		turn.setText("Turn");
		turn.setVisible(true);
		turn.setLocation(300, 0);
		turn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				manager.turn(true);
			}
			
		});
		board.add(turn);
		JButton start = new JButton();
		start.setSize(100, 50);
		start.setText("Start");
		start.setVisible(true);
		start.setLocation(300, 50);
		start.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				manager.stop = false;
				manager.start();
			}
			
		});
		board.add(start);
		JButton stop = new JButton();
		stop.setSize(100, 50);
		stop.setText("Stop");
		stop.setVisible(true);
		stop.setLocation(300, 100);
		stop.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				manager.stop = true;
			}
			
		});
		board.add(stop);
		JButton save = new JButton();
		save.setSize(100, 50);
		save.setText("Save");
		save.setVisible(true);
		save.setLocation(300, 150);
		save.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				save();
			}
			
		});
		board.add(save);
		JButton load = new JButton();
		load.setSize(100, 50);
		load.setText("Load");
		load.setVisible(true);
		load.setLocation(300, 200);
		load.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				load();
			}
			
		});
		board.add(load);
		JButton reset = new JButton();
		reset.setSize(100, 50);
		reset.setText("Reset AI");
		reset.setVisible(true);
		reset.setLocation(300, 250);
		reset.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				o.remove();
				o = new AI(board, State.O, true);
				o.controller.neutralize();
				if (manager.player1.getTeam() == State.O) {
					manager.player1 = o;
				} else {
					manager.player2 = o;
				}
			}
			
		});
		board.add(reset);
		JButton clear = new JButton();
		clear.setSize(100, 50);
		clear.setText("Clear");
		clear.setVisible(true);
		clear.setLocation(300, 300);
		clear.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				board.clear();
			}
			
		});
		board.add(clear);
		JButton random = new JButton();
		random.setSize(100, 50);
		random.setText("Random");
		random.setVisible(true);
		random.setLocation(0, 300);
		random.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				x.remove();
				x = new AI(board, State.X, false);
				((AI) x).controller.neutralize();
				if (manager.player1.getTeam() == State.X) {
					manager.player1 = x;
				} else {
					manager.player2 = x;
				}
			}
			
		});
		board.add(random);
		JButton predictable = new JButton();
		predictable.setSize(100, 50);
		predictable.setText("Predictable");
		predictable.setVisible(true);
		predictable.setLocation(100, 300);
		predictable.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				x.remove();
				x = new SeriesAI(State.X, board);
				if (manager.player1.getTeam() == State.X) {
					manager.player1 = x;
				} else {
					manager.player2 = x;
				}
			}
			
		});
		board.add(predictable);
		JButton learning = new JButton();
		learning.setSize(100, 50);
		learning.setText("Learning");
		learning.setVisible(true);
		learning.setLocation(200, 300);
		learning.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				x.remove();
				x = new AI(board, State.X, true);
				((AI) x).controller.neutralize();
				if (manager.player1.getTeam() == State.X) {
					manager.player1 = x;
				} else {
					manager.player2 = x;
				}
			}
			
		});
		board.add(learning);
		JButton human = new JButton();
		human.setSize(100, 50);
		human.setText("Human");
		human.setVisible(true);
		human.setLocation(100, 350);
		human.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				x.remove();
				x = new HumanPlayer(State.X, board);
				if (manager.player1.getTeam() == State.X) {
					manager.player1 = x;
				} else {
					manager.player2 = x;
				}
			}
			
		});
		board.add(human);
		JCheckBox evolve = new JCheckBox();
		evolve.setSize(100, 25);
		evolve.setLocation(0, 350);
		evolve.setText("Evolve");
		Task task = new Task(new Runnable() {
			@Override
			public void run() {
				if (evolve.isSelected() != o.evolve) {
					evolve.setSelected(o.evolve);
				}
			}
		}, true, 100);
		evolve.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				o.evolve = !evolve.isSelected();
			}
			
		});
		task.start();
		evolve.setVisible(true);
		board.add(evolve);
		
		JCheckBox autostopToggle = new JCheckBox();
		autostopToggle.setSize(100, 25);
		autostopToggle.setLocation(0, 375);
		autostopToggle.setText("Autostop");
		autostopToggle.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				autostop = !autostop;
			}
			
		});
		Task autostopTask = new Task(new Runnable() {
			
			@Override
			public void run() {
				autostopToggle.setSelected(autostop);
			}
			
		}, true, 1000);
		autostopTask.start();
		autostopToggle.setVisible(true);
		board.add(autostopToggle);
		
		board.repaint();
	}
	
	public static void save() {
		try {
			String value = o.controller.toString();
			File file = new File("save");
			if (!file.exists()) {
					file.createNewFile();
			}
			FileWriter writer = new FileWriter(file);
			writer.write(value);
			writer.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void load() {
		try {
			File file = new File("save");
			if (!file.exists()) {
					file.createNewFile();
			}
			FileReader reader = new FileReader(file);
			BufferedReader buffered = new BufferedReader(reader);
			String combined = "";
			String next = "";
			while ((next = buffered.readLine()) != null) {
				combined += next;
			}
			AIController controller = AIController.fromString(combined);
			controller.board = board;
			o.controller = controller;
			buffered.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
}
