import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Images {

	// Space Theme
	private static BufferedImage spaceBackground;
	private static BufferedImage skyBackground; /* ADD */
	private static BufferedImage seaBackground;/* ADD */

	private static BufferedImage spaceBarrier;
	private static BufferedImage skyBarrier;/* ADD */
	private static BufferedImage seaBarrier;/* ADD */

	private static BufferedImage blueEnemy;
	private static BufferedImage redEnemy;
	private static BufferedImage purpleEnemy;
	private static BufferedImage flyingEnemy;

	private static BufferedImage blueProjectile;
	private static BufferedImage redProjectile;
	private static BufferedImage purpleProjectile;

	private static BufferedImage spaceshipProjectile;
	private static BufferedImage spaceship;

	private static BufferedImage redEnemyRotatedRight;/* ADD */
	private static BufferedImage redEnemyRotatedLeft;/* ADD */

	private static BufferedImage homePage;
	private static BufferedImage title;

	private static BufferedImage playGameButton;
	private static BufferedImage purpleBirdEnemy;
	private static BufferedImage greenEnemy;
	private static BufferedImage orangeEnemy;
	private static BufferedImage enemies;
	private static BufferedImage instructions;
	private static BufferedImage homePageBackground;
	private static BufferedImage greenFishR;
	private static BufferedImage pinkFishR;
	private static BufferedImage yellowFish;
	private static BufferedImage greenFishL;
	private static BufferedImage pinkFishL;
	private static BufferedImage restoreBarriers;
	private static BufferedImage heart;
	private static BufferedImage smallerSpaceship;
	private static BufferedImage dessertBackground;
	private static BufferedImage freezeEnemies;
	private static BufferedImage invisibleBarrier;
	private static BufferedImage gameOverWindow;
	private static BufferedImage rocketProjectile;
	private static BufferedImage info;
	private static BufferedImage play;
	private static BufferedImage pause;
	private static BufferedImage dessertBarrier;
	private static BufferedImage infoPanel;

	// static ArrayList<BufferedImage> images = new ArrayList<>();
	// static ArrayList<BufferedImage> images2 = new ArrayList<>();
	// static ArrayList<String> imagesName = new ArrayList<>();

	public static void loadImages() throws IOException {

		InputStream inputStream = null;

		inputStream = new FileInputStream("src/Images/SpaceBackground.png");
		spaceBackground = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/SkyBackground.png");
		skyBackground = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/SeaBackground.png");
		seaBackground = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/DesertBackground.png");
		dessertBackground = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/SpaceBarrier.png");
		spaceBarrier = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/SkyBarrier.png");
		skyBarrier = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/SeaBarrier.png");
		seaBarrier = ImageIO.read(inputStream);
		
		inputStream = new FileInputStream("src/Images/DesertBarrier.png");
		dessertBarrier = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/EnemyPurple.png");
		purpleEnemy = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/EnemyBlue.png");
		blueEnemy = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/EnemyRed.png");
		redEnemy = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/EnemyOrange.png");
		orangeEnemy = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/EnemyGreen.png");
		greenEnemy = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/EnemyPurpleBird.png");
		purpleBirdEnemy = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/FlyingEnemy.png");
		flyingEnemy = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/ProjectilePurple.png");
		purpleProjectile = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/ProjectileBlue.png");
		blueProjectile = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/ProjectileRed.png");
		redProjectile = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/Spaceship.png");
		spaceship = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/SpaceshipProjectile.png");
		spaceshipProjectile = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/GalacticInvaders.png");
		homePage = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/GalacticInvadersTitle.png");
		title = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/PlayGameButton.png");
		playGameButton = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/Enemies.png");
		enemies = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/Instructions.png");
		instructions = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/HomePageBackground.png");
		homePageBackground = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/EnemyGreenFish.png");
		greenFishR = ImageIO.read(inputStream);
		greenFishL = flipHorizontally(greenFishR);

		inputStream = new FileInputStream("src/Images/EnemyPinkFish.png");
		pinkFishR = ImageIO.read(inputStream);
		pinkFishL = flipHorizontally(pinkFishR);

		inputStream = new FileInputStream("src/Images/EnemyYellowFish.png");
		yellowFish = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/RestoreBarriers.png");
		restoreBarriers = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/Heart.png");
		heart = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/SmallerSpaceship.png");
		smallerSpaceship = ImageIO.read(inputStream);
		
		inputStream = new FileInputStream("src/Images/FreezeEnemies.png");
		freezeEnemies = ImageIO.read(inputStream);
		
		inputStream = new FileInputStream("src/Images/InvisibleBarrier.png");
		invisibleBarrier = ImageIO.read(inputStream);
		
		inputStream = new FileInputStream("src/Images/GameOverWindow.png");
		gameOverWindow = ImageIO.read(inputStream);
		
		inputStream = new FileInputStream("src/Images/RocketProjectile.png");
		rocketProjectile = ImageIO.read(inputStream);
		
		inputStream = new FileInputStream("src/Images/Info.png");
		info = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/Play.png");
		play = ImageIO.read(inputStream);
		
		inputStream = new FileInputStream("src/Images/Pause.png");
		pause = ImageIO.read(inputStream);
		
		inputStream = new FileInputStream("src/Images/InfoPanel.png");
		infoPanel = ImageIO.read(inputStream);
	}

	public static boolean inBounds(BufferedImage image, int r, int c) {
		if (r >= 0 && r < image.getHeight() && c >= 0 && c < image.getWidth()) {
			return true;
		}
		return false;
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

	private static GraphicsConfiguration getDefaultConfiguration() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.getDefaultConfiguration();
	}

	public static BufferedImage getSpaceBackground() {
		return spaceBackground;
	}

	public static void setSpaceBackground(BufferedImage spaceBackground) {
		Images.spaceBackground = spaceBackground;
	}

	public static BufferedImage getSkyBackground() {
		return skyBackground;
	}

	public static void setSkyBackground(BufferedImage skyBackground) {
		Images.skyBackground = skyBackground;
	}

	public static BufferedImage getSeaBackground() {
		return seaBackground;
	}

	public static void setSeaBackground(BufferedImage seaBackground) {
		Images.seaBackground = seaBackground;
	}

	public static BufferedImage getSpaceBarrier() {
		return spaceBarrier;
	}

	public static void setSpaceBarrier(BufferedImage spaceBarrier) {
		Images.spaceBarrier = spaceBarrier;
	}

	public static BufferedImage getSkyBarrier() {
		return skyBarrier;
	}

	public static void setSkyBarrier(BufferedImage skyBarrier) {
		Images.skyBarrier = skyBarrier;
	}

	public static BufferedImage getSeaBarrier() {
		return seaBarrier;
	}

	public static void setSeaBarrier(BufferedImage seaBarrier) {
		Images.seaBarrier = seaBarrier;
	}

	public static BufferedImage getBlueEnemy() {
		return blueEnemy;
	}

	public static void setBlueEnemy(BufferedImage blueEnemy) {
		Images.blueEnemy = blueEnemy;
	}

	public static BufferedImage getRedEnemy() {
		return redEnemy;
	}

	public static void setRedEnemy(BufferedImage redEnemy) {
		Images.redEnemy = redEnemy;
	}

	public static BufferedImage getPurpleEnemy() {
		return purpleEnemy;
	}

	public static void setPurpleEnemy(BufferedImage purpleEnemy) {
		Images.purpleEnemy = purpleEnemy;
	}

	public static BufferedImage getFlyingEnemy() {
		return flyingEnemy;
	}

	public static void setFlyingEnemy(BufferedImage flyingEnemy) {
		Images.flyingEnemy = flyingEnemy;
	}

	public static BufferedImage getBlueProjectile() {
		return blueProjectile;
	}

	public static void setBlueProjectile(BufferedImage blueProjectile) {
		Images.blueProjectile = blueProjectile;
	}

	public static BufferedImage getRedProjectile() {
		return redProjectile;
	}

	public static void setRedProjectile(BufferedImage redProjectile) {
		Images.redProjectile = redProjectile;
	}

	public static BufferedImage getPurpleProjectile() {
		return purpleProjectile;
	}

	public static void setPurpleProjectile(BufferedImage purpleProjectile) {
		Images.purpleProjectile = purpleProjectile;
	}

	public static BufferedImage getSpaceshipProjectile() {
		return spaceshipProjectile;
	}

	public static void setSpaceshipProjectile(BufferedImage spaceshipProjectile) {
		Images.spaceshipProjectile = spaceshipProjectile;
	}

	public static BufferedImage getSpaceship() {
		return spaceship;
	}

	public static void setSpaceship(BufferedImage spaceship) {
		Images.spaceship = spaceship;
	}

	public static BufferedImage getRedEnemyRotatedRight() {
		return redEnemyRotatedRight;
	}

	public static void setRedEnemyRotatedRight(BufferedImage redEnemyRotatedRight) {
		Images.redEnemyRotatedRight = redEnemyRotatedRight;
	}

	public static BufferedImage getRedEnemyRotatedLeft() {
		return redEnemyRotatedLeft;
	}

	public static void setRedEnemyRotatedLeft(BufferedImage redEnemyRotatedLeft) {
		Images.redEnemyRotatedLeft = redEnemyRotatedLeft;
	}

	public static BufferedImage getHomePage() {
		return homePage;
	}

	public static void setHomePage(BufferedImage homePage) {
		Images.homePage = homePage;
	}

	public static BufferedImage getTitle() {
		return title;
	}

	public static void setTitle(BufferedImage title) {
		Images.title = title;
	}

	public static BufferedImage getPlayGameButton() {
		return playGameButton;
	}

	public static void setPlayGameButton(BufferedImage playGameButton) {
		Images.playGameButton = playGameButton;
	}

	public static BufferedImage getPurpleBirdEnemy() {
		return purpleBirdEnemy;
	}

	public static void setPurpleBirdEnemy(BufferedImage purpleBirdEnemy) {
		Images.purpleBirdEnemy = purpleBirdEnemy;
	}

	public static BufferedImage getGreenEnemy() {
		return greenEnemy;
	}

	public static void setGreenEnemy(BufferedImage greenEnemy) {
		Images.greenEnemy = greenEnemy;
	}

	public static BufferedImage getOrangeEnemy() {
		return orangeEnemy;
	}

	public static void setOrangeEnemy(BufferedImage orangeEnemy) {
		Images.orangeEnemy = orangeEnemy;
	}

	public static BufferedImage getEnemies() {
		return enemies;
	}

	public static void setEnemies(BufferedImage enemies) {
		Images.enemies = enemies;
	}

	public static BufferedImage getInstructions() {
		return instructions;
	}

	public static void setInstructions(BufferedImage instructions) {
		Images.instructions = instructions;
	}

	public static BufferedImage getHomePageBackground() {
		return homePageBackground;
	}

	public static void setHomePageBackground(BufferedImage homePageBackground) {
		Images.homePageBackground = homePageBackground;
	}

	public static BufferedImage getYellowFish() {
		return yellowFish;
	}

	public static void setYellowFish(BufferedImage yellowFish) {
		Images.yellowFish = yellowFish;
	}

	public static BufferedImage getGreenFishR() {
		return greenFishR;
	}

	public static void setGreenFishR(BufferedImage greenFishR) {
		Images.greenFishR = greenFishR;
	}

	public static BufferedImage getPinkFishR() {
		return pinkFishR;
	}

	public static void setPinkFishR(BufferedImage pinkFishR) {
		Images.pinkFishR = pinkFishR;
	}

	public static BufferedImage getGreenFishL() {
		return greenFishL;
	}

	public static void setGreenFishL(BufferedImage greenFishL) {
		Images.greenFishL = greenFishL;
	}

	public static BufferedImage getPinkFishL() {
		return pinkFishL;
	}

	public static void setPinkFishL(BufferedImage pinkFishL) {
		Images.pinkFishL = pinkFishL;
	}

	public static BufferedImage getRestoreBarriers() {
		return restoreBarriers;
	}

	public static void setRestoreBarriers(BufferedImage restoreBarriers) {
		Images.restoreBarriers = restoreBarriers;
	}

	public static BufferedImage getHeart() {
		return heart;
	}

	public static void setHeart(BufferedImage heart) {
		Images.heart = heart;
	}

	public static BufferedImage getSmallerSpaceship() {
		return smallerSpaceship;
	}

	public static void setSmallerSpaceship(BufferedImage smallerSpaceship) {
		Images.smallerSpaceship = smallerSpaceship;
	}

	public static BufferedImage getDessertBackground() {
		return dessertBackground;
	}
	
	public static BufferedImage getFreezeEnemies() {
		return freezeEnemies;
	}

	public static BufferedImage getInvisibleBarrier() {
		return invisibleBarrier;
	}
	
	public static void setDessertBackground(BufferedImage dessertBackground) {
		Images.dessertBackground = dessertBackground;
	}

	public static BufferedImage getGameOverWindow() {
		return gameOverWindow;
	}

	public static void setGameOverWindow(BufferedImage gameOverWindow) {
		Images.gameOverWindow = gameOverWindow;
	}

	public static void setFreezeEnemies(BufferedImage freezeEnemies) {
		Images.freezeEnemies = freezeEnemies;
	}

	public static void setInvisibleBarrier(BufferedImage invisibleBarrier) {
		Images.invisibleBarrier = invisibleBarrier;
	}

	public static BufferedImage getRocketProjectile() {
		
		return rocketProjectile;
	}

	public static BufferedImage getInfo() {
		return info;
	}

	public static void setInfo(BufferedImage info) {
		Images.info = info;
	}

	public static void setRocketProjectile(BufferedImage rocketProjectile) {
		Images.rocketProjectile = rocketProjectile;
	}

	public static BufferedImage getPlay() {
		return play;
	}

	public static void setPlay(BufferedImage play) {
		Images.play = play;
	}

	public static BufferedImage getPause() {
		return pause;
	}

	public static void setPause(BufferedImage pause) {
		Images.pause = pause;
	}

	public static BufferedImage getDessertBarrier() {
		return dessertBarrier;
	}

	public static void setDessertBarrier(BufferedImage dessertBarrier) {
		Images.dessertBarrier = dessertBarrier;
	}

	public static BufferedImage getInfoPanel() {
		return infoPanel;
	}

	public static void setInfoPanel(BufferedImage infoPanel) {
		Images.infoPanel = infoPanel;
	}

}
