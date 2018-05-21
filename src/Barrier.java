import java.awt.Color;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;

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
	private ArrayList<ArrayList<Integer>> hitPixels = new ArrayList<ArrayList<Integer>>();


	Color colBackground;
	BufferedImage backgroundImage;
	String imageName = "GreenBarier.png";
	
	
	
	BufferedImage image;
	public Barrier(int row, int col) {

		this.row = row;
		this.col = col;
		// image = Images.getSpaceBarrier();
	}

	public void setImage(BufferedImage image, boolean modifyImage) {
		if(modifyImage) {
			if (image != null && this.image != null) {
				BufferedImage colorImage = new BufferedImage(image.getWidth(), image.getHeight(),
						BufferedImage.TYPE_INT_ARGB);
				for (int r = 0; r < image.getHeight(); r++) {
					for (int c = 0; c < image.getWidth(); c++) {
						int rgba = (0 << 24) | (0 << 16) | (0 << 8) | 0;
						if(Images.inBounds(image, r, c) && Images.inBounds(this.image, r, c)) {
							if (this.image.getRGB(c, r) != rgba && image.getRGB(c, r) != rgba) {
								colorImage.setRGB(c, r, image.getRGB(c, r));
							}
						}

					}
				}
				this.image = colorImage;
			}
		}
		else {
			this.image = image;
		}
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

	}

	@Override

	public void paintComponent(Graphics g) {
		g.drawImage(image, col, row, width, height, this);
		for (ArrayList<Integer> a : hitPixels) {
			g.setColor(new Color(0, 0, 0));
			g.drawRect(a.get(1), a.get(0), 1, 1);
			g.fillRect(a.get(1), a.get(0), 1, 1);
		}
	}

	// getters and setters

	public BufferedImage getImage() {
		return image;
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

	public void setHealth(int health) {
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

	public boolean isHit(Projectile pro) {
		if (pro.getRow() + pro.getHeight() >= row && pro.getCol() + (pro.getWidth() / 2) >= col
				&& pro.getCol() + (pro.getWidth() / 2) <= col + width) {
			hit(pro);
			return true;
		}
		return false;
	}

	public void hit(Projectile pro) {
		int hitCol = pro.getCol() + pro.getWidth() / 2;
		int maxRow = row;
		// for(int i = row; i<row+height; i++) {
		if (hitPixels.size() == 0) {

			for (int r = row; r <= row + 9; r++) {
				for (int c = hitCol - 2; c < hitCol + 2; c++) {
					ArrayList<Integer> temp = new ArrayList<>();
					temp.add(r);
					temp.add(c);
					hitPixels.add(temp);
				}
			}

		}
		for (int r = 0; r < hitPixels.size(); r++) {
			if (hitPixels.get(r).get(1) == hitCol) {
				if (hitPixels.get(r).get(0) > row) {
					maxRow = hitPixels.get(r).get(0);
				}
			}
		}
		for (int r = maxRow; r <= maxRow + 9; r++) {
			for (int c = hitCol - 2; c < hitCol + 2; c++) {
				ArrayList<Integer> temp = new ArrayList<>();
				temp.add(r);
				temp.add(c);
				hitPixels.add(temp);
			}
		}
		// }
	}

	public void changeImage(boolean spaceshipP) {
		BufferedImage image = this.image;
		if (image != null) {
			BufferedImage colorImage = new BufferedImage(image.getWidth(), image.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			for (int r = 0; r < image.getHeight(); r++) {
				for (int c = 0; c < image.getWidth(); c++) {
					colorImage.setRGB(c, r, image.getRGB(c, r));
				}
			}
			if (!spaceshipP) {
				int attackedHeight = attackedWidth;
				for (int row = attackedY; row < attackedY + attackedHeight; row++) {
					for (int col = attackedX; col < attackedX + attackedWidth; col++) {
						if (Images.inBounds(colorImage, row, col)) {
							int r = 0;
							int g = 0;
							int b = 0;
							int a = 0;
							int rgba = (a << 24) | (r << 16) | (g << 8) | b;
							// if(colorImage.getRGB(col, row) == rgba) {
							// attackedY = attackedY + attackedWidth;
							// changeImage();
							// }
							colorImage.setRGB(col, row, rgba);
						}
					}

				}
			} else {
				int attackedHeight = attackedWidth;
				for (int row = attackedY; row > attackedY - attackedHeight; row--) {
					for (int col = attackedX; col < attackedX + attackedWidth; col++) {
						if (Images.inBounds(colorImage, row, col)) {
							int r = 0;
							int g = 0;
							int b = 0;
							int a = 0;
							int rgba = (a << 24) | (r << 16) | (g << 8) | b;
							colorImage.setRGB(col, row, rgba);
						}
					}

				}
			}
			this.image = colorImage;

		}

	}

}