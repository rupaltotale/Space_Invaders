import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Images {
	private InputStream inputStream;

	

	// Space Theme
	private static BufferedImage spaceBackground;
	private static BufferedImage skyBackground; /* ADD */
	private static BufferedImage seaBackground;/* ADD */
	private static BufferedImage dessertBackground;

	private static BufferedImage spaceBarrier;
	private static BufferedImage skyBarrier;/* ADD */
	private static BufferedImage seaBarrier;/* ADD */
	private static BufferedImage dessertBarrier;

	private static BufferedImage blueEnemy;
	private static BufferedImage redEnemy;
	private static BufferedImage purpleEnemy;
	private static BufferedImage flyingEnemy;

	private static BufferedImage purpleBirdEnemy;
	private static BufferedImage greenBirdEnemy;
	private static BufferedImage orangeBirdEnemy;

	private static BufferedImage greenFishR;
	private static BufferedImage pinkFishR;
	private static BufferedImage yellowFish;
	private static BufferedImage greenFishL;
	private static BufferedImage pinkFishL;

	private static BufferedImage fox;
	private static BufferedImage bunny;
	private static BufferedImage lizard;

	private static BufferedImage blueProjectile;
	private static BufferedImage redProjectile;
	private static BufferedImage purpleProjectile;

	private static BufferedImage spaceshipProjectile;
	private static BufferedImage spaceship;

	private static BufferedImage homePage;
	private static BufferedImage title;
	private static BufferedImage playGameButton;
	private static BufferedImage homePageBackground;

	private static BufferedImage restoreBarriers;
	private static BufferedImage heart;
	private static BufferedImage smallerSpaceship;
	private static BufferedImage freezeEnemies;
	private static BufferedImage invisibleBarrier;
	private static BufferedImage rocketProjectile;

	private static BufferedImage gameOverWindow;

	private static BufferedImage info;
	private static BufferedImage play;
	private static BufferedImage pause;
	private static BufferedImage infoPanel;
	private static BufferedImage mute;
	private static BufferedImage makeSound;

	private static BufferedImage enemyBlast;
	private static BufferedImage spaceshipBlast;

	public void loadImages() throws IOException {
		inputStream = this.getClass().getResourceAsStream("Images/SpaceBackground.png");
		spaceBackground = ImageIO.read(inputStream);
		
		inputStream = this.getClass().getResourceAsStream("Images/SkyBackground.png");
		skyBackground = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/SeaBackground.png");
		seaBackground = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/DesertBackground.png");
		dessertBackground = ImageIO.read(inputStream);

		/* Barriers */
		inputStream = this.getClass().getResourceAsStream("Images/SpaceBarrier.png");
		spaceBarrier = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/SkyBarrier.png");
		skyBarrier = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/SeaBarrier.png");
		seaBarrier = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/DesertBarrier.png");
		dessertBarrier = ImageIO.read(inputStream);

		/* Enemies */

		// Space
		inputStream = this.getClass().getResourceAsStream("Images/EnemyPurple.png");
		purpleEnemy = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/EnemyBlue.png");
		blueEnemy = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/EnemyRed.png");
		redEnemy = ImageIO.read(inputStream);

		// Sky
		inputStream = this.getClass().getResourceAsStream("Images/EnemyOrangeBird.png");
		orangeBirdEnemy = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/EnemyGreenBird.png");
		greenBirdEnemy = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/EnemyPurpleBird.png");
		purpleBirdEnemy = ImageIO.read(inputStream);

		// Desert
		inputStream = this.getClass().getResourceAsStream("Images/EnemyFox.png");
		fox = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/EnemyBunny.png");
		bunny = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/EnemyLizard.png");
		lizard = ImageIO.read(inputStream);

		// Sea
		inputStream = this.getClass().getResourceAsStream("Images/HomePageBackground.png");
		homePageBackground = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/EnemyGreenFish.png");
		greenFishR = ImageIO.read(inputStream);
		greenFishL = flipHorizontally(greenFishR);

		inputStream = this.getClass().getResourceAsStream("Images/EnemyPinkFish.png");
		pinkFishR = ImageIO.read(inputStream);
		pinkFishL = flipHorizontally(pinkFishR);

		inputStream = this.getClass().getResourceAsStream("Images/EnemyYellowFish.png");
		yellowFish = ImageIO.read(inputStream);

		// Flying
		inputStream = this.getClass().getResourceAsStream("Images/FlyingEnemy.png");
		flyingEnemy = ImageIO.read(inputStream);

		/* Projectiles */

		inputStream = this.getClass().getResourceAsStream("Images/ProjectilePurple.png");
		purpleProjectile = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/ProjectileBlue.png");
		blueProjectile = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/ProjectileRed.png");
		redProjectile = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/Spaceship.png");
		spaceship = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/SpaceshipProjectile.png");
		spaceshipProjectile = ImageIO.read(inputStream);

		/* Home page */

		inputStream = this.getClass().getResourceAsStream("Images/GalacticInvaders.png");
		homePage = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/GalacticInvadersTitle.png");
		title = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/PlayGameButton.png");
		playGameButton = ImageIO.read(inputStream);

		/* Superpowers */

		inputStream = this.getClass().getResourceAsStream("Images/RestoreBarriers.png");
		restoreBarriers = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/Heart.png");
		heart = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/SmallerSpaceship.png");
		smallerSpaceship = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/FreezeEnemies.png");
		freezeEnemies = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/InvisibleBarrier.png");
		invisibleBarrier = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/RocketProjectile.png");
		rocketProjectile = ImageIO.read(inputStream);

		/* Game Over */

		inputStream = this.getClass().getResourceAsStream("Images/GameOverWindow.png");
		gameOverWindow = ImageIO.read(inputStream);

		/* Footer */

		inputStream = this.getClass().getResourceAsStream("Images/Info.png");
		info = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/InfoPanel.png");
		infoPanel = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/Play.png");
		play = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/Pause.png");
		pause = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/Mute.png");
		mute = ImageIO.read(inputStream);

		inputStream = this.getClass().getResourceAsStream("Images/MakeSound.png");
		makeSound = ImageIO.read(inputStream);

		/* Animations */

		inputStream = this.getClass().getResourceAsStream("Images/EnemyBlast.png");
		enemyBlast = ImageIO.read(inputStream);
		
		inputStream = this.getClass().getResourceAsStream("Images/SpaceshipBlast.png");
		spaceshipBlast = ImageIO.read(inputStream);
	}

	public static BufferedImage flipHorizontally(BufferedImage image) {
		// BufferedImage image = this.image;
		if (image != null) {
			BufferedImage colorImage = new BufferedImage(image.getWidth(), image.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			for (int r = 0; r < image.getHeight(); r++) {
				for (int c = 1; c < image.getWidth(); c++) {
					colorImage.setRGB(image.getWidth() - c, r, image.getRGB(c, r));
				}

			}
			return colorImage;
		}
		return null;
	}

	public static BufferedImage getBlueEnemy() {
		return blueEnemy;
	}

	public static BufferedImage getBlueProjectile() {
		return blueProjectile;
	}

	public static BufferedImage getBunny() {
		return bunny;
	}

	private static GraphicsConfiguration getDefaultConfiguration() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.getDefaultConfiguration();
	}

	public static BufferedImage getDessertBackground() {
		return dessertBackground;
	}

	public static BufferedImage getDessertBarrier() {
		return dessertBarrier;
	}

	public static BufferedImage getEnemyBlast() {
		return enemyBlast;
	}

	public static BufferedImage getFlyingEnemy() {
		return flyingEnemy;
	}

	public static BufferedImage getFox() {
		return fox;
	}

	public static BufferedImage getFreezeEnemies() {
		return freezeEnemies;
	}

	public static BufferedImage getGameOverWindow() {
		return gameOverWindow;
	}

	public static BufferedImage getGreenBirdEnemy() {
		return greenBirdEnemy;
	}

	public static BufferedImage getGreenFishL() {
		return greenFishL;
	}

	public static BufferedImage getGreenFishR() {
		return greenFishR;
	}

	public static BufferedImage getHeart() {
		return heart;
	}

	public static BufferedImage getHomePage() {
		return homePage;
	}

	public static BufferedImage getHomePageBackground() {
		return homePageBackground;
	}

	public static BufferedImage getInfo() {
		return info;
	}

	public static BufferedImage getInfoPanel() {
		return infoPanel;
	}

	public static BufferedImage getInvisibleBarrier() {
		return invisibleBarrier;
	}

	public static BufferedImage getLizard() {
		return lizard;
	}

	public static BufferedImage getOrangeBirdEnemy() {
		return orangeBirdEnemy;
	}

	public static BufferedImage getPause() {
		return pause;
	}

	public static BufferedImage getPinkFishL() {
		return pinkFishL;
	}

	public static BufferedImage getPinkFishR() {
		return pinkFishR;
	}

	public static BufferedImage getPlay() {
		return play;
	}

	public static BufferedImage getPlayGameButton() {
		return playGameButton;
	}

	public static BufferedImage getPurpleBirdEnemy() {
		return purpleBirdEnemy;
	}

	public static BufferedImage getPurpleEnemy() {
		return purpleEnemy;
	}

	public static BufferedImage getPurpleProjectile() {
		return purpleProjectile;
	}

	public static BufferedImage getRedEnemy() {
		return redEnemy;
	}

	public static BufferedImage getRedProjectile() {
		return redProjectile;
	}

	public static BufferedImage getRestoreBarriers() {
		return restoreBarriers;
	}

	public static BufferedImage getRocketProjectile() {

		return rocketProjectile;
	}

	public static BufferedImage getSeaBackground() {
		return seaBackground;
	}

	public static BufferedImage getSeaBarrier() {
		return seaBarrier;
	}

	public static BufferedImage getSkyBackground() {
		return skyBackground;
	}

	public static BufferedImage getSkyBarrier() {
		return skyBarrier;
	}

	public static BufferedImage getSmallerSpaceship() {
		return smallerSpaceship;
	}

	public static BufferedImage getSpaceBackground() {
		return spaceBackground;
	}

	public static BufferedImage getSpaceBarrier() {
		return spaceBarrier;
	}

	public static BufferedImage getSpaceship() {
		return spaceship;
	}

	public static BufferedImage getSpaceshipProjectile() {
		return spaceshipProjectile;
	}

	public static BufferedImage getTitle() {
		return title;
	}

	public static BufferedImage getYellowFish() {
		return yellowFish;
	}

	public static BufferedImage getMute() {
		return mute;
	}

	public static BufferedImage getMakeSound() {
		return makeSound;
	}

	public static boolean inBounds(BufferedImage image, int r, int c) {
		if (r >= 0 && r < image.getHeight() && c >= 0 && c < image.getWidth()) {
			return true;
		}
		return false;
	}

	public static BufferedImage rotate(BufferedImage image, double angle) {
		double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
		int w = image.getWidth(), h = image.getHeight();
		int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
		GraphicsConfiguration gc = getDefaultConfiguration();
		BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
		Graphics2D g = result.createGraphics();
		g.translate((neww - w) / 2, (newh - h) / 2);
		g.rotate(angle, w / 2, h / 2);
		g.drawRenderedImage(image, null);
		g.dispose();
		return result;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public static BufferedImage getSpaceshipBlast() {
		return spaceshipBlast;
	}

	

	

	
}
