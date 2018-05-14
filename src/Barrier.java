import java.awt.Color;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import java.io.IOException;

import java.io.InputStream;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class Barrier extends JPanel {
	int health; // the number of pixels it beings with
	int attackedX;
	int attackedY;
	int col;
	int row;
	int width;
	int height;
	Color colBackground;
	BufferedImage backgroundImage;
	String imageName = "GreenBarier.png";

	public Barrier(int row, int col) {
		this.col = col;

		this.row = row;

		setImage(imageName);

	}

	public int[] getAttackedLocation() {

		int[] loc = new int[2];

		loc[0] = attackedX;

		loc[1] = attackedY;

		return loc;

	}

	public void damage(int x, int y, Color col) {

		this.attackedX = x;

		this.attackedY = y;

		this.colBackground = col;

	}

	@Override

	public void paintComponent(Graphics g) {

		g.drawImage(backgroundImage, col, row, width, height, this);

	}

	// getters and setters
	public void setImage(String name) {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		InputStream input = classLoader.getResourceAsStream(name);

		BufferedImage img = null;

		try {

			img = ImageIO.read(input);

		} catch (IOException e) {

			e.printStackTrace();

		}

		backgroundImage = img;

	}

	public BufferedImage getImage() {
		return backgroundImage;
	}

	public int getAttackedX() {
		return attackedX;
	}

	public void setAttackedX(int attackedX) {
		this.attackedX = attackedX;
	}

	public int getAttackedY() {
		return attackedY;
	}

	public void setAttackedY(int attackedY) {
		this.attackedY = attackedY;
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

	public Color getColBackground() {
		return colBackground;
	}

	public void setColBackground(Color colBackground) {
		this.colBackground = colBackground;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {

		return health;

	}

	public void setHealth() {

		this.health = health;

	}

	public void setWidth(int width) {

		this.width = width;

	}

	public int getWidth() {

		return width;

	}

	public void setHeight(int height) {

		this.height = height;

	}

	public int getHeight() {

		return height;

	}
}