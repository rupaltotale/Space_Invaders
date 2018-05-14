import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Barriers extends JPanel{
	
	int health; //the number of pixels it beings with 
	int attackedX;
	int attackedY;
	int xLoc;
	int yLoc;
	int width; 
	int height; 
	Color colBackground; 
	
	BufferedImage image; 
	String barry = "BarrierSpace.png";
	

	public Barriers(int xLoc, int yLoc) {
		this.xLoc = xLoc; 
		this.yLoc = yLoc; 
		setImage(barry);
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
	
	public void setImage(String name) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(name);
		BufferedImage img = null;

		try {
			img = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = img;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, xLoc, yLoc, width, height, this);
		}
	
	public BufferedImage getImage() {
		return image;
	}

	
	
	
}

	



>>>>>>> Stashed changes
