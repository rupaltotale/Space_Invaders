import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Image {

	private static BufferedImage background;

	// Space Theme
	private static BufferedImage spaceBarrier;

	private static BufferedImage blueEnemy;
	private static BufferedImage redEnemy;
	private static BufferedImage purpleEnemy;
	private static BufferedImage flyingEnemy;

	private static BufferedImage blueProjectile;
	private static BufferedImage redProjectile;
	private static BufferedImage purpleProjectile;
	private static BufferedImage yellowProjectile;

	private static BufferedImage spaceship;

	// PLEASE DON'T CHANGE ORDER OF ARRAYS!! IF YOU ADD ANYTHING, ADD IT TO THE END!!
	private static BufferedImage[] images = { 
			background, 
			spaceBarrier, 
			blueEnemy, 
			redEnemy, 
			purpleEnemy, 
			flyingEnemy, 
			blueProjectile, 
			redProjectile, 
			purpleProjectile, 
			yellowProjectile, 
			spaceship };
	private static String[] imageNames = {
			"SpaceBackground.png", 
			"RectangularBarriers.png", 
			"EnemyBlue.png", 
			"EnemyRed.png", 
			"EnemyPurple.png", 
			"FlyingEnemy.png", 
			"ProjectileBlue.png", 
			"ProjectileRed.png", 
			"ProjectilePurple.png", 
			"YellowRocket.png", 
			"Spaceship.png"
	};

	public static void loadImages() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input;
		BufferedImage img = null;

		for(int i = 0; i< images.length; i++) {
			input = classLoader.getResourceAsStream(imageNames[i]);
			
			try {
				img = ImageIO.read(input);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			images[i] = img;
			
		}
	}

	public static BufferedImage getBackground() {
		
		return images[0];
		
	}

	public static void setBackground(BufferedImage background) {
		Image.background = background;
	}

	public static BufferedImage getSpaceBarrier() {
		return images[1];
	}

	public static void setSpaceBarrier(BufferedImage spaceBarrier) {
		Image.spaceBarrier = spaceBarrier;
	}

	public static BufferedImage getBlueEnemy() {
		return images[2];
	}

	public static void setBlueEnemy(BufferedImage blueEnemy) {
		Image.blueEnemy = blueEnemy;
	}

	public static BufferedImage getRedEnemy() {
		return images[3];
	}

	public static void setRedEnemy(BufferedImage redEnemy) {
		Image.redEnemy = redEnemy;
	}

	public static BufferedImage getPurpleEnemy() {
		return images[4];
	}

	public static void setPurpleEnemy(BufferedImage purpleEnemy) {
		Image.purpleEnemy = purpleEnemy;
	}

	public static BufferedImage getFlyingEnemy() {
		return images[5];
	}

	public static void setFlyingEnemy(BufferedImage flyingEnemy) {
		Image.flyingEnemy = flyingEnemy;
	}

	public static BufferedImage getBlueProjectile() {
		return images[6];
	}

	public static void setBlueProjectile(BufferedImage blueProjectile) {
		Image.blueProjectile = blueProjectile;
	}

	public static BufferedImage getRedProjectile() {
		return images[7];
	}

	public static void setRedProjectile(BufferedImage redProjectile) {
		Image.redProjectile = redProjectile;
	}

	public static BufferedImage getPurpleProjectile() {
		return images[8];
	}

	public static void setPurpleProjectile(BufferedImage purpleProjectile) {
		Image.purpleProjectile = purpleProjectile;
	}

	public static BufferedImage getYellowProjectile() {
		return images[9];
	}

	public static void setYellowProjectile(BufferedImage yellowProjectile) {
		Image.yellowProjectile = yellowProjectile;
	}

	public static BufferedImage getSpaceship() {
		return images[10];
	}

	public static void setSpaceship(BufferedImage spaceship) {
		Image.spaceship = spaceship;
	}

	public static BufferedImage[] getImages() {
		return images;
	}

	public static void setImages(BufferedImage[] images) {
		Image.images = images;
	}

	public static String[] getImageNames() {
		return imageNames;
	}

	public static void setImageNames(String[] imageNames) {
		Image.imageNames = imageNames;
	}
		

}
