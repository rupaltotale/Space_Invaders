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
	static int width = 1200; // panel width
	static int height = 800; // panel height
	static int margin = 100;

	static boolean gameOver = true;

	static int time = 30; // in milliseconds
	static Timer timer = new Timer(time, null);

	static ArrayList<ArrayList<Enemy>> enemies = new ArrayList<ArrayList<Enemy>>();
	static int enemyRow = 3;
	static int enemyCol = 12;
	static int shiftBy = 3;

	static Spaceship spaceship = new Spaceship(height - 100, 100);
	static int moveLimit = 75;
	static int movedBy = moveLimit;
	static int direction = 0; // -1 is left, 1 is right
	static boolean shootProjectile = true;

	static ArrayList<Projectile> sProjectiles = new ArrayList();

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
		setupTimer();

	}

	private void setUpKeyMappings() {

		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");

		this.getActionMap().put("right", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ADD implementation of right key here
				// System.out.println("Right Key Pressed");
				direction = 1;
				movedBy = 0;
			}
		});

		this.getActionMap().put("left", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ADD implementation of left key here
				// System.out.println("Left Key Pressed");
				direction = -1;
				movedBy = 0;

			}
		});

		this.getActionMap().put("space", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ADD implementation of space key here
				System.out.println("Space Key Pressed");
				Projectile projectile = new Projectile("Rocket");
				int row = spaceship.getRow() - projectile.getHeight();
				int col = spaceship.getCol() + spaceship.getWidth() / 2 - projectile.getWidth() / 2;
				projectile.setLocation(row, col);
				// projectile.setColor(Color.decode("#F7DC6F"));
				// Color.decode("#F7DC6F")
				sProjectiles.add(projectile);

			}
		});

		this.requestFocusInWindow();

	}

	public static void createEnemies() {
		int spacing = (width - margin * 2) / enemyCol;
		for (int r = 0; r < enemyRow; r++) {
			ArrayList<Enemy> enemyRow = new ArrayList<Enemy>();
			for (int c = 0; c < enemyCol; c++) {
				Enemy enemy = new Enemy(r * spacing, c * spacing + margin, "Enemy1.png");
				enemyRow.add(enemy);
			}
			enemies.add(enemyRow);
		}
		gameOver = false;

	}

	private static void setupTimer() {
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tick();
				board.repaint();
			}

		});
		timer.start();

	}

	protected static void tick() {
		moveEnemies();
		moveSpaceship();
		shootSpaceshipProjectile();

	}

	private static void shootSpaceshipProjectile() {
		if (sProjectiles.size() > 0) {
			for (int i = 0; i < sProjectiles.size(); i++) {
				Projectile projectile = sProjectiles.get(i);
				if (projectile.getRow() < 0) {
					sProjectiles.remove(projectile);
				} else {
					projectile.move();
					ArrayList<Integer> loc= new ArrayList();
//					loc.add(projectile.getRow());
//					loc.add(projectile.getCol());
//					for (int r = 0; r < enemies.size(); r++) {
//						Enemy enemy = enemies.get(r).get(enemies.get(r).size() - 1);
//						enemy.updateListOfPixelsCovered();
//						for(int j = 0; j< enemy.getPixelsCovered().size(); j++) {
//							if(enemy.getPixelsCovered().get(j).get(0) == loc.get(0)
//									&& enemy.getPixelsCovered().get(j).get(1) == loc.get(1)
//									
//									) {
//								System.out.println("Touching!");
//							}
//						}
//					}
					
				}
			}

		}

	}

	private static void moveSpaceship() {
		int change = 5;
		if (movedBy < moveLimit) {
			if (direction == 1 & spaceship.getCol() + change <= width) {
				spaceship.setCol(spaceship.getCol() + change);
				movedBy += change;
			} else if (direction == -1 && spaceship.getCol() - change >= 0) {
				spaceship.setCol(spaceship.getCol() - change);
				movedBy += change;
			}
		}

	}

	public static void moveEnemies() {
		int lastCol = enemies.get(0).get(enemyCol - 1).getCol();
		int w = enemies.get(0).get(enemyCol - 1).getImage().getWidth() / 9;
		int firstCol = enemies.get(0).get(0).getCol();
		int total = lastCol + shiftBy + w;
		if (Math.signum(shiftBy) > 0) { // moving right
			if (total > width) {
				shiftBy = -1 * shiftBy;
			}
		} else if (Math.signum(shiftBy) < 0) {
			if (firstCol - shiftBy < 0) {
				shiftBy = -1 * shiftBy;

			}
		}
		for (int r = 0; r < enemyRow; r++) {
			for (int c = 0; c < enemyCol; c++) {
				Enemy enemy = enemies.get(r).get(c);
				enemy.setCol(enemy.getCol() + shiftBy);
			}

		}
	}

	@Override
	public void paintComponent(Graphics g) {

		if (!gameOver) {
			
			setBackground(g);
			// Paint Enemies
			for (int r = 0; r < enemyRow; r++) {
				for (int c = 0; c < enemyCol; c++) {
					Enemy enemy = enemies.get(r).get(c);
					enemy.setWidth(enemy.getImage().getWidth() / 9);
					enemy.setHeight(enemy.getImage().getHeight() / 9);
					enemy.paintComponent(g);
				}
			}
			// Paint Spaceship
			spaceship.setWidth(spaceship.getImage().getWidth() / 9);
			spaceship.setHeight(spaceship.getImage().getHeight() / 9);
			spaceship.paintComponent(g);

			// shoot projectile
			if (shootProjectile) {
				if (sProjectiles.size() > 0) {
					for (int i = 0; i < sProjectiles.size(); i++) {
						Projectile projectile = sProjectiles.get(i);
						projectile.paintComponent(g);

					}
				}

			}

		}

	}

	private void setBackground(Graphics g) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("SpaceBackground.png");
		BufferedImage img = null;

		try {
			img = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, 0, 0, width, height, null);
		
	}
}
