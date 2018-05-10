
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SmallEnemy extends JPanel{
	
	private int row;
	private int col;
	private BufferedImage image;
	private int width = 100;
	private int height = 100;
//	Projectile projectile;
	private ArrayList<ArrayList<Integer>> pixelsCovered = new ArrayList<ArrayList<Integer>>();
	private String imageName;

	
	public SmallEnemy(int row, int col,String imageName) {
		this.row = row;
		this.col = col;
		this.imageName = imageName;
		setImage(imageName);
	}
	public void setImage(String imageName) {
		// TODO Auto-generated method stub
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(imageName);
		BufferedImage img = null;

		try {
			img = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedImage transparentBackground = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int row = 0; row < img.getWidth(); row++) {
			for (int col = 0; col < img.getHeight(); col++) {
				int pixel = img.getRGB(row,col);
				if (!((pixel>>24) == 0x00)) {
					transparentBackground.setRGB(row, col, pixel);
					ArrayList<Integer> loc = new ArrayList();
					loc.add(row);
					loc.add(col);
					pixelsCovered.add(loc);
					
				}
			}
		}
		image =  transparentBackground;

	}
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		System.out.println("In the enemy class");
		g.drawImage(image, col, row, width, height, this);
		
	}
	
	
	//getters and setters
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
	
	
	

}
