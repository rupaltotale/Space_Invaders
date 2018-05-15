
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

	/* Frame and board */
	static JFrame frame = new JFrame("Space Invaders");
	static Board board = new Board(); // JPanel

	/* Game settings */
	static int width = 1200; // panel width
	static int height = 800; // panel height
	static int margin = 150;
	static BufferedImage background;
	static boolean gameOver = false;
	static int score = 0;
	static int livesLeft;
	static int timeElapsed = 0;
	static int time = 20; // in milliseconds
	static Timer timer = new Timer(time, null);

	/* Barriers */
	static ArrayList<Barrier> barriers = new ArrayList<Barrier>();
	private static int numberOfBarriers = 4;

	/* Regular enemies */
	static ArrayList<ArrayList<Enemy>> enemies = new ArrayList<ArrayList<Enemy>>();
	static int enemyRow = 5;
	static int enemyCol = 12;
	static int eChange = 1;
	private static int moveDownBy;
	static ArrayList<Projectile> eProjectiles = new ArrayList();
	static int epSpeed = 6;

	/* Flying enemies */
	static int fRow = margin / 3;
	static int fCol = margin;
	static Enemy flyingEnemy = new Enemy(fRow, fCol, Enemy.getFlyingEnemy());
	static int fTime = 20 * 1000 / time; // every 10 seconds
	static int fSpeed = 3;

	/* Spaceship */
	static int sRow = height - 100;
	static int sCol = margin;
	static int lives = 4;
	static Spaceship spaceship = new Spaceship(sRow, sCol, lives);// for the spaceship characteristics
	static int moveLimit = 40;
	static int movedBy = moveLimit;
	static int direction = 0; // -1 is left, 1 is right
	static boolean timing = true;
	static boolean isAlive = true;
	static int sChange = 5;
	static int spSpeed = -25;
	static ArrayList<Projectile> sProjectiles = new ArrayList(); // list of projectiles thrown by the spaceship

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(board);
		board.setPreferredSize(new Dimension(width, height));
		setBackground();
		frame.pack();
		frame.setVisible(true);
		board.setUpKeyMappings();
		startNewGame();
		setupTimer();

	}

	public static void startNewGame() {
		createEnemies();
		createBarriers();
		flyingEnemy.setInvalid(true);
		flyingEnemy.setCol(margin);
		gameOver = false;
		score = 0;
		livesLeft = lives - 1;
		timeElapsed = 0;
		// setupTimer();
		timer.start();
		spaceship.setLives(lives);
		if (sProjectiles.size() > 0) {
			for (int i = 0; i < sProjectiles.size(); i++) {
				sProjectiles.remove(i);
			}
		}
		if (eProjectiles.size() > 0) {
			for (int i = 0; i < eProjectiles.size(); i++) {
				eProjectiles.remove(i);
			}
		}
		board.removeAll();
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

				// System.out.println("Space Key Pressed");
				if (!gameOver) {

					if (sProjectiles.size() == 0) {
						Projectile projectile = new Projectile("yellowRocket.png", spSpeed);
						int row = spaceship.getRow() - projectile.getHeight();
						int col = spaceship.getCol() + spaceship.getWidth() / 2 - projectile.getWidth() / 2;
						projectile.setLocation(row, col);
						sProjectiles.add(projectile);
					}
				} else {
					startNewGame();
				}
			}

		});

		this.requestFocusInWindow();

	}

	/*
	 * Creates the enemies visually and adds rows of them to the enemies list.
	 */
	public static void createEnemies() {
		enemies = new ArrayList();
		int colSpacing = (width - margin * 2) / (enemyCol + 1);
		int rowSpacing = (int) ((height * 0.3) / (enemyRow));
		for (int r = 0; r < enemyRow; r++) {
			ArrayList<Enemy> enemyRow = new ArrayList<Enemy>();
			for (int c = 0; c < enemyCol; c++) {
				if (r < 1) {
					Enemy enemy = new Enemy(r * rowSpacing + margin, c * colSpacing + margin, Enemy.getPurpleEnemy());
					moveDownBy = enemy.getHeight() / 4;
					enemyRow.add(enemy);
				} else if (r < 3) {
					Enemy enemy = new Enemy(r * rowSpacing + margin, c * colSpacing + margin, Enemy.getBlueEnemy());
					enemyRow.add(enemy);
				} else {
					Enemy enemy = new Enemy(r * rowSpacing + margin, c * colSpacing + margin, Enemy.getRedEnemy());
					enemyRow.add(enemy);
				}

			}
			enemies.add(enemyRow);
		}

	}

	public static void createBarriers() {
		// I changed the code a bit because the barriers became very spaced out when I
		// clicked new game
		barriers = new ArrayList();
		int columns = numberOfBarriers * 2 + 1;
		int row = spaceship.getRow() - 100;
		int widthOfBarrier = width / columns;
		for (int i = 1; i < numberOfBarriers * 2; i += 2) {
			int col = widthOfBarrier * i;
			Barrier barrier = new Barrier(row, col);
			barrier.setWidth(widthOfBarrier);
			barrier.setHeight(widthOfBarrier / barrier.getImage().getWidth() * barrier.getImage().getHeight());
			barriers.add(barrier);
		}
	}

	/*
	 * Adds an action listener to the timer. This action is performed every <time>
	 * millisecond.
	 */
	private static void setupTimer() {
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tick();
				board.repaint();
				timeElapsed++;
			}

		});
		timer.start();

	}

	/*
	 * This function is executed every <time> millisecond.
	 */
	protected static void tick() {
		moveEnemies();
		moveSpaceship();
		shootSpaceshipProjectile();
		moveFlyingEnemy();
		// addEnemiesProjectiles();
		chooseRandomEnemyForProjectile();
		moveEnemiesProjectiles();
		repaintAllEnemies();
		isGameOver();
		// isAlive = spaceship.alive();

	}

	/*
	 * This either shows the flying enemy (every <fTime>) or moves it or removes it
	 */
	private static void moveFlyingEnemy() {
		if (flyingEnemy.isInvalid()) {
			if (timeElapsed % fTime == 0 && timeElapsed != 0) {
				// showFlyingEnemy = true;
				flyingEnemy.setInvalid(false);
				flyingEnemy.setCol(margin);

			}
		} else {
			int col = flyingEnemy.getCol() + fSpeed;
			flyingEnemy.setCol(col);
			if (flyingEnemy.getCol() > width) {
				// showFlyingEnemy = false;
				flyingEnemy.setInvalid(true);
			}
		}

	}

	/*
	 * Checks if enemies are near the edge of the panel and based on that shifts
	 * them left or right
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
		int total = lastCol + eChange + w;
		if (Math.signum(eChange) > 0) { // moving right
			if (total > width) {
				eChange = -1 * eChange;
				moveEnemiesDown();
			}
		} else if (Math.signum(eChange) < 0) {
			if (firstCol - eChange < 0) {
				eChange = -1 * eChange;
				moveEnemiesDown();

			}
		}
		for (int r = 0; r < enemies.size(); r++) {
			for (int c = 0; c < enemies.get(r).size(); c++) {
				Enemy enemy = enemies.get(r).get(c);
				enemy.setCol(enemy.getCol() + eChange);
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
		if (movedBy < moveLimit) {
			if (direction == 1 & spaceship.getCol() + sChange + spaceship.getWidth() <= width) {
				spaceship.setCol(spaceship.getCol() + sChange);
				movedBy += sChange;
			} else if (direction == -1 && spaceship.getCol() - sChange >= 0) {
				spaceship.setCol(spaceship.getCol() - sChange);
				movedBy += sChange;
			}
		}

	}

	/*
	 * moves the projectile up and checks for collision between projectile and
	 * exposed enemy
	 */
	private static void shootSpaceshipProjectile() {
		if (sProjectiles.size() > 0) {
			for (int i = 0; i < sProjectiles.size(); i++) {
				Projectile projectile = sProjectiles.get(i);
				if (projectile.getRow() < 0) { // above the panel
					sProjectiles.remove(projectile);
				} else {
					projectile.move();

					// checks if the projectile is colliding with any regular enemy

					for (int r = 0; r < enemies.size(); r++) {
						for (int c = 0; c < enemies.get(r).size(); c++) {
							Enemy enemy = enemies.get(r).get(c);
							if (isColliding(enemy, projectile)) {
								enemy.setInvalid(true);
								score += enemy.getScore();
								sProjectiles.remove(projectile);

							}
						}
					}

					// checks if projectile is colliding with a flying enemy
					if (isColliding(flyingEnemy, projectile)) {
						flyingEnemy.setInvalid(true);
						sProjectiles.remove(projectile);
						score += flyingEnemy.getScore();

					}
					for (int b = 0; b < barriers.size(); b++) {
						if (isColliding(barriers.get(b), projectile)) {
							sProjectiles.remove(projectile);
						}
					}
				}
			}
		}
	}

	private static boolean isColliding(Object obj, Projectile projectile) {
		if (obj instanceof Enemy) {
			Enemy enemy = (Enemy) obj;
			if (projectile.getRow() >= enemy.getRow() && projectile.getRow() <= enemy.getRow() + enemy.getHeight()
					&& projectile.getCol() >= enemy.getCol() && projectile.getCol() <= enemy.getCol() + enemy.getWidth()
					// && nextEnemy.isInvalid()
					&& !enemy.isInvalid()) {
				return true;
			}

		}

		if (obj instanceof Spaceship) {
			if (projectile.getRow() >= spaceship.getRow()
					&& projectile.getRow() <= spaceship.getRow() + spaceship.getHeight()
					&& projectile.getCol() >= spaceship.getCol()
					&& projectile.getCol() <= spaceship.getCol() + spaceship.getWidth()) {
				return true;
			}
		}
		if (obj instanceof Barrier) {
			Barrier barrier = (Barrier) obj;
			if (projectile.getRow() >= barrier.getRow() && projectile.getRow() <= barrier.getRow() + barrier.getHeight()
					&& projectile.getCol() >= barrier.getCol()
					&& projectile.getCol() <= barrier.getCol() + barrier.getWidth()) {
				// This is just rough code for damage.
				// barrier.setAttacked(true);
				// int colRelativeToBarrier = projectile.getCol() - barrier.getCol();
				// barrier.setAttackedX(colRelativeToBarrier);
				// int rowRelativeToBarrier = projectile.getRow() - barrier.getRow();
				// barrier.setAttackedY(rowRelativeToBarrier);
				// barrier.setAttackedWidth(projectile.getWidth());
				return true;
			}
		}
		return false;
	}

	private static ArrayList<Enemy> findEnemiesForProjectile() {
		ArrayList<Enemy> enemiesForProjectile = new ArrayList<Enemy>();
		for (int c = enemyCol - 1; c >= 0; c--) {
			for (int r = enemyRow - 1; r >= 0; r--) {
				if (!enemies.get(r).get(c).isInvalid()) {
					enemiesForProjectile.add(enemies.get(r).get(c));
					break;
				}
			}
		}
		return enemiesForProjectile;
	}

	public static void chooseRandomEnemyForProjectile() {
		ArrayList<Enemy> enemiesForProjectile = findEnemiesForProjectile();
		if (enemiesForProjectile.size() != 0) {
			int random = (int) (enemiesForProjectile.size() * Math.random());
			Enemy enemy = enemiesForProjectile.get(random);
			Projectile projectile = new Projectile(enemy.getProjectileName(), epSpeed);
			// System.out.println(enemy.getProjectileName());
			projectile.setCol(enemy.getCol() + enemy.getWidth() / 2 - projectile.getWidth() / 2);
			projectile.setRow(enemy.getRow() + enemy.getHeight());
			if (eProjectiles.size() == 0) {
				eProjectiles.add(projectile);
			}
		}
	}

	private static void moveEnemiesProjectiles() {
		// Either moves projectile down or removes it from eProjectiles if it is greater
		// than height
		if (eProjectiles.size() > 0) {
			for (int i = 0; i < eProjectiles.size(); i++) {
				Projectile projectile = eProjectiles.get(i);
				if (projectile.getRow() > height) {
					eProjectiles.remove(projectile);
				} else {
					projectile.move();
				}
			}
		}
		// Checks for collision between spaceship and enemy's projectile
		for (int p = 0; p < eProjectiles.size(); p++) {
			Projectile projectile = eProjectiles.get(p);
			if (isColliding(spaceship, projectile)) {
				spaceship.hit(projectile.getDamage());
				spaceship.removeLife();
				livesLeft = spaceship.getLives();
				eProjectiles.remove(projectile);

			}

			// checks for collision between barrier and eProjectile
			for (int b = 0; b < barriers.size(); b++) {
				if (isColliding(barriers.get(b), projectile)) {
					eProjectiles.remove(projectile);
				}
			}

		}

	}

	public static void repaintAllEnemies() {
		boolean allKilled = true;
		for (int r = 0; r < enemies.size(); r++) {
			for (int c = 0; c < enemies.get(r).size(); c++) {
				if (!enemies.get(r).get(c).isInvalid()) {
					allKilled = false;
				}
			}
		}
		if (allKilled) {
			enemies = new ArrayList<ArrayList<Enemy>>();
			createEnemies();
		}
	}

	public static void isGameOver() {
		boolean belowSpaceship = false;
		for (int r = 0; r < enemies.size(); r++) {
			for (int c = 0; c < enemies.get(r).size(); c++) {
				if (!enemies.get(r).get(c).isInvalid()
						&& enemies.get(r).get(c).getRow() + enemies.get(r).get(c).getHeight() > spaceship.getRow()) {
					belowSpaceship = true;
				}
			}
		}
		if (spaceship.isDead() || belowSpaceship) {
			gameOver = true;
			timer.stop();
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		g.drawImage(background, 0, 0, width, height, null);
		if (!gameOver) {
			// Paint Barriers
			for (Barrier br : barriers) {
				br.setWidth((int) (br.getImage().getWidth() / 4.5));
				br.setHeight((int) (br.getImage().getHeight() / 4.5));
				br.paintComponent(g);

				// This is just rough code for damage.
				// if(br.isAttacked()) {
				// g.setColor(Color.WHITE);
				// g.fillRect(br.getAttackedX(), br.getAttackedY(), br.getAttackedWidth(), 50);
				// br.setAttacked(false);
				// System.out.println("Barrier has been attacked!");
				// }

			}

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

			// Shoot sProjectiles

			if (sProjectiles.size() > 0) {
				for (int i = 0; i < sProjectiles.size(); i++) {
					Projectile projectile = sProjectiles.get(i);
					projectile.paintComponent(g);

				}
			}

			// Shoot eProjectiles
			if (eProjectiles.size() > 0) {
				for (int i = 0; i < eProjectiles.size(); i++) {
					Projectile projectile = eProjectiles.get(i);

					projectile.paintComponent(g);

				}
			}

			// Paint Flying Enemy
			if (!flyingEnemy.isInvalid() && timeElapsed != 0) {
				flyingEnemy.setWidth(flyingEnemy.getImage().getWidth() / 7);
				flyingEnemy.setHeight(flyingEnemy.getImage().getHeight() / 7);
				flyingEnemy.paintComponent(g);
			}
			// Lives Left in left corner
			Font font = new Font("Courier", Font.PLAIN, 25);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("Lives Left: " + livesLeft, margin / 3, margin / 3);

			// Score in right corner
			g.setColor(Color.WHITE);
			g.setFont(font);
			String s = "Score: " + score;
			g.drawString(s, width - margin / 3 - 15 * s.length(), margin / 3);
		}

		else if (gameOver) { // if game is over
			String buttonText = "<html>" + "<body" + "'>" + "<center><h1>Game Over</h1>"
					+ "<h2>Press SPACE to start a new game or click HERE</h2>" + "<h4> Score: " + score + "</h4>"
					+ "<h4> Time: " + timeElapsed / time / 2 + " seconds. </h4></center>";
			Button gameOverButton = new Button();
			gameOverButton.setLabel(buttonText);
			board.add(gameOverButton);
			gameOverButton.setBounds(width / 4, height / 4, width / 2, height / 2);
			gameOverButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Starting new game");
					startNewGame();

				}

			});

		}

	}

	/*
	 * Sets the background of the panel
	 */
	private static void setBackground() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("SpaceBackground.png");
		BufferedImage img = null;

		try {
			img = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		background = img;

	}

}
