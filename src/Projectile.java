import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Projectile extends JPanel {



	private int speed;
	private int height;
	private int width;
	private int col;
	private int row;
	private BufferedImage image;
	private String imageName;
	private boolean spaceshipP;

	public Projectile(BufferedImage projectile, int speed, boolean spaceshipP) {

		this.image = projectile;
		this.spaceshipP = spaceshipP;
		setSpeed(speed);
		setSize();
	}

	public void setSpeed(int speed) {
		this.speed = speed;

	}

	public void setSize() {

		height = image.getHeight() / 15;
		width = image.getWidth() / 15;

	}



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


	public void setImage(BufferedImage image) {
		this.image = image;
	}


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

	//
}
