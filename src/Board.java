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
	static int timeElapsed = 0;
	static BufferedImage background;

	static boolean gameOver = true;

	static int time = 20; // in milliseconds
	static Timer timer = new Timer(time, null);

	static ArrayList<ArrayList<Enemy>> enemies = new ArrayList<ArrayList<Enemy>>();
	static int enemyRow = 5;
	static int enemyCol = 12;
	static int eChange = 1;
	private static int moveDownBy;

	static int fRow = margin / 3;
	static int fCol = margin;
	static Enemy flyingEnemy = new Enemy(fRow, fCol, "FlyingEnemy.png");
	static int fTime = 10000 / time; // every 10 seconds
	// private static boolean showFlyingEnemy = false;
	static int speed = 3;

	static int sRow = height - 100;
	static int sCol = margin;
	static Spaceship spaceship = new Spaceship(sRow, sCol);// for the spaceship characteristics
	static int moveLimit = 40;
	static int movedBy = moveLimit;
	static int direction = 0; // -1 is left, 1 is right
	static boolean shootProjectile = true;
	static boolean timing = true;
	static boolean status = true;

	static ArrayList<Projectile> eProjectiles = new ArrayList();

	static int sChange = 20;
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
		createEnemies();
		flyingEnemy.setInvalid(true);
		setupTimer();

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

				System.out.println("Space Key Pressed");

				//if (sProjectiles.size() == 0) {
					Projectile projectile = new Projectile("Rocket", "Spaceship");
					int row = spaceship.getRow() - projectile.getHeight();
					int col = spaceship.getCol() + spaceship.getWidth() / 2 - projectile.getWidth() / 2;
					projectile.setLocation(row, col);
					sProjectiles.add(projectile);
				//}


			}
		});

		this.requestFocusInWindow();

	}

	/*
	 * Creates the enemies visually and adds rows of them to the enemies list.
	 */
	public static void createEnemies() {
		int colSpacing = (width - margin * 2) / (enemyCol + 1);
		int rowSpacing = (int) ((height * 0.3) / (enemyRow));
		for (int r = 0; r < enemyRow; r++) {
			ArrayList<Enemy> enemyRow = new ArrayList<Enemy>();
			for (int c = 0; c < enemyCol; c++) {
				if (r < 1) {
					Enemy enemy = new Enemy(r * rowSpacing + margin, c * colSpacing + margin, "EnemyPurple.png");
					moveDownBy = enemy.getHeight() / 2;
					enemyRow.add(enemy);
				} else if (r < 3) {
					Enemy enemy = new Enemy(r * rowSpacing + margin, c * colSpacing + margin, "EnemyBlue.png");
					enemyRow.add(enemy);
				} else {
					Enemy enemy = new Enemy(r * rowSpacing + margin, c * colSpacing + margin, "EnemyRed.png");
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
				timeElapsed++;
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
		showFlyingEnemy();
		enemiesShoot();
		if(timing) {
		shootEnemyProjectile();
		timing = false;
		} else {
			timing = true;
		}
		
		status = spaceship.alive();
		
		

	}

	private static void showFlyingEnemy() {
		if (flyingEnemy.isInvalid()) {
			if (timeElapsed % fTime == 0 && timeElapsed != 0) {
				// showFlyingEnemy = true;
				flyingEnemy.setInvalid(false);

			}
		} else {
			int col = flyingEnemy.getCol() + speed;
			flyingEnemy.setCol(col);
			if (flyingEnemy.getCol() > width) {
				// showFlyingEnemy = false;
				flyingEnemy.setInvalid(true);
			}
		}

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
								sProjectiles.remove(projectile);

							}
						}
					}

					// checks if projectile is colliding with a flying enemy
					if (isColliding(flyingEnemy, projectile)) {
						flyingEnemy.setInvalid(true);
						sProjectiles.remove(projectile);

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
			if (projectile.getRow() >= spaceship.getRow() && projectile.getRow() <= spaceship.getRow() + spaceship.getHeight()
					&& projectile.getCol() >= spaceship.getCol() && projectile.getCol() <= spaceship.getCol() + spaceship.getWidth()
					// && nextEnemy.isInvalid()
					//&& !spaceship.isInvalid()
					) {
				System.out.println("Your Spaceship has been hit!");
				return true;
			}
		}
		return false;
	}

	@Override
	public void paintComponent(Graphics g) {

		if (!gameOver) {

			g.drawImage(background, 0, 0, width, height, null);
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

			// Shoot projectile

			if (sProjectiles.size() > 0) {
				for (int i = 0; i < sProjectiles.size(); i++) {
					Projectile projectile = sProjectiles.get(i);
					projectile.paintComponent(g);

				}
			}
				
				if (eProjectiles.size() > 0) {
					for (int i = 0; i < eProjectiles.size(); i++) {
						Projectile projectile = eProjectiles.get(i);
						projectile.paintComponent(g);

					}
				}

			

			// Paint Barriers
			// ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			// InputStream input = classLoader.getResourceAsStream("BarrierSpace.png");
			// BufferedImage img = null;
			//
			// try {
			// img = ImageIO.read(input);
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			//
			// g.drawImage(img, margin -30, spaceship.getRow() - img.getHeight()/4,
			// img.getWidth()/4, img.getHeight()/4, null);
			//
			//

		}

		if (!flyingEnemy.isInvalid() && timeElapsed != 0) {
			// System.out.println(timeElapsed + ", Flying Enemy");
			flyingEnemy.setWidth(flyingEnemy.getImage().getWidth() / 7);
			flyingEnemy.setHeight(flyingEnemy.getImage().getHeight() / 7);
			flyingEnemy.paintComponent(g);
		}
		
		if(!status) {
			g.setColor(new Color (255,255,255));
			g.drawString("YOU ARE DEAD", frame.getWidth()/2 - 20, frame.getHeight()/5);
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
	
	private static void enemiesShoot() {
		for(int row = enemies.size()-1; row > 0; row--) {
			for (int col= enemies.get(row).size()-1; col>=0; col--) {
				if (row == enemies.size()-1 && enemies.get(row).get(col) != null) {
					int random1 = (int)(Math.random()*250);
					if(random1>=249) {
						Projectile temp = new Projectile("Rocket","Enemy");
						int r = enemies.get(row).get(col).getRow() + enemies.get(row-1).get(col).getHeight();
						int c = enemies.get(row).get(col).getCol() + enemies.get(row).get(col).getWidth() / 2 - temp.getWidth() / 2;
						temp.setLocation(r, c);
						eProjectiles.add(temp);
				}
				if (enemies.get(row).get(col) == null && enemies.get(row-1).get(col) != null) {
					int random2 = (int)(Math.random()*250);
					if(random2>=249) {
						Projectile temp = new Projectile("Rocket","Enemy");
						int r = enemies.get(row-1).get(col).getRow() + enemies.get(row-1).get(col).getHeight();
						int c = enemies.get(row-1).get(col).getCol() + enemies.get(row-1).get(col).getWidth() / 2 - temp.getWidth() / 2;
						temp.setLocation(r, c);
						eProjectiles.add(temp);
					}
				}
			}
		}
	}
	}
	
	private static void shootEnemyProjectile(){
		if (eProjectiles.size() > 0) {
			for (int i = 0; i < eProjectiles.size(); i++) {
				Projectile projectile = eProjectiles.get(i);
				if (projectile.getRow() < 0) {
					eProjectiles.remove(projectile);
				} else {
					projectile.move();
					//ArrayList<Integer> loc= new ArrayList();
				}
	}
		}
		
		for (int p = 0; p < eProjectiles.size(); p++) {
				Projectile pro = eProjectiles.get(p);
				if (isColliding(spaceship, pro)) {
					spaceship.hit(pro.getDamage());
					eProjectiles.remove(pro);

				}
			
		}
	}

	

}
