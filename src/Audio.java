import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Audio {
	static String hardKillSound = "Audio/HardKillSound.wav";
	static String whooshFile = "Audio/Whoosh.wav";
	static String softKillSound = "Audio/CollisionWithEnemies.wav";
	static String launchProjectile = "Audio/LaunchProjectile.wav";
	static InputStream inputStream;

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

}
