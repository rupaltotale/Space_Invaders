import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Audio {
	private static String hardKillSound = "Audio/HardKillSound.wav";
	private static String whooshFile = "Audio/Whoosh.wav";
	private static String softKillSound = "Audio/CollisionWithEnemies.wav";
	private static String launchProjectile = "Audio/LaunchProjectile.wav";
	private static String bubblePop = "Audio/BubblePop.wav";
	private static String backgroundMusic = "Audio/BackgroundMusic70s.wav";
	private static InputStream inputStream;

	public void makeSound(String file) {
		AudioStream sound = null;

		inputStream = this.getClass().getResourceAsStream(file);
		try {
			sound = new AudioStream(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AudioPlayer.player.start(sound);
	}

	public void makeHardKillingSoundForEnemy() {
		makeSound(hardKillSound);
	}

	public void makeKillingSoundForSpaceship() {
		makeSound(whooshFile);
	}

	public void makeSoftKillingSoundForEnemy() {
		makeSound(softKillSound);
	}

	public void makeLaunchProjectileSound() {
//		System.out.println("makingsound");
		makeSound(launchProjectile);
	}
	public void makeBubblePopSound() {
//		System.out.println("makingsound");
		makeSound(bubblePop);
	}
	public void make70sBackgroundMusic() {
//		System.out.println("makingsound");
		makeSound(backgroundMusic);
	}
}
