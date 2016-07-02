package redempt.tictactoe;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

import redempt.tictactoe.ai.AIController;

public class Main {
	
	public static AI o;
	public static Board board;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setTitle("Tic tac toe");
		board = new Board();
		Player x = new SeriesAI(State.X, board);
		o = new AI(board, State.O, true);
		o.controller.neutralize();
		GameManager manager = new GameManager(board, o, x);
		frame.add(board);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					manager.turn();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					manager.start();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_T) {
					manager.stop = !manager.stop;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_S) {
					save();
					
				}
				
				if (e.getKeyCode() == KeyEvent.VK_L) {
					load();
				}
			}
			
		});
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
