import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * 
 * @author Anjana, Ashutosh, Katie, Nicole, Rupal This will be our main class.
 *
 */

public class Board extends JPanel {

	static Board board = new Board(); // JPanel
	static int width = 800; // panel width
	static int height = 600; // panel height
	static int enemyRow = 3;
	static int enemyCol = 8;
	static boolean gameOver = true;

	static ArrayList<ArrayList<SmallEnemy>> enemies = new ArrayList<ArrayList<SmallEnemy>>();

	public static void main(String[] args) {

		// Just some copy pasted code from a previous project to setup JPanel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Space Invaders");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(board);
		board.setPreferredSize(new Dimension(width, height));
		board.setBackground(Color.BLACK);
		frame.pack();
		frame.setVisible(true);
		board.setUpKeyMappings(); // sets up the keys' (left, right, spcae) functionalities
		createEnemies();
		board.repaint();

	}

	public static void createEnemies() {
		for(int r = 0; r< enemyRow; r++) {
			ArrayList<SmallEnemy> enemyRow = new ArrayList<SmallEnemy>();
			for(int c = 0; c< enemyCol; c++) {
				SmallEnemy enemy = new SmallEnemy(r * 100,c * 100, "Enemy1.png");
				enemyRow.add(enemy);
			}
			enemies.add(enemyRow);
		}
		gameOver = false;

	}

	private void setUpKeyMappings() {

		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");

		/*
		 * THIS IS WHERE YOU WRITE THE METHOD WHEN ARROW KEYS ARE PRESSED
		 */
		this.getActionMap().put("right", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ADD implementation of right key here
				System.out.println("Right Key Pressed");
			}
		});

		this.getActionMap().put("left", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ADD implementation of left key here
				System.out.println("Left Key Pressed");

			}
		});

		this.getActionMap().put("space", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ADD implementation of space key here
				System.out.println("Space Key Pressed");

			}
		});

		this.requestFocusInWindow();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(!gameOver) {
			System.out.println("Painting");
			for(int r = 0; r< enemyRow; r++) {
				for(int c = 0; c< enemyCol; c++) {
					
					SmallEnemy enemy = enemies.get(r).get(c);
					enemy.setWidth(enemy.getImage().getWidth()/8);
					enemy.setHeight(enemy.getImage().getHeight()/8);
					enemy.paintComponent(g);
				}
			}
		}
		
		
	}
}
