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

	// Spaceship Theme: Score, imageName, projectileName
	private static String[] redEnemy = { "50", "EnemyRed.png", "ProjectileRed.png" };
	private static String[] blueEnemy = { "100", "EnemyBlue.png", "ProjectileBlue.png" };
	private static String[] purpleEnemy = { "150", "EnemyPurple.png", "ProjectilePurple.png" };
	private static String[] flyingEnemy = { "500", "FlyingEnemy.png", null };

	public Enemy(int row, int col, String[] enemyType) {
		this.row = row;
		this.col = col;
		this.score = Integer.parseInt(enemyType[0]);
		this.imageName = enemyType[1];
		this.projectileName = enemyType[2];
		setImage(this.imageName);
	}

	public void setImage(String imageName) {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(imageName);
		BufferedImage img = null;

		try {
			img = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// BufferedImage transparentBackground = new BufferedImage(img.getWidth(),
		// img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		// for (int row = 0; row < img.getWidth(); row++) {
		// for (int col = 0; col < img.getHeight(); col++) {
		// int pixel = img.getRGB(row,col);
		// if (!((pixel>>24) == 0x00)) {
		// transparentBackground.setRGB(row, col, pixel);
		// ArrayList<Integer> loc = new ArrayList<Integer>();
		// loc.add(row);
		// loc.add(col);
		// pixelsCovered.add(loc);
		//
		// }
		// }
		// }
		image = img;

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

	public static String[] getRedEnemy() {
		return redEnemy;
	}

	public static void setRedEnemy(String[] redEnemy) {
		Enemy.redEnemy = redEnemy;
	}

	public static String[] getBlueEnemy() {
		return blueEnemy;
	}

	public static void setBlueEnemy(String[] blueEnemy) {
		Enemy.blueEnemy = blueEnemy;
	}

	public static String[] getPurpleEnemy() {
		return purpleEnemy;
	}

	public static void setPurpleEnemy(String[] purpleEnemy) {
		Enemy.purpleEnemy = purpleEnemy;
	}

	public static void setFlyingEnemy(String[] flyingEnemy) {
		Enemy.flyingEnemy = flyingEnemy;
	}

	public static String[] getFlyingEnemy() {
		return flyingEnemy;
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

}
