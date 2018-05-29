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
	private static JFrame frame = new JFrame("Space Invaders");
	private static Board board = new Board(); // JPanel

	/* Game settings */
	private static String dashboardTextColor = "#232323";// "#F8F1D7";
	private static int width = 1125; // panel width
	private static int height = 750; // panel height
	private static int margin = 150;
	private static BufferedImage background;
	private static boolean gameOver = false;
	private static String initialTheme = "space";
	private static String currentTheme = initialTheme;
	private static int score = 0;
	private static int livesLeft;
	private static int timeElapsed = 0;
	private static int constant = 1;
	private static int time = 20 * constant; // in milliseconds
	private static Timer timer = new Timer(time, null);
	private static boolean pause = false;
	private static boolean showHomePage = true;
	private static ArrayList<Integer> playGameRect = new ArrayList<Integer>();
	private static ArrayList<Integer> infoRect = new ArrayList<Integer>();
	private static ArrayList<Integer> playOrPauseRect = new ArrayList<Integer>();
	private static ArrayList<Integer> muteOrUnmuteRect = new ArrayList<Integer>();
	private static boolean showInfo;
	private static boolean mute = false;
	private static Audio audio = new Audio();

	/* Barriers */
	private static ArrayList<Barrier> barriers = new ArrayList<Barrier>();
	private static int numberOfBarriers = 4;

	/* Regular enemies */
	private static ArrayList<ArrayList<Enemy>> enemies = new ArrayList<ArrayList<Enemy>>();
	private static int enemyRow = 5;
	private static int enemyCol = 12;
	private static int moveDownBy = 0;
	private static ArrayList<Projectile> eProjectiles = new ArrayList<Projectile>();
	private static double eSpeed = 1 * constant;
	private static int epSpeed = 8 * constant;
	private static double probabilityOfNotShooting = 0.98;
	private static int rowsInvalidated;
	private static double angle = 0;
	private static double angleIncrement = 0.03;

	/*Super powers*/
	private static ArrayList<String> superpowers = new ArrayList<String>();
	private static boolean hasSuperpower = false;

	private static int pauseEnemiesTime = 0;
	private static int numRocketPro = 0;
	private static int spaceshipSizeRatio = 9;
	private static int smallerSpaceshipTime = 1000;
	private static int invisibleBarrierTime = 0;

	private static boolean pauseEnemies = false;
	private static boolean invisibleBarrier = false;
	private static boolean rocketProjectile = false;
	private static boolean smallerSpaceship = false;

	private static int superpowerCurrentRow;
	private boolean superpowerMovingUp = true;

	private static String superpowerDashboardText = "";

	/* Flying enemies */
	private static int fRow = (int) (margin / 2);
	private static int fCol = margin;
	private static Enemy flyingEnemy;
	private static int fTime = 30 * 1000 / time / constant; // every 10 seconds
	private static int fSpeed = 3 * constant;

	/* Spaceship */
	private static int sRow = height - 120;
	private static int sCol = margin;
	private static int lives = 3;
	private static Spaceship spaceship = new Spaceship(sRow, sCol, lives);// for the spaceship characteristics
	private static int moveLimit = 40;
	private static int movedBy = moveLimit;
	private static int direction = 0; // -1 is left, 1 is right
	private static boolean isAlive = true;
	private static int sSpeed = 5 * constant;
	private static int spSpeed = -20 * constant;
	private static int spaceshipBlastTimes = 0;
	private static ArrayList<Projectile> sProjectiles = new ArrayList<Projectile>();// list of projectiles thrown by the

	public static void main(String[] args) throws IOException {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Images images = new Images();
		images.loadImages();
		setupSuperpowers();

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
		// Reset all variables to initial game settings
		hasSuperpower = false;
		pauseEnemies = false;
		invisibleBarrier = false;
		rocketProjectile = false;
		smallerSpaceship = false;
		spaceshipSizeRatio = 9;

		superpowerDashboardText = "";
		pauseEnemiesTime = 0;
		gameOver = false;
		score = 0;
		livesLeft = lives;
		timeElapsed = 0;
		angle = 0;
		probabilityOfNotShooting = 0.98;
		angleIncrement = 0.03;
		eSpeed = 1;

		sRow = height - 120;
		sCol = margin;
		lives = 3;
		spaceship = new Spaceship(sRow, sCol, lives);// for the spaceship characteristics
		moveLimit = 40;
		movedBy = moveLimit;
		direction = 0; // -1 is left, 1 is right
		isAlive = true;
		sSpeed = 5 * constant;
		spSpeed = -20 * constant;
		sProjectiles = new ArrayList<Projectile>();
		spaceshipBlastTimes = 0;

		createEnemies();
		createBarriers();
		flyingEnemy.setInvalid(true);
		flyingEnemy.setCol(margin);
		spaceship.setImage(Images.getSpaceship());
		spaceship.setLives(lives);

		timer.start();
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
		setTheme(initialTheme, false);
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
		this.getInputMap().put(KeyStroke.getKeyStroke("I"), "info");
		this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
		this.getInputMap().put(KeyStroke.getKeyStroke("M"), "mute");

		this.getActionMap().put("right", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				direction = 1;
				movedBy = 0;  
			}
		});

		this.getActionMap().put("left", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				direction = -1;
				movedBy = 0;

			}
		});

		this.getActionMap().put("shoot", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {

				if (!gameOver) {

					if (sProjectiles.size() == 0) {
						// audio.makeLaunchProjectileSound();
						Projectile projectile = new Projectile(Images.getSpaceshipProjectile(), spSpeed, true);
						int row = spaceship.getRow() - projectile.getHeight();
						int col = spaceship.getCol() + spaceship.getWidth() / 2 - projectile.getWidth() / 2;
						projectile.setLocation(row, col);
						if (rocketProjectile && numRocketPro <= 3) {
							numRocketPro++;
						}
						if (rocketProjectile && numRocketPro == 4) {
							hasSuperpower = false;
							rocketProjectile = false;
							numRocketPro = 0;
						}

						sProjectiles.add(projectile);

					}
				}
			}

		});

		this.getActionMap().put("pause", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				// ADD implementation of space key here

				pause = !pause;
				showInfo = false;
				board.repaint();
			}

		});
		this.getActionMap().put("newGame", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				// if (gameOver) {
				// startNewGame();
				// } else
				if (showHomePage) {
					showHomePage = false;
					timer.start();
				}
				startNewGame();
			}

		});
		this.getActionMap().put("info", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				showInfo = !showInfo;
				pause = showInfo;
				board.repaint();
			}

		});
		this.getActionMap().put("escape", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				if (showInfo) {
					showInfo = false;
					pause = false;
					board.repaint();
				}
			}

		});
		this.getActionMap().put("mute", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {

				mute = !mute;
			}

		});

		this.requestFocusInWindow();

	}

	public static void setTheme(String changeToTheme, boolean modifyBarrier) {
		currentTheme = changeToTheme;
		if (currentTheme.equals("space")) {
			background = Images.getSpaceBackground();
			for (int i = 0; i < barriers.size(); i++) {
				barriers.get(i).setImage(Images.getSpaceBarrier(), modifyBarrier);
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
		// GreenBirdEnemy -- ProjectilePurple
		// OrangeBirdEnemy -- ProjectileBlue
		// PurpleBirdEnemy -- ProjectileRed

		if (currentTheme.equals("sky")) {
			background = Images.getSkyBackground();
			for (int i = 0; i < barriers.size(); i++) {
				barriers.get(i).setImage(Images.getSkyBarrier(), modifyBarrier);
			}
			for (int r = 0; r < enemies.size(); r++) {
				for (int c = 0; c < enemies.get(r).size(); c++) {
					Enemy enemy = enemies.get(r).get(c);
					if (enemy.getScore() == 150) {
						enemy.setImage(Images.getGreenBirdEnemy());
					}
					if (enemy.getScore() == 100) {
						enemy.setImage(Images.getOrangeBirdEnemy());
					}
					if (enemy.getScore() == 50) {
						enemy.setImage(Images.getPurpleBirdEnemy());
					}
				}
			}
			dashboardTextColor = "#232323";
		}
		// YellowFishEnemy -- ProjectileBlue
		// PinkFishEnemy -- ProjectileRed
		// GreenFishEnemy -- ProjectilePurple

		if (currentTheme.equals("sea")) {
			background = Images.getSeaBackground();
			for (int i = 0; i < barriers.size(); i++) {
				barriers.get(i).setImage(Images.getSeaBarrier(), modifyBarrier);
			}
			for (int r = 0; r < enemies.size(); r++) {
				for (int c = 0; c < enemies.get(r).size(); c++) {
					Enemy enemy = enemies.get(r).get(c);
					if (enemy.getScore() == 150) {
						enemy.setImage(Images.getYellowFish());
					}
					if (enemy.getScore() == 100) {
						enemy.setImage(Images.getPinkFishR());
					}
					if (enemy.getScore() == 50) {
						enemy.setImage(Images.getGreenFishR());
					}
				}
			}
			dashboardTextColor = "#232323";
		}
		// EnemyBunny -- ProjectilePurple
		// EnemyFox -- ProjectileRed
		// EnemyLizard -- ProjectileBlue

		if (currentTheme.equals("desert")) {
			background = Images.getDessertBackground();
			for (int i = 0; i < barriers.size(); i++) {
				barriers.get(i).setImage(Images.getDessertBarrier(), modifyBarrier);
			}
			for (int r = 0; r < enemies.size(); r++) {
				for (int c = 0; c < enemies.get(r).size(); c++) {
					Enemy enemy = enemies.get(r).get(c);
					if (enemy.getScore() == 150) {
						enemy.setImage(Images.getBunny());
					}
					if (enemy.getScore() == 100) {
						enemy.setImage(Images.getFox());
					}
					if (enemy.getScore() == 50) {
						enemy.setImage(Images.getLizard());
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
		hasSuperpower = false;
		rowsInvalidated = 0;
		enemies = new ArrayList<ArrayList<Enemy>>();
		int colSpacing = (width - margin * 2) / (enemyCol + 1);
		int rowSpacing = (int) ((height * 0.4) / (enemyRow));
		for (int r = 0; r < enemyRow; r++) {
			ArrayList<Enemy> eRow = new ArrayList<Enemy>();
			for (int c = 0; c < enemyCol; c++) {
				if (r < 1) {
					
					Enemy enemy = new Enemy(r * rowSpacing + margin, c * colSpacing + margin, Enemy.getPurpleEnemy());
					if (moveDownBy == 0)
						moveDownBy = enemy.getHeight() / 4;
					eRow.add(enemy);
				} else if (r < 3) {
					Enemy enemy = new Enemy(r * rowSpacing + margin, c * colSpacing + margin, Enemy.getBlueEnemy());
					eRow.add(enemy);
				} else {
					Enemy enemy = new Enemy(r * rowSpacing + margin, c * colSpacing + margin, Enemy.getRedEnemy());
					eRow.add(enemy);
				}

			}
			enemies.add(eRow);
		}
		addNewSuperpower();
		flyingEnemy = new Enemy(fRow, fCol, Enemy.getFlyingEnemy());

	}

	public static void createBarriers() {
		// I changed the code a bit because the barriers became very spaced out when I
		// clicked new game
		barriers = new ArrayList<Barrier>();
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
		if (!pauseEnemies) {
			moveEnemies();
		}
		if (!hasSuperpower) {
			// System.out.println("No Superpower");
			superpowerDashboardText = "";
		}

		if (numberOfValidEnemies() > 5) {
			addNewSuperpower();
		}

		moveSpaceship();
		shootSpaceshipProjectile();
		moveFlyingEnemy();
		chooseRandomEnemyForProjectile();
		moveEnemiesProjectiles();
		checkTimeDependentSuperpowers();
		advanceToNextLevel();
		if (spaceshipBlastTimes == 0)
			gameOver();

	}

	private static int numberOfValidEnemies() {
		int numberOfValidEnemies = 0;
		for (int r = 0; r < enemyRow; r++) {
			for (int c = 0; c < enemyCol; c++) {
				if (!enemies.get(r).get(c).isInvalid()) {
					numberOfValidEnemies++;
				}
			}
		}
		return numberOfValidEnemies;
	}

	private static void addNewSuperpower() {
		if (!hasSuperpower) {
			Enemy enemy = new Enemy(0, 0, "");
			enemy.setInvalid(true);
			int i = 0;
			while (i == 0) {
				int randomRow = (int) (enemyRow * Math.random());
				int randomCol = (int) (enemyCol * Math.random());
				String superpowerString = superpowers.get((int) (Math.random() * superpowers.size()));
				enemy = enemies.get(randomRow).get(randomCol);
				int r = enemy.getRow();
				int c = enemy.getCol();
				// i++;
				if (!enemy.isInvalid()) {
					Enemy superpower = new Enemy(r, c, superpowerString);
					setSuperpowerImage(superpower);
					superpowerCurrentRow = r;
					// System.out.println("New superpower: " + superpowerCurrentRow);
					hasSuperpower = true;
					enemies.get(randomRow).set(randomCol, superpower);
					// System.out.println("Valid");
					break;
				}

			}

		}

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
					// maxValidCol = c;
					lastCol = enemies.get(r).get(c).getCol();
				}
			}
		}

		for (int c = enemyCol - 1; c >= 0; c--) {
			for (int r = 0; r < enemies.size(); r++) {
				if (!enemies.get(r).get(c).isInvalid()) {
					// minValidCol = c;
					firstCol = enemies.get(r).get(c).getCol();
				}
			}
		}
		int w = enemies.get(0).get(enemyCol - 1).getImage().getWidth() / 9;
		int total = (int) (lastCol + eSpeed + w);
		if (Math.signum(eSpeed) > 0) { // moving right
			if (total > width) {
				// angleIncrement = angleIncrement + (enemyCol - maxValidCol - 1) * 0.015;
				eSpeed = -1 * eSpeed; // change to left
				moveEnemiesDown();
				// flip fish direction
				for (int r = 0; r < enemies.size(); r++) {
					for (int c = 0; c < enemies.get(r).size(); c++) {
						Enemy enemy = enemies.get(r).get(c);
						if (enemy.getImage().equals(Images.getGreenFishR())) {
							enemy.setImage(Images.getGreenFishL());
						} else if (enemy.getImage().equals(Images.getPinkFishR())) {
							enemy.setImage(Images.getPinkFishL());
						}
					}
				}
			}
		} else if (Math.signum(eSpeed) < 0) {
			if (firstCol - eSpeed < 0) {
				// angleIncrement = angleIncrement + (minValidCol) * 0.015;
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
				if (enemy.getSuperPower() != null && !enemy.isInvalid()) {
					superpowerCurrentRow = superpowerCurrentRow + moveDownBy;
					// System.out.println(superpowerCurrentRow);
				}

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
					// Checks for collision between projectile and barrier
					for (int b = 0; b < barriers.size(); b++) {
						if (isColliding(barriers.get(b), projectile)) {
							sProjectiles.remove(projectile);
						}
					}

					// checks if the projectile is colliding with any regular enemy

					for (int r = 0; r < enemies.size(); r++) {
						for (int c = 0; c < enemies.get(r).size(); c++) {
							Enemy enemy = enemies.get(r).get(c);
							if (isColliding(enemy, projectile)) {
								if (!rocketProjectile) {
									sProjectiles.remove(projectile);
								}
								if (enemy.getSuperPower() == null)
									enemy.incrementNumBlasts();
								activateSuperpower(enemy);
								enemy.setInvalid(true);
								if (!mute) {
									if (enemy.getSuperPower() == null) {
										audio.makeSoftKillingSoundForEnemy();
									}
									else {
										audio.makeBubblePopSound();
									}
								}
								score += (enemy.getScore() * 100) / timeElapsed;
								break;

							}
						}
					}

					// checks if projectile is colliding with a flying enemy
					if (isColliding(flyingEnemy, projectile)) {
						flyingEnemy.setInvalid(true);
						sProjectiles.remove(projectile);
						score += flyingEnemy.getScore();
						if (!mute)
							audio.makeHardKillingSoundForEnemy();
					}

				}
			}
		}
	}

	/*
	 * Super powers: restore barriers, another life, smaller spaceship, rocket
	 * projectile, freeze Enemies, invisible barrier
	 */
	private static void setupSuperpowers() {
		superpowers.add("restoreBarriers");
		superpowers.add("anotherLife");
		superpowers.add("smallerSpaceship");// 10 seconds
		superpowers.add("rocketProjectile");
		superpowers.add("freezeEnemies");
		superpowers.add("invisibleBarrier");
	}

	private static void setSuperpowerImage(Enemy superpower) {
		if (superpower.getSuperPower().equals("restoreBarriers")) {
			superpower.setImage(Images.getRestoreBarriers());
		} else if (superpower.getSuperPower().equals("anotherLife")) {
			superpower.setImage(Images.getHeart());
		} else if (superpower.getSuperPower().equals("smallerSpaceship")) {
			superpower.setImage(Images.getSmallerSpaceship());
		} else if (superpower.getSuperPower().equals("freezeEnemies")) {
			superpower.setImage(Images.getFreezeEnemies());
		} else if (superpower.getSuperPower().equals("invisibleBarrier")) {
			superpower.setImage(Images.getInvisibleBarrier());
		} else if (superpower.getSuperPower().equals("rocketProjectile")) {
			superpower.setImage(Images.getRocketProjectile());
		}

	}

	private static void activateSuperpower(Enemy superpower) {
		if (superpower.getSuperPower() != null) {
			if (superpower.getSuperPower().equals("restoreBarriers")) {
				setTheme(currentTheme, false);
				hasSuperpower = false;

			}
			if (superpower.getSuperPower().equals("anotherLife")) {
				int lives = spaceship.getLives() + 1;
				spaceship.setLives(lives);
				livesLeft = spaceship.getLives();
				hasSuperpower = false;
			}
			if (superpower.getSuperPower().equals("smallerSpaceship")) {
				smallerSpaceship = true;
				smallerSpaceshipTime = 0;
				spaceshipSizeRatio = 15;
			}
			if (superpower.getSuperPower().equals("freezeEnemies")) {
				pauseEnemies = true;

			}
			if (superpower.getSuperPower().equals("invisibleBarrier")) {
				invisibleBarrier = true;
			}

			if (superpower.getSuperPower().equals("rocketProjectile")) {
				rocketProjectile = true;
			}

		}

	}

	public static void checkTimeDependentSuperpowers() {
		// smaller spaceship

		if (smallerSpaceship) {
			smallerSpaceshipTime++;
			if (smallerSpaceshipTime >= 10 * 1000 / 20) {
				spaceshipSizeRatio = 9;
				smallerSpaceshipTime = 0;
				hasSuperpower = false;
				smallerSpaceship = false;
			} else {
				int time = (int) (10 - smallerSpaceshipTime * 0.02);
				superpowerDashboardText = "Spaceship will be smaller for " + time + " seconds";
			}
		}
		if (pauseEnemies) {
			pauseEnemiesTime++;
			if (pauseEnemiesTime >= 10 * 1000 / 20) {
				pauseEnemies = false;
				pauseEnemiesTime = 0;
				hasSuperpower = false;
				// superpowerDashboardText = "";
			} else {
				int time = (int) (10 - pauseEnemiesTime * 0.02);
				superpowerDashboardText = "Enemies will be frozen for " + time + " seconds";
			}
		}

		if (invisibleBarrier) {
			invisibleBarrierTime++;
			if (invisibleBarrierTime >= 10 * 1000 / 20) {
				invisibleBarrier = false;
				invisibleBarrierTime = 0;
				hasSuperpower = false;
			} else {
				int time = (int) (10 - invisibleBarrierTime * 0.02);
				superpowerDashboardText = "Barriers will be impenetrable for " + time + " seconds";
			}

		}
		if (rocketProjectile) {
			int shoots = 4 - numRocketPro;
			superpowerDashboardText = (shoots - 1) + " rocket projectiles left";
			if (shoots == 1) {
				superpowerDashboardText = "";
			}
			if (shoots == 0) {
				rocketProjectile = false;
			}
		}
		if (!hasSuperpower) {
			superpowerDashboardText = "";
		}

	}

	private static boolean isColliding(Object obj, Projectile projectile) {
		if (obj instanceof Barrier) {
			Barrier barrier = (Barrier) obj;
			if (!projectile.isSpaceshipP()) { // projectile is shot by enemy
				for (int row = projectile.getRow(); row < projectile.getRow() + projectile.getHeight() + eSpeed
						+ 1; row++) {
					for (int col = projectile.getCol(); col < projectile.getCol() + projectile.getWidth(); col++) {

						if (row >= barrier.getRow() && row < barrier.getRow() + barrier.getHeight()
								&& col >= barrier.getCol() && col < barrier.getCol() + barrier.getWidth()) {

							int r = (row - barrier.getRow());
							int c = (col - barrier.getCol());
							int rgba = (0 << 24) | (0 << 16) | (0 << 8) | 0;
							boolean transparent = barrier.getImage().getRGB(c, r) == rgba;
							if (!transparent && !invisibleBarrier) {
								barrier.setAttacked(true);
								barrier.setAttackedX(c);
								barrier.setAttackedY(r);
								barrier.setAttackedWidth(barrier.getWidth() / 10);
								barrier.changeImage(projectile.isSpaceshipP());
								barrier.setAttacked(false);
								return true;
							}
							if (invisibleBarrier) {
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
								barrier.setAttackedWidth(barrier.getWidth() / 7);
								barrier.changeImage(projectile.isSpaceshipP());
								barrier.setAttacked(false);
								return true;
							}

						}

					}

				}
			}
		} else if (obj instanceof Enemy) {
			Enemy enemy = (Enemy) obj;
			if (projectile.getRow() > enemy.getRow() && projectile.getRow() < enemy.getRow() + enemy.getHeight() / 2
					&& projectile.getCol() > enemy.getCol() +10 && projectile.getCol() < enemy.getCol() + enemy.getWidth() - 10
					&& !enemy.isInvalid()) {
				return true;
			}

		}

		else if (obj instanceof Spaceship) {
			if (projectile.getRow() >= spaceship.getRow()
					&& projectile.getRow() <= spaceship.getRow() + spaceship.getHeight()
					&& projectile.getCol() + projectile.getWidth() >= spaceship.getCol()
					&& projectile.getCol() <= spaceship.getCol() + spaceship.getWidth()) {
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
			if (enemy.getSuperPower() == null) {
				Projectile projectile = new Projectile(enemy.getProjectile(), epSpeed, false);
				projectile.setCol(enemy.getCol() + enemy.getWidth() / 2 - projectile.getWidth() / 2);
				projectile.setRow(enemy.getRow() + enemy.getHeight() / 2);
				double randomAdd = Math.random() * 500;
				if (eProjectiles.size() == 0 && randomAdd > 500 * probabilityOfNotShooting) {
					eProjectiles.add(projectile);
				}
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
				// spaceship.hit(projectile.getDamage());
				spaceship.removeLife();
				spaceshipBlastTimes++;
				// spaceship.setCol(margin);
				if (!mute)
					audio.makeKillingSoundForSpaceship();
				livesLeft = spaceship.getLives();
				eProjectiles.remove(projectile);

			}

		}

	}

	public static void advanceToNextLevel() {
		boolean allKilled = true;
		for (int r = 0; r < enemies.size(); r++) {
			for (int c = 0; c < enemies.get(r).size(); c++) {
				if (!enemies.get(r).get(c).isInvalid()) {
					allKilled = false;
				}
			}
		}
		if (allKilled) {
			// things to do when advancing to new level
			score += 100;
			enemies = new ArrayList<ArrayList<Enemy>>();
			hasSuperpower = false;
			pauseEnemies = false;
			invisibleBarrier = false;
			rocketProjectile = false;
			smallerSpaceship = false;
			spaceshipSizeRatio = 9;
			superpowerDashboardText = "";
			createEnemies();
			nextTheme();
		
			if (eSpeed < 0 && eSpeed > -4) {
				eSpeed--;
				moveDownBy /= 1.1;
			} else if (eSpeed < 4) {
				eSpeed++;
				moveDownBy /= 1.1;
			}
			sSpeed += 3;
			moveLimit += 12;
		

		}
	}

	private static void nextTheme() {
		if (currentTheme.equals("space")) {
			setTheme("sky", true);
		} else if (currentTheme.equals("sky")) {

			setTheme("sea", true);
		} else if (currentTheme.equals("sea")) {
			setTheme("desert", true);
		} else if (currentTheme.equals("desert")) {
			setTheme("space", true);
		}

	}

	private static int numberOfRowsInvalidated() {
		int invalidRows = 0;
		for (int c = 0; c < enemies.get(0).size(); c++) {
			boolean newInvalidRow = true;
			for (int r = 0; r < enemies.size(); r++) {
				if (!enemies.get(r).get(c).isInvalid()) {
					newInvalidRow = false;
				}
			}
			if (newInvalidRow) {
				invalidRows++;
			}
		}

		if (invalidRows > rowsInvalidated) {
			rowsInvalidated++;
			probabilityOfNotShooting -= 0.02;
		}
		return invalidRows;
	}

	public static void gameOver() {
		boolean belowSpaceship = false;
		// checks if bottom most row of enemies is below spaceship
		for (int r = 0; r < enemies.size(); r++) {
			for (int c = 0; c < enemies.get(r).size(); c++) {
				if (!enemies.get(r).get(c).isInvalid() && enemies.get(r).get(c).getRow() > spaceship.getRow()) {
					belowSpaceship = true;
					// System.out.println("Below Spaceship");
				}
			}
		}
		if (spaceship.isDead() || belowSpaceship) {
			gameOver = true;
			timer.stop();
		}

	}

	public void paintComponent(Graphics g) {

		g.drawImage(background, 0, 0, width, height, null);

		showHomePage(g);
		if (!gameOver && timeElapsed != 0 && !showHomePage) {

			paintFooter(g);
			paintBarriers(g);
			paintEnemies(g);
			paintSpaceship(g);
			paintProjectiles(g);
			paintFlyingEnemy(g);
			paintDashBoard(g);
			//
			if (showInfo)
				// pause = true;
				paintInfo(g);

		}

		else if (gameOver) {
			paintGameOverPanel(g);

		}

	}

	private void paintFooter(Graphics g) {
		int infoW = Images.getInfo().getWidth() / 25;
		int infoH = Images.getInfo().getHeight() / 25;
		int infoX1 = width - margin / 2 - infoW;
		int infoY1 = height - margin / 2;
		g.drawImage(Images.getInfo(), infoX1, infoY1, infoW, infoH, null);
		infoRect.removeAll(infoRect);
		infoRect.add(infoX1);
		infoRect.add(infoX1 + infoW);
		infoRect.add(infoY1);
		infoRect.add(infoX1 + infoH);

		int ppX = (int) (width - margin / 2) - infoW * 2 - 15 * 1;
		BufferedImage pauseOrPlay;
		if (!pause) {
			pauseOrPlay = Images.getPause();
		} else {
			pauseOrPlay = Images.getPlay();
		}
		g.drawImage(pauseOrPlay, (int) ppX, infoY1, infoW, infoH, null);
		playOrPauseRect.add(ppX);
		playOrPauseRect.add(ppX + infoW);
		playOrPauseRect.add(infoY1);
		playOrPauseRect.add(ppX + infoH);

		int muteX = (int) (width - margin / 2) - infoW * 3 - 15 * 2;
		BufferedImage muteOrUnmute;
		if (!mute) {
			muteOrUnmute = Images.getMakeSound();
		} else {
			muteOrUnmute = Images.getMute();
		}
		g.drawImage(muteOrUnmute, (int) muteX, infoY1, infoW, infoH, null);
		muteOrUnmuteRect.add(muteX);
		muteOrUnmuteRect.add(ppX + infoW);
		muteOrUnmuteRect.add(infoY1);
		muteOrUnmuteRect.add(ppX + infoH);
	}

	private void paintGameOverPanel(Graphics g) {
		

		g.drawImage(Images.getGameOverWindow(), 0, 0, width, height, null);
		Font font = new Font("Courier", Font.PLAIN, 50);
		g.setColor(Color.decode("#000000"));
		g.setFont(font);
		g.drawString("" + score + "", (int) (width * 0.529164), (int) (height * 0.57367));
		g.drawString("" + (timeElapsed) / time / 2 + "s", (int) (width * 0.490667), (int) (height * 0.67967));

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

		g.drawString(superpowerDashboardText, width / 2 - superpowerDashboardText.length() * 15 / 2,
				(int) (margin / 1.5));

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

		if (spaceshipBlastTimes > 0 && spaceshipBlastTimes < 15) {
			spaceship.setImage(Images.getSpaceshipBlast());
			BufferedImage image = Images.getSpaceshipBlast();
			int width = spaceship.getImage().getWidth() / spaceshipSizeRatio * (spaceshipBlastTimes / 3 + 1);
			int height = spaceship.getImage().getHeight() / spaceshipSizeRatio * (spaceshipBlastTimes / 3 + 1);
			g.drawImage(image, spaceship.getCol() - (width - spaceship.getWidth()) / 2,
					spaceship.getRow() - (height - spaceship.getHeight()) / 2, width, height, null);
			spaceshipBlastTimes++;
		} else {
			spaceship.setImage(Images.getSpaceship());
			spaceshipBlastTimes = 0;
			spaceship.setWidth(spaceship.getImage().getWidth() / spaceshipSizeRatio);
			spaceship.setHeight(spaceship.getImage().getHeight() / spaceshipSizeRatio);
			spaceship.paintComponent(g);
		}

	}

	private void paintEnemies(Graphics g) {
		for (int r = 0; r < enemies.size(); r++) {
			for (int c = 0; c < enemies.get(r).size(); c++) {
				Enemy enemy = enemies.get(r).get(c);
				if (!enemy.isInvalid()) {
					if (enemy.getSuperPower() != null) {
						if (!pause) {

							if (enemy.getRow() > superpowerCurrentRow + 12) {
								superpowerMovingUp = true;
							}
							if (enemy.getRow() > superpowerCurrentRow - 12 && superpowerMovingUp) {
								enemy.setRow(enemy.getRow() - 1);
							} else {
								superpowerMovingUp = false;
								enemy.setRow(enemy.getRow() + 1);
							}
						}
						enemy.setWidth(enemy.getImage().getWidth() / 8);
						enemy.setHeight(enemy.getImage().getHeight() / 8);
						enemy.paintComponent(g);
					} else if (!(enemy.getImage().equals(Images.getGreenFishL()))
							&& !(enemy.getImage().equals(Images.getGreenFishR()))
							&& !(enemy.getImage().equals(Images.getPinkFishL()))
							&& !(enemy.getImage().equals(Images.getPinkFishR()))) {
						AffineTransform at = AffineTransform.getTranslateInstance(enemy.getCol(), enemy.getRow());
						at.scale(.12, 0.12);
						at.rotate(Math.toRadians(angle), enemy.getImage().getWidth() / 2,
								enemy.getImage().getHeight() / 2);
						//
						angleIncrement = (numberOfRowsInvalidated() + 1) * 0.02;

						if (!pause)
							angle += angleIncrement;
						Graphics2D g2d = (Graphics2D) g;

						g2d.drawImage(enemy.getImage(), at, null);
					} else {
						enemy.setWidth(enemy.getImage().getWidth() / 8);
						enemy.setHeight(enemy.getImage().getHeight() / 8);
						enemy.paintComponent(g);
					}

				}

				else if (enemy.getNumBlasts() > 0 && enemy.getNumBlasts() < 5) {
					enemy.setImage(Images.getEnemyBlast());
					enemy.setWidth(enemy.getImage().getWidth() / 8);
					enemy.setHeight(enemy.getImage().getHeight() / 8);
					enemy.paintComponent(g);
					enemy.incrementNumBlasts();

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

			// INFO ICON
			int infoX1 = width / 2 + playGameImage.getWidth() / 2 + 10;
			int infoY1 = (int) (height - 2.2 * margin);
			int infoW = Images.getInfo().getWidth() / 20;
			int infoH = Images.getInfo().getHeight() / 20;
			g.drawImage(Images.getInfo(), infoX1, infoY1, infoW, infoH, null);
			infoRect.add(infoX1);
			infoRect.add(infoX1 + infoW);
			infoRect.add(infoY1);
			infoRect.add(infoX1 + infoH);

			if (showInfo) {
				paintInfo(g);
			}

		}

	}

	private void paintInfo(Graphics g) {
		if (showInfo) {

			pause = true;
			board.repaint();
			int w = (int) (Images.getInfoPanel().getWidth() / 3.5);
			int h = (int) (Images.getInfoPanel().getHeight() / 3.5);
			g.drawImage(Images.getInfoPanel(), width / 2 - w / 2, height / 2 - h / 2, w, h, null);

		}

	}

	public void mouseClicked(MouseEvent e) {
		int mX = e.getX();
		int mY = e.getY();
		if (showHomePage) {
			if (checkInRect(mX, mY, playGameRect)) {
				showHomePage = false;
				timer.start();
				pause = false;
			} else if (checkInRect(mX, mY, infoRect)) {
				showInfo = !showInfo;
				board.repaint();
			}
		} else if (gameOver) {
			startNewGame();
		}

		else if (!gameOver && !showHomePage) {
			if (checkInRect(mX, mY, infoRect)) {
				showInfo = !showInfo;
				pause = showInfo;
				board.repaint();
			} else if (checkInRect(mX, mY, playOrPauseRect)) {
				pause = !pause;
				showInfo = false;
				board.repaint();
			} else if (checkInRect(mX, mY, muteOrUnmuteRect)) {
				mute = !mute;
				board.repaint();
			}
		}

	}

	private boolean checkInRect(int x, int y, ArrayList<Integer> rect) {
		if (x >= rect.get(0) && x <= rect.get(1) && y >= rect.get(2) && y <= rect.get(3)) {
			return true;
		}
		return false;
	}

	// Ignore this!

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

}
