import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

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

		inputStream = new FileInputStream("src/Images/SpaceBarrier.png");
		spaceBarrier = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/SkyBarrier.png");
		skyBarrier = ImageIO.read(inputStream);

		inputStream = new FileInputStream("src/Images/SeaBarrier.png");
		seaBarrier = ImageIO.read(inputStream);

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

	}

	public static boolean inBounds(BufferedImage image, int r, int c) {
		if (r >= 0 && r < image.getHeight() && c >= 0 && c < image.getWidth()) {
			return true;
		}
		return false;
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
}
