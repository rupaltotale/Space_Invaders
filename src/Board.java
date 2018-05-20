import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * 
 * @author Anjana, Ashutosh, Katie, Nicole, Rupal This will be our main class.
 *
 */

public class Board extends JPanel implements MouseListener {

	/* Frame and board */
	static JFrame frame = new JFrame("Space Invaders");
	static Board board = new Board(); // JPanel

	/* Game settings */
	private static String dashboardTextColor = "#232323";// "#F8F1D7";
	static int width = 1200; // panel width
	static int height = 800; // panel height
	static int margin = 150;
	static BufferedImage background;
	static boolean gameOver = false;
	static String initialTheme = "space";
	static String currentTheme = initialTheme;
	static int score = 0;
	static int livesLeft;
	static int timeElapsed = 0;
	static int constant = 1;
	static int time = 20 * constant; // in milliseconds
	static Timer timer = new Timer(time, null);
	private static boolean pause = false;
	private static boolean showHomePage = true;
	private static ArrayList<Integer> playGameRect = new ArrayList<>();

	/* Barriers */
	static ArrayList<Barrier> barriers = new ArrayList<Barrier>();
	private static int numberOfBarriers = 4;

	/* Regular enemies */
	static ArrayList<ArrayList<Enemy>> enemies = new ArrayList<ArrayList<Enemy>>();
	static int enemyRow = 5;
	static int enemyCol = 12;
	private static int moveDownBy;
	static ArrayList<Projectile> eProjectiles = new ArrayList();
	static double eSpeed = 1 * constant;
	static int epSpeed = 9 * constant;
	private static double probabilityOfNotShooting = 0.98;
	static int rowsInvalidated;
	private static double angle = 0;
	private static double angleIncrement = 0.03;

	/* Flying enemies */
	static int fRow = margin / 3;
	static int fCol = margin;
	static Enemy flyingEnemy;
	static int fTime = 30 * 1000 / time / constant; // every 10 seconds
	static int fSpeed = 3 * constant;

	/* Spaceship */
	static int sRow = height - 100;
	static int sCol = margin;
	static int lives = 4;
	static Spaceship spaceship = new Spaceship(sRow, sCol, lives);// for the spaceship characteristics
	static int moveLimit = 40;
	static int movedBy = moveLimit;
	static int direction = 0; // -1 is left, 1 is right
	static boolean isAlive = true;
	static int sSpeed = 5 * constant;
	static int spSpeed = -25 * constant;
	static ArrayList<Projectile> sProjectiles = new ArrayList(); // list of projectiles thrown by the spaceship

	public static void main(String[] args) throws IOException {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Images.loadImages();

		background = Images.getSpaceBackground();
		Enemy.makeEnemyLists();
		startNewGame();
		setupTimer();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(board);
		board.setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setVisible(true);
		board.setUpKeyMappings();
		Board mml = new Board();
		board.addMouseListener(mml);

	}

	public static void startNewGame() {
		createEnemies();
		createBarriers();
		flyingEnemy.setInvalid(true);
		flyingEnemy.setCol(margin);

		spaceship.setImage(Images.getSpaceship());
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
		setTheme(initialTheme);
		board.removeAll();

	}

	/*
	 * Defines functionalities of different keys: Right, Left, Space
	 */
	private void setUpKeyMappings() {

		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "shoot");
		this.getInputMap().put(KeyStroke.getKeyStroke("P"), "pause");
		this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "newGame");

		this.getActionMap().put("right", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ADD implementation of right key here
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

		this.getActionMap().put("shoot", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ADD implementation of space key here

				if (!gameOver) {

					if (sProjectiles.size() == 0) {
						Projectile projectile = new Projectile(Images.getSpaceshipProjectile(), spSpeed, true);
						int row = spaceship.getRow() - projectile.getHeight();
						int col = spaceship.getCol() + spaceship.getWidth() / 2 - projectile.getWidth() / 2;
						projectile.setLocation(row, col);
						sProjectiles.add(projectile);
					}
				}
			}

		});
		this.getActionMap().put("pause", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ADD implementation of space key here

				pause = !pause;
			}

		});
		this.getActionMap().put("newGame", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameOver) {
					startNewGame();
				} else if (showHomePage) {
					showHomePage = false;
					timer.start();
				}
			}

		});

		this.requestFocusInWindow();

	}

	public static void setTheme(String changeToTheme) {
		currentTheme = changeToTheme;
		if (currentTheme.equals("space")) {
			background = Images.getSpaceBackground();
			for (int i = 0; i < barriers.size(); i++) {
				barriers.get(i).setImage(Images.getSpaceBarrier(), true);
			}
			for (int r = 0; r < enemies.size(); r++) {
				for (int c = 0; c < enemies.get(r).size(); c++) {
					Enemy enemy = enemies.get(r).get(c);
					if (enemy.getScore() == 150) {
						enemy.setImage(Images.getPurpleEnemy());
					}
					if (enemy.getScore() == 100) {
						enemy.setImage(Images.getBlueEnemy());
					}
					if (enemy.getScore() == 50) {
						enemy.setImage(Images.getRedEnemy());
					}
				}
			}
			dashboardTextColor = "#F8F1D7";

		}
		if (currentTheme.equals("sky")) {
			background = Images.getSkyBackground();
			for (int i = 0; i < barriers.size(); i++) {
				barriers.get(i).setImage(Images.getSkyBarrier(), true);
			}
			for (int r = 0; r < enemies.size(); r++) {
				for (int c = 0; c < enemies.get(r).size(); c++) {
					Enemy enemy = enemies.get(r).get(c);
					if (enemy.getScore() == 150) {
						enemy.setImage(Images.getPurpleBirdEnemy());
					}
					if (enemy.getScore() == 100) {
						enemy.setImage(Images.getGreenEnemy());
					}
					if (enemy.getScore() == 50) {
						enemy.setImage(Images.getOrangeEnemy());
					}
				}
			}
			dashboardTextColor = "#232323";
		}
		if (currentTheme.equals("sea")) {
			background = Images.getSeaBackground();
			for (int i = 0; i < barriers.size(); i++) {
				barriers.get(i).setImage(Images.getSeaBarrier(), true);
			}
			for (int r = 0; r < enemies.size(); r++) {
				for (int c = 0; c < enemies.get(r).size(); c++) {
					Enemy enemy = enemies.get(r).get(c);
					if (enemy.getScore() == 150) {
						enemy.setImage(Images.getGreenFishR());
					}
					if (enemy.getScore() == 100) {
						enemy.setImage(Images.getPinkFishR());
					}
					if (enemy.getScore() == 50) {
						enemy.setImage(Images.getYellowFish());
					}
				}
			}
			dashboardTextColor = "#232323";
		}
	}

	/*
	 * Creates the enemies visually and adds rows of them to the enemies list.
	 */
	public static void createEnemies() {
		rowsInvalidated = 0;
		enemies = new ArrayList();
		int colSpacing = (width - margin * 2) / (enemyCol + 1);
		int rowSpacing = (int) ((height * 0.4) / (enemyRow));
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
		flyingEnemy = new Enemy(fRow, fCol, Enemy.getFlyingEnemy());

	}

	public static void createBarriers() {
		// I changed the code a bit because the barriers became very spaced out when I
		// clicked new game
		barriers = new ArrayList();
		int columns = numberOfBarriers * 2 + 1;
		int row = spaceship.getRow() - 150;
		int widthOfBarrier = width / columns;
		for (int i = 1; i < numberOfBarriers * 2; i += 2) {
			int col = widthOfBarrier * i;
			Barrier barrier = new Barrier(row, col);
			barrier.setImage(Images.getSpaceBarrier(), false);
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
				if (!pause) {
					tick();
					board.repaint();
					timeElapsed++;
				}

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
		isNewRowInvalidated();
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
		int maxValidCol = 0;
		int minValidCol = 0;
		for (int c = 0; c < enemyCol; c++) {
			for (int r = 0; r < enemies.size(); r++) {
				if (!enemies.get(r).get(c).isInvalid()) {
					maxValidCol = c;
					lastCol = enemies.get(r).get(c).getCol();
				}
			}
		}

		for (int c = enemyCol - 1; c >= 0; c--) {
			for (int r = 0; r < enemies.size(); r++) {
				if (!enemies.get(r).get(c).isInvalid()) {
					minValidCol = c;
					firstCol = enemies.get(r).get(c).getCol();
				}
			}
		}
		int w = enemies.get(0).get(enemyCol - 1).getImage().getWidth() / 9;
		int total = (int) (lastCol + eSpeed + w);
		if (Math.signum(eSpeed) > 0) { // moving right
			if (total > width) {
				angleIncrement = angleIncrement + (enemyCol - maxValidCol - 1) * 0.015;
				eSpeed = -1 * eSpeed; // change to left
				moveEnemiesDown();
				// flip fish direction
				for (int r = 0; r < enemies.size(); r++) {
					for (int c = 0; c < enemies.get(r).size(); c++) {
						Enemy enemy = enemies.get(r).get(c);
						if (enemy.getImage().equals(Images.getGreenFishR())) {
							// || enemy.getImage().equals(Images.getPinkFishR())) {
							enemy.setImage(Images.getGreenFishL());
							// }
						} else if (enemy.getImage().equals(Images.getPinkFishR())) {
							enemy.setImage(Images.getPinkFishL());
						}
					}
				}
			}
		} else if (Math.signum(eSpeed) < 0) {
			if (firstCol - eSpeed < 0) {
				angleIncrement = angleIncrement + (minValidCol) * 0.015;
				eSpeed = -1 * eSpeed;
				moveEnemiesDown();
				// flip fish direction
				for (int r = 0; r < enemies.size(); r++) {
					for (int c = 0; c < enemies.get(r).size(); c++) {
						Enemy enemy = enemies.get(r).get(c);
						if (enemy.getImage().equals(Images.getGreenFishL())) {

							enemy.setImage(Images.getGreenFishR());

						} else if (enemy.getImage().equals(Images.getPinkFishL())) {
							enemy.setImage(Images.getPinkFishR());
						}
					}
				}

			}
		}
		for (int r = 0; r < enemies.size(); r++) {
			for (int c = 0; c < enemies.get(r).size(); c++) {
				Enemy enemy = enemies.get(r).get(c);
				enemy.setCol((int) (enemy.getCol() + eSpeed));
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
			if (direction == 1 & spaceship.getCol() + sSpeed + spaceship.getWidth() <= width) {
				spaceship.setCol(spaceship.getCol() + sSpeed);
				movedBy += sSpeed;
			} else if (direction == -1 && spaceship.getCol() - sSpeed >= 0) {
				spaceship.setCol(spaceship.getCol() - sSpeed);
				movedBy += sSpeed;
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
								Audio.makeSoftKillingSoundForEnemy();
								score += enemy.getScore();
								sProjectiles.remove(projectile);
								break;

							}
						}
					}

					// checks if projectile is colliding with a flying enemy
					if (isColliding(flyingEnemy, projectile)) {
						flyingEnemy.setInvalid(true);
						sProjectiles.remove(projectile);
						score += flyingEnemy.getScore();
						Audio.makeHardKillingSoundForEnemy();
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
					&& projectile.getCol() + projectile.getWidth() >= spaceship.getCol()
					&& projectile.getCol() <= spaceship.getCol() + spaceship.getWidth()) {
				return true;
			}
		}
		if (obj instanceof Barrier) {
			Barrier barrier = (Barrier) obj;
			if (!projectile.isSpaceshipP()) { // add it!
				for (int row = projectile.getRow(); row < projectile.getRow() + projectile.getHeight() + eSpeed
						+ 1; row++) {
					for (int col = projectile.getCol(); col < projectile.getCol() + projectile.getWidth(); col++) {

						if (row >= barrier.getRow() && row < barrier.getRow() + barrier.getHeight()
								&& col >= barrier.getCol() && col < barrier.getCol() + barrier.getWidth()) {

							int r = (row - barrier.getRow());
							int c = (col - barrier.getCol());
							int rgba = (0 << 24) | (0 << 16) | (0 << 8) | 0;
							boolean transparent = barrier.getImage().getRGB(c, r) == rgba;
							if (!transparent) {
								barrier.setAttacked(true);
								barrier.setAttackedX(c);
								barrier.setAttackedY(r);
								barrier.setAttackedWidth((int) (projectile.getWidth() / 1.5));
								barrier.changeImage(projectile.isSpaceshipP());
								barrier.setAttacked(false);
								return true;
							}

						}

					}

				}
			} else {

				for (int row = projectile.getRow() + projectile.getHeight() + sSpeed + 1; row >= projectile
						.getRow(); row--) {
					for (int col = projectile.getCol(); col < projectile.getCol() + projectile.getWidth(); col++) {

						if (row >= barrier.getRow() && row < barrier.getRow() + barrier.getHeight()
								&& col >= barrier.getCol() && col < barrier.getCol() + barrier.getWidth()) {

							int r = (row - barrier.getRow());
							int c = (col - barrier.getCol());
							int rgba = (0 << 24) | (0 << 16) | (0 << 8) | 0;
							boolean transparent = barrier.getImage().getRGB(c, r) == rgba;
							if (!transparent) {
								barrier.setAttacked(true);
								barrier.setAttackedX(c);
								barrier.setAttackedY(r);
								barrier.setAttackedWidth((int) (projectile.getWidth() / 1.5));
								barrier.changeImage(projectile.isSpaceshipP());
								barrier.setAttacked(false);
								return true;
							}

						}

					}

				}
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
			Projectile projectile = new Projectile(enemy.getProjectile(), epSpeed, false);
			projectile.setCol(enemy.getCol() + enemy.getWidth() / 2 - projectile.getWidth() / 2);
			projectile.setRow(enemy.getRow() + enemy.getHeight());
			double randomAdd = Math.random() * 500;
			if (eProjectiles.size() == 0 && randomAdd > 500 * probabilityOfNotShooting) {
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
				// checks for collision between barrier and eProjectile
				for (int b = 0; b < barriers.size(); b++) {
					if (isColliding(barriers.get(b), projectile)) {
						eProjectiles.remove(projectile);
					}
				}

				// moves
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
				Audio.makeKillingSoundForSpaceship();
				livesLeft = spaceship.getLives() - 1;
				eProjectiles.remove(projectile);

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
			nextTheme();
			angleIncrement = 0.03;

		}
	}

	private static void nextTheme() {
		if (currentTheme.equals("space")) {
			setTheme("sky");
		} else if (currentTheme.equals("sky")) {

			setTheme("sea");
		} else if (currentTheme.equals("sea")) {
			setTheme("space");
		}

	}

	private static void isNewRowInvalidated() {
		int invalidRows = 0;
		for (int r = 0; r < enemies.size(); r++) {
			boolean invalid = true;
			for (int c = 0; c < enemies.get(r).size(); c++) {
				if (!enemies.get(r).get(c).isInvalid()) {
					invalid = false;
				}
			}
			if (invalid) {
				invalidRows++;
			}
		}
		if (invalidRows > rowsInvalidated) {
			rowsInvalidated++;
			// eSpeed += 1;
			probabilityOfNotShooting -= 0.02;
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

		showHomePage(g);
		if (!gameOver && timeElapsed != 0 && !showHomePage) {

			paintBarriers(g);
			paintEnemies(g);
			paintSpaceship(g);
			paintProjectiles(g);
			paintFlyingEnemy(g);
			paintDashBoard(g);

		}

		else if (gameOver) {
			paintGameOverPanel(g);

		}

	}

	private void paintGameOverPanel(Graphics g) {
		String buttonText = "<html>" + "<body" + "'>" + "<center><h1>Game Over</h1>"
				+ "<h2>Press ENTER to start a new game or click HERE</h2>" + "<h4> Score: " + score + "</h4>"
				+ "<h4> Time: " + (timeElapsed) / time / 2 + " seconds. </h4></center>";
		Button gameOverButton = new Button();
		gameOverButton.setLabel(buttonText);
		board.add(gameOverButton);
		gameOverButton.setBounds(width / 4, height / 4, width / 2, height / 2);
		gameOverButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startNewGame();

			}

		});

	}

	private void paintDashBoard(Graphics g) {
		// Lives Left in left corner
		Font font = new Font("Courier", Font.PLAIN, 22);
		g.setColor(Color.decode(dashboardTextColor));
		g.setFont(font);
		g.drawString("Lives Left: " + livesLeft, margin / 3, margin / 3);

		// Score in right corner
		String s = "Score: " + score;
		g.drawString(s, width - margin / 3 - 15 * s.length(), margin / 3);

		// Time elapsed in the middle
		String t = "Time Elapsed: " + (timeElapsed) / time / 2;
		g.drawString(t, width / 2 - t.length() * 15 / 2, margin / 3);

	}

	private void paintFlyingEnemy(Graphics g) {
		if (timeElapsed != 0 && !flyingEnemy.isInvalid()) {
			flyingEnemy.setWidth(flyingEnemy.getImage().getWidth() / 7);
			flyingEnemy.setHeight(flyingEnemy.getImage().getHeight() / 7);
			flyingEnemy.paintComponent(g);
		}

	}

	private void paintProjectiles(Graphics g) {
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

	}

	private void paintSpaceship(Graphics g) {
		spaceship.setWidth(spaceship.getImage().getWidth() / 9);
		spaceship.setHeight(spaceship.getImage().getHeight() / 9);
		spaceship.paintComponent(g);
	}

	private void paintEnemies(Graphics g) {
		for (int r = 0; r < enemies.size(); r++) {
			for (int c = 0; c < enemies.get(r).size(); c++) {
				Enemy enemy = enemies.get(r).get(c);
				if (!enemy.isInvalid()) {
					if (!(enemy.getImage().equals(Images.getGreenFishL()))
							&& !(enemy.getImage().equals(Images.getGreenFishR()))
							&& !(enemy.getImage().equals(Images.getPinkFishL()))
							&& !(enemy.getImage().equals(Images.getPinkFishR()))) {
						AffineTransform at = AffineTransform.getTranslateInstance(enemy.getCol(), enemy.getRow());
						// at.translate(enemy.getCol(), enemy.getRow());
						at.scale(.12, 0.12);
						at.rotate(Math.toRadians(angle), enemy.getImage().getWidth() / 2,
								enemy.getImage().getHeight() / 2);
						angle += angleIncrement;
						Graphics2D g2d = (Graphics2D) g;

						g2d.drawImage(enemy.getImage(), at, null);
					}
					else {
						 enemy.setWidth(enemy.getImage().getWidth() / 8);
						 enemy.setHeight(enemy.getImage().getHeight() / 8);
						 enemy.paintComponent(g);
					}

					//
				}

			}
		}

	}

	private void paintBarriers(Graphics g) {
		for (Barrier br : barriers) {
			br.setWidth((int) (br.getImage().getWidth()));
			br.setHeight((int) (br.getImage().getHeight()));
			br.paintComponent(g);

		}
	}

	private void showHomePage(Graphics g) {

		if (showHomePage) {

			timer.stop();
			System.out.println("Setting home page");
			g.drawImage(Images.getHomePageBackground(), 0, 0, width, height, null);

			BufferedImage title = Images.getTitle();
			g.drawImage(title, width / 2 - title.getWidth() / 2, (int) (margin / 2), title.getWidth(),
					title.getHeight(), null);
			BufferedImage playGameImage = Images.getPlayGameButton();
			g.drawImage(playGameImage, width / 2 - playGameImage.getWidth() / 2, (int) (height - 2.25 * margin),
					playGameImage.getWidth(), playGameImage.getHeight(), null);
			playGameRect.add(width / 2 - playGameImage.getWidth() / 2); // minX
			playGameRect.add(playGameRect.get(0) + playGameImage.getWidth()); // maxX
			playGameRect.add((int) (height - 2.25 * margin)); // minY
			playGameRect.add(playGameRect.get(2) + playGameImage.getHeight()); // maxY

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (showHomePage) {
			int mX = e.getX();
			int mY = e.getY();
			checkIfPlayGame(mX, mY);
		}

	}

	private void checkIfPlayGame(int x, int y) {
		if (x >= playGameRect.get(0) && x <= playGameRect.get(1) && y >= playGameRect.get(2)
				&& y <= playGameRect.get(3)) {
			showHomePage = false;
			timer.start();
		}
	}

	// Ignore this!
	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
