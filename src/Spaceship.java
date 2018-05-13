import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Spaceship extends JPanel{
	
	int row;
	int col;
	int width;
	int height;
	int health;
	int lives;
	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}
	Projectile projectile = new Projectile("Rocket", "Spaceship");

	
	BufferedImage image;
	String imageName = "Spaceship.png";
	
	public Spaceship(int row, int col, int lives) {
		this.row = row;
		this.col = col;
		setImage(imageName);
		health = 100;
		this.lives = lives;
		
	}
	void setImage(String imageName){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(imageName);
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
		g.drawImage(image, col, row, width, height, this);
		
	}
	
	//Getters and setters
	public Projectile getProjectile() {
		return projectile;
	}
	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
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
	public BufferedImage getImage() {
		return image;
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

	public void removeLife() {
		lives -= 1;
	}
	public void hit(int damage) {
		health -= damage;
	}
	
	public boolean isDead() {
		if(lives <= 0) {
			return true;
		}
		return false;
	}
	public boolean alive() {
		if (health<=0) {
			return false;
		}
		return true;
	}

}
