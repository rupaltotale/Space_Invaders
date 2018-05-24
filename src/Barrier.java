import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Barrier extends JPanel {
	private int attackedX;
	private int attackedY;
	private int col;
	private int row;
	private int width;
	private int height;
	private boolean isAttacked = false;
	private int attackedWidth;

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



	
	@Override

	public void paintComponent(Graphics g) {
		g.drawImage(image, col, row, width, height, this);
		
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