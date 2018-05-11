import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Projectile extends JPanel {

	public enum ProjectileTypes {
		Boomerang, LaserGun, Rocket, Cannon
	};

	private int speed;
	private int height;
	private int width;
	private ProjectileTypes projectile;
	private Color color = Color.BLACK;
	private int col;
	private int row;
	private int damage;
	private int pointValue;
	private BufferedImage image;

	public Projectile(String projectile) {
		if (projectile.equals("Boomerang"))
			this.projectile = ProjectileTypes.Boomerang;
		if (projectile.equals("LaserGun"))
			this.projectile = ProjectileTypes.LaserGun;
		if (projectile.equals("Cannon"))
			this.projectile = ProjectileTypes.Cannon;
		if (projectile.equals("Rocket"))
			this.projectile = ProjectileTypes.Rocket;
		getImage();
		setSpeed();
		setSize();
		setDamage();

	}

	public void setSpeed() {
		// I changed the speed because it was really slow before
		if (projectile == ProjectileTypes.Boomerang) {
			speed = 70;

		} else if (projectile == ProjectileTypes.Cannon) {
			speed = 50;
		} else if (projectile == ProjectileTypes.LaserGun) {
			speed = 30;
		} else if (projectile == ProjectileTypes.Rocket) {
			speed = 20;
		}

	}

	// should we make them all the same size?
	public void setSize() {
		if (projectile == ProjectileTypes.Boomerang) {
			height = image.getHeight()/10;
			width = image.getWidth()/10;
		}
		if (projectile == ProjectileTypes.Cannon) {
			height = 25;
			width = 8;
		}
		if (projectile == ProjectileTypes.Rocket) {
			height = image.getHeight()/10;
			width = image.getWidth()/10;
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

	public void getImage() {
		if (projectile == ProjectileTypes.Boomerang) {
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				image = ImageIO.read(classLoader.getResourceAsStream("Boomerang.png"));
				// image = ImageIO.read(new File("src/boomerang.png"));

			} catch (IOException e) {

				e.printStackTrace();
			}
		} else if (projectile == ProjectileTypes.Rocket) {
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				image = ImageIO.read(classLoader.getResourceAsStream("YellowRocket.png"));
//				image = ImageIO.read(new File("src/rocket.png"));

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	public void setLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public void move() {

		// I changed this to minus because the projectile moves up and and up in java is
		// less than down
		row -= speed;

	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(color);
		if (projectile == ProjectileTypes.Boomerang) {
			// ((Graphics2D) g).rotate(Math.toRadians(90));
			// The above statement does not rotate the image. Rotating the image will be
			// tough. We can figure it out once we have a basic version of the game.
			g.drawImage(image, col, row, width, height, null);
		}
		if (projectile == ProjectileTypes.Cannon) {
			
			g.drawOval(col, row, width, height);
			g.fillOval(col, row, width, height);
		}
		if (projectile == ProjectileTypes.Rocket) {
			g.drawImage(image, col, row, width, height, null);
		}
		if (projectile == ProjectileTypes.LaserGun) {
			g.drawRect(col, row, width, height);
			g.fillRect(col, row, width, height);
		}
	}

	// getters and setters
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
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

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		if (image != null) {
			BufferedImage colorImage = new BufferedImage(image.getWidth(), image.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			for (int row = 0; row < image.getWidth(); row++) {
				for (int col = 0; col < image.getHeight(); col++) {
					int pixel = image.getRGB(row, col);
					if (!((pixel >> 24) == 0x00)) {
						colorImage.setRGB(row, col, this.color.getRGB());
					}
				}

			}
			image = colorImage;
		}

	}

}
