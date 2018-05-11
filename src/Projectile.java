import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

import javax.imageio.ImageIO;

public class Projectile {
	private int speed;
	private int height;
	private int width;
	public enum ProjectileTypes{Boomerang, LaserGun, Rocket, Cannon};
	private ProjectileTypes projectile;
	private int x;
	private int y;
	private int damage;
	private int pointValue;
	private Image image;
	public Projectile(String projectile) {
		if (projectile.equals("Boomerang"))
		this.projectile = ProjectileTypes.Boomerang;
		if (projectile.equals("LaserGun"))
			this.projectile = ProjectileTypes.LaserGun;
		if (projectile.equals("Cannon"))
			this.projectile = ProjectileTypes.Cannon;
		if (projectile.equals("Rocket"))
			this.projectile = ProjectileTypes.Rocket;
		setSpeed();
		setSize();
		setDamage();
		
		
		
	}
	public void setSpeed() {
		if (projectile == ProjectileTypes.Boomerang) {
			speed = 7;
			
		} else if (projectile == ProjectileTypes.Cannon) {
			speed = 5;
		} else if (projectile == ProjectileTypes.LaserGun) {
			speed = 3;
		} else if (projectile == ProjectileTypes.Rocket) {
			speed = 2;
		}
		
	}
	
	//should we make them all the same size?
	public void setSize() {
		if (projectile == ProjectileTypes.Boomerang) {
			height = 10;
			width = 10;
		}
		if (projectile == ProjectileTypes.Cannon) {
			height = 20;
			width = 20;
		}
		if (projectile == ProjectileTypes.Rocket) {
			height = 30;
			width = 30;
		}
		if (projectile == ProjectileTypes.LaserGun) {
			height = 10;
			width = 5;
		}
		
	}
	
	public void setDamage() {
		if (projectile == ProjectileTypes.Boomerang) {
			damage = 10;
		}
		if (projectile == ProjectileTypes.Cannon) {
			damage = 15;
		}
		if (projectile == ProjectileTypes.Rocket) {
			damage = 20;
		}
		if (projectile == ProjectileTypes.LaserGun) {
			damage = 5;
		}
	}
	
	public Image getImage() {
			if (projectile == ProjectileTypes.Boomerang) {
				try {
					image = ImageIO.read(new File("src/boomerang.png"));
					return image;
				} catch (IOException e) {
					System.out.println("Exception");
					e.printStackTrace();
				}
			} else if (projectile == ProjectileTypes.Rocket) {
				try {
					image = ImageIO.read(new File("src/rocket.png"));
					return image;
				} catch (IOException e) {
					System.out.println("Exception");
					e.printStackTrace();
				}
			}
			return image;
	}
			
			public void setlocation(int row, int col) {
				y = row;
				x = col;
			}
			
			public void move() {
				y+=speed;
				
			}
			
			public void draw(Graphics2D g) {
				if (projectile == ProjectileTypes.Boomerang) {
					g.rotate(Math.toRadians(90));
					g.drawImage(image, x, y, width, height, null);
				}
				if (projectile == ProjectileTypes.Cannon) {
					g.drawOval(x, y, width, height);
					g.fillOval(x, y, width, height);
				}
				if (projectile == ProjectileTypes.Rocket) {
					g.drawImage(image, x, y, width, height, null);
				}
				if (projectile == ProjectileTypes.LaserGun) {
					g.drawRect(x, y, width, height);
					g.fillRect(x, y, width, height);
				}
			}
	
}


