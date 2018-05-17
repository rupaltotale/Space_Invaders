import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Enemy extends JPanel {

	private int row;
	private int col;
	private BufferedImage image;
	private int width = 100;
	private int height = 100;
	private ArrayList<ArrayList<Integer>> pixelsCovered = new ArrayList<ArrayList<Integer>>();
	private String imageName;
	private boolean invalid = false;
	private int score;
	private String projectileName;
	private BufferedImage projectile;

	// Spaceship Theme: Score, imageName, projectileName
	private static ArrayList<Object> redEnemy = new ArrayList<>();
	private static ArrayList<Object> blueEnemy = new ArrayList<>();
	private static ArrayList<Object> purpleEnemy = new ArrayList<>();
	private static ArrayList<Object> flyingEnemy = new ArrayList<>();

	// private static String[] redEnemy = { "50", "EnemyRed.png",
	// "ProjectileRed.png" };
	// private static String[] blueEnemy = { "100", "EnemyBlue.png",
	// "ProjectileBlue.png" };
	// private static String[] purpleEnemy = { "150", "EnemyPurple.png",
	// "ProjectilePurple.png" };
	// private static String[] flyingEnemy = { "500", "FlyingEnemy.png", null };

	public Enemy(int row, int col, ArrayList<Object> enemyList) {
		this.row = row;
		this.col = col;
		this.score = (int) enemyList.get(0);
		this.image = (BufferedImage) enemyList.get(1);

		if (enemyList.get(2) != null)
			this.projectile = (BufferedImage) enemyList.get(2);
	}

	public static void makeEnemyLists() {
		redEnemy.add(50);
		redEnemy.add(Image.getRedEnemy());
		redEnemy.add(Image.getRedProjectile());

		blueEnemy.add(100);
		blueEnemy.add(Image.getBlueEnemy());
		blueEnemy.add(Image.getBlueProjectile());

		purpleEnemy.add(150);
		purpleEnemy.add(Image.getPurpleEnemy());
		purpleEnemy.add(Image.getPurpleProjectile());

		flyingEnemy.add(50);
		flyingEnemy.add(Image.getFlyingEnemy());
		flyingEnemy.add(null);

	}

	

	@Override
	public void paintComponent(Graphics g) {

		g.drawImage(image, col, row, width, height, this);

	}

	// getters and setters
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;

	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<ArrayList<Integer>> getPixelsCovered() {
		return pixelsCovered;
	}

	public void setPixelsCovered(ArrayList<ArrayList<Integer>> pixelsCovered) {
		this.pixelsCovered = pixelsCovered;
	}

	public BufferedImage getImage() {
		return this.image;
	}

	public boolean isInvalid() {
		return invalid;
	}

	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}

	public static ArrayList<Object> getRedEnemy() {
		return redEnemy;
	}

	public static void setRedEnemy(ArrayList<Object> redEnemy) {
		Enemy.redEnemy = redEnemy;
	}

	public static ArrayList<Object> getBlueEnemy() {
		return blueEnemy;
	}

	public static void setBlueEnemy(ArrayList<Object> blueEnemy) {
		Enemy.blueEnemy = blueEnemy;
	}

	public static ArrayList<Object> getPurpleEnemy() {
		return purpleEnemy;
	}

	public static void setPurpleEnemy(ArrayList<Object> purpleEnemy) {
		Enemy.purpleEnemy = purpleEnemy;
	}

	public static ArrayList<Object> getFlyingEnemy() {
		return flyingEnemy;
	}

	public static void setFlyingEnemy(ArrayList<Object> flyingEnemy) {
		Enemy.flyingEnemy = flyingEnemy;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getProjectileName() {
		return projectileName;
	}

	public void setProjectileName(String projectileName) {
		this.projectileName = projectileName;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getProjectile() {
		return projectile;
	}

	public void setProjectile(BufferedImage projectile) {
		this.projectile = projectile;
	}

}
