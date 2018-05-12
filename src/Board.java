import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * 
 * @author Anjana, Ashutosh, Katie, Nicole, Rupal This will be our main class.
 *
 */

public class Board extends JPanel {

	static JFrame frame = new JFrame("Space Invaders");
	static Board board = new Board(); // JPanel
	static int width = 1200; // panel width
	static int height = 800; // panel height
	static int margin = 150;

	static boolean gameOver = true;

	static int time = 1; // in milliseconds
	static Timer timer = new Timer(time, null);

	static ArrayList<ArrayList<Enemy>> enemies = new ArrayList<ArrayList<Enemy>>();
	static int enemyRow = 5;
	static int enemyCol = 12;
	static int shiftBy = 3;
	private static int moveDownBy;

	static Spaceship spaceship = new Spaceship(height - 100, margin);// for the spaceship characteristics
	static int moveLimit = 40;
	static int movedBy = moveLimit;
	static int direction = 0; // -1 is left, 1 is right
	static ArrayList<Projectile> sProjectiles = new ArrayList(); // list of projectiles thrown by the spaceship

	public static void main(String[] args) {

		// Just some copy pasted code from a previous project to setup JPanel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(board);
		board.setPreferredSize(new Dimension(width, height));
		// board.setLabelBackground();
		// board.setBackground(Color.BLACK);
		frame.pack();
		frame.setVisible(true);
		board.setUpKeyMappings(); // sets up the keys' (left, right, spcae) functionalities
		createEnemies();
		setupTimer();

	}

	/*
	 * an alternate way to set the background. More energy efficient
	 */
	private void setLabelBackground() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("SpaceBackground.png");
		BufferedImage img = null;

		try {
			img = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = newImage.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();

		ImageIcon icon = new ImageIcon(newImage);
		JLabel contentPane = new JLabel();
		contentPane.setOpaque(false);
		contentPane.setIcon(icon);
		contentPane.setBounds(0, 0, width, height);
		board.add(contentPane);

	}

	/*
	 * Defines functionalities of different keys: Right, Left, Space
	 */
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
				direction = -1;
				movedBy = 0;

			}
		});

		this.getActionMap().put("space", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ADD implementation of space key here

				if (sProjectiles.size() == 0) {
					Projectile projectile = new Projectile("Rocket");
					int row = spaceship.getRow() - projectile.getHeight();
					int col = spaceship.getCol() + spaceship.getWidth() / 2 - projectile.getWidth() / 2;
					projectile.setLocation(row, col);
					sProjectiles.add(projectile);
				}

			}
		});

		this.requestFocusInWindow();

	}

	/*
	 * Creates the enemies visually and adds rows of them to the enemies list.
	 */
	public static void createEnemies() {
		int spacing = (width - margin * 2) / (enemyCol + 1);
		for (int r = 0; r < enemyRow; r++) {
			ArrayList<Enemy> enemyRow = new ArrayList<Enemy>();
			for (int c = 0; c < enemyCol; c++) {
				if (r < 1) {
					Enemy enemy = new Enemy(r * spacing, c * spacing + margin, "EnemyPurple.png");
					moveDownBy = enemy.getHeight() / 2;
					enemyRow.add(enemy);
				} else if (r < 3) {
					Enemy enemy = new Enemy(r * spacing, c * spacing + margin, "EnemyBlue.png");
					enemyRow.add(enemy);
				} else {
					Enemy enemy = new Enemy(r * spacing, c * spacing + margin, "EnemyRed.png");
					enemyRow.add(enemy);
				}

			}
			enemies.add(enemyRow);
		}
		gameOver = false;

	}

	/*
	 * Adds an action listener to the timer. This action is performed every 1
	 * millisecond.
	 */
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

	/*
	 * This function is executed every millisecond.
	 */
	protected static void tick() {
		moveEnemies();
		moveSpaceship();
		shootSpaceshipProjectile();

	}

	/*
	 * Checks if enemies are near the edge of the panel and based on that shifts the
	 * enemy left or right
	 */
	public static void moveEnemies() {
		int lastCol = -1;
		int firstCol = -1;
		for (int c = 0; c < enemyCol; c++) {
			for (int r = 0; r < enemies.size(); r++) {
				if (!enemies.get(r).get(c).isInvalid()) {
					lastCol = enemies.get(r).get(c).getCol();
				}
			}
		}
		for (int c = enemyCol - 1; c >= 0; c--) {
			for (int r = 0; r < enemies.size(); r++) {
				if (!enemies.get(r).get(c).isInvalid()) {
					firstCol = enemies.get(r).get(c).getCol();
				}
			}
		}
		int w = enemies.get(0).get(enemyCol - 1).getImage().getWidth() / 9;
		int total = lastCol + shiftBy + w;
		if (Math.signum(shiftBy) > 0) { // moving right
			if (total > width) {
				shiftBy = -1 * shiftBy;
				moveEnemiesDown();
			}
		} else if (Math.signum(shiftBy) < 0) {
			if (firstCol - shiftBy < 0) {
				shiftBy = -1 * shiftBy;
				moveEnemiesDown();

			}
		}
		for (int r = 0; r < enemies.size(); r++) {
			for (int c = 0; c < enemies.get(r).size(); c++) {
				Enemy enemy = enemies.get(r).get(c);
				enemy.setCol(enemy.getCol() + shiftBy);
			}

		}
	}
	
	/*
	 * Moves enemies down at the when their direction of movement is changed. 
	 */
	private static void moveEnemiesDown() {
		for (int r = 0; r < enemies.size(); r++) {
			for (int c = 0; c < enemies.get(r).size(); c++) {
				Enemy enemy = enemies.get(r).get(c);
				int initialY = enemy.getRow();
				int newY = initialY + moveDownBy;
				enemy.setRow(newY);
			}
		}
	}

	/*
	 * Moves the spaceship either left or right
	 */
	private static void moveSpaceship() {
		int change = 20;
		if (movedBy < moveLimit) {
			if (direction == 1 & spaceship.getCol() + change + spaceship.getWidth() <= width) {
				spaceship.setCol(spaceship.getCol() + change);
				movedBy += change;
			} else if (direction == -1 && spaceship.getCol() - change >= 0) {
				spaceship.setCol(spaceship.getCol() - change);
				movedBy += change;
			}
		}

	}

	/*
	 * moves the projectile up and checks for collision between projectile and exposed enemy
	 */
	private static void shootSpaceshipProjectile() {
		if (sProjectiles.size() > 0) {
			for (int i = 0; i < sProjectiles.size(); i++) {
				Projectile projectile = sProjectiles.get(i);
				if (projectile.getRow() < 0) { // above the panel
					sProjectiles.remove(projectile);
				} else {
					projectile.move();

					// checks if the projectile is colliding with an enemy
					int row = projectile.getRow();
					int col = projectile.getCol();
					ArrayList<Enemy> lastRow = enemies.get(enemyRow - 1);
					for (int c = 0; c < lastRow.size(); c++) {
						Enemy enemy = lastRow.get(c);
						if (row >= enemy.getRow() && row <= enemy.getRow() + enemy.getHeight() && col >= enemy.getCol()
								&& col <= enemy.getCol() + enemy.getWidth() && !enemy.isInvalid()) {
							enemies.get(enemyRow - 1).get(c).setInvalid(true); // invalid = don't paint
							sProjectiles.remove(projectile);
						}
					}
					// checks above rows to see if enemies on those rows are exposed
					for (int r = 0; r < enemies.size() - 1; r++) {
						for (int c = 0; c < enemies.get(r).size(); c++) {
							Enemy enemy = enemies.get(r).get(c);
							Enemy nextEnemy = enemies.get(r + 1).get(c);
							if (row >= enemy.getRow() && row <= enemy.getRow() + enemy.getHeight()
									&& col >= enemy.getCol() && col <= enemy.getCol() + enemy.getWidth()
									&& nextEnemy.isInvalid() && !enemy.isInvalid()) {
								enemy.setInvalid(true);
								sProjectiles.remove(projectile);

							}
						}
					}
				}
			}
		}
	}

	
	

	@Override
	public void paintComponent(Graphics g) {

		if (!gameOver) {

			setBackground(g);
			// Paint Enemies
			for (int r = 0; r < enemies.size(); r++) {
				for (int c = 0; c < enemies.get(r).size(); c++) {
					Enemy enemy = enemies.get(r).get(c);
					if (!enemy.isInvalid()) {
						enemy.setWidth(enemy.getImage().getWidth() / 8);
						enemy.setHeight(enemy.getImage().getHeight() / 8);
						enemy.paintComponent(g);
					}

				}
			}
			// Paint Spaceship
			spaceship.setWidth(spaceship.getImage().getWidth() / 9);
			spaceship.setHeight(spaceship.getImage().getHeight() / 9);
			spaceship.paintComponent(g);

			// shoot projectile

			if (sProjectiles.size() > 0) {
				for (int i = 0; i < sProjectiles.size(); i++) {
					Projectile projectile = sProjectiles.get(i);
					projectile.paintComponent(g);

				}

			}

		}

	}

	/*
	 * Sets the background of the panel
	 */
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
