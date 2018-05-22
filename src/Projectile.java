import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Projectile extends JPanel {

//	public enum ProjectileTypes {
//		Boomerang, LaserGun, Rocket, Cannon
//	};

	private int speed;
	private int height;
	private int width;
//	private ProjectileTypes projectile;
//	private Color color = Color.BLACK;
	private int col;
	private int row;
//	private int damage;
//	private int pointValue;
	private BufferedImage image;
//	private boolean spaceship;
	private String imageName;
	private boolean spaceshipP;

	public Projectile(BufferedImage projectile, int speed, boolean spaceshipP) {

		this.image = projectile;
		this.spaceshipP = spaceshipP;
		setSpeed(speed);
		setSize();
//		setDamage();

	}

	public void setSpeed(int speed) {
		this.speed = speed;

	}
	
	// should we make them all the same size?
	public void setSize() {
		height = image.getHeight() / 15;
		width = image.getWidth() / 15;
	}

//	public void setDamage() {
//		if (projectile == ProjectileTypes.Rocket) {
//			damage = 20;
//		}
//	}

	public BufferedImage getImage() {

		return image;

	}

	public void setLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public void move() {
		row += speed;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, col, row, width, height, null);

	}

	// getters and setters
	public int getSpeed() {
		return speed;
	}

	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

//	public int getDamage() {
//		return damage;
//	}
//
//	public void setDamage(int damage) {
//		this.damage = damage;
//	}
//
//	public int getPointValue() {
//		return pointValue;
//	}
//
//	public void setPointValue(int pointValue) {
//		this.pointValue = pointValue;
//	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

//	public Color getColor() {
//		return color;
//	}
//
//	public void setColor(Color color) {
//		this.color = color;
//	}
//
//	public ProjectileTypes getProjectile() {
//		return projectile;
//	}
//
//	public void setProjectile(ProjectileTypes projectile) {
//		this.projectile = projectile;
//	}
//
//	public boolean isSpaceship() {
//		return spaceship;
//	}
//
//	public void setSpaceship(boolean spaceship) {
//		this.spaceship = spaceship;
//	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public boolean isSpaceshipP() {
		return spaceshipP;
	}

	public void setSpaceshipP(boolean spaceshipP) {
		this.spaceshipP = spaceshipP;
	}

}
