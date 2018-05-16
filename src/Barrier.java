import java.awt.Color;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import java.io.IOException;

import java.io.InputStream;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class Barrier extends JPanel {
	private int health; // the number of pixels it beings with
	private int attackedX;
	private int attackedY;
	private int col;
	private int row;
	private int width;
	private int height;
	private boolean isAttacked = false;
	private int attackedWidth;

	Color colBackground;
	BufferedImage backgroundImage;
	String imageName = "RectangularBarriers.png";

	public Barrier(int row, int col) {

		this.row = row;
		this.col = col;
		backgroundImage = Image.getSpaceBarrier(); 
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

	public boolean isAttacked() {
		return isAttacked;
	}

	public void setAttacked(boolean isAttacked) {
		this.isAttacked = isAttacked;
	}

	public void setAttackedWidth(int width) {

		this.attackedWidth = width;
	}

	public int getAttackedWidth() {
		return attackedWidth;
	}
}