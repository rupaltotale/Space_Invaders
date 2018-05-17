import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Audio {
	static File killEnemyFile = new File("src/Audio/SoftKillSound.wav");
	static File killSpaceshipFile = new File("src/Audio/KillSpaceship.wav");
	static InputStream inputStream;

	public static void makeSound(File file) {
		AudioStream sound = null;
		try {
			inputStream = new FileInputStream(file);
			try {
				sound = new AudioStream(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		AudioPlayer.player.start(sound);
	}
	
	public static void makeKillingSoundForEnemy() {
		makeSound(killEnemyFile);
	}
	public static void makeKillingSoundForSpaceship() {
		makeSound(killSpaceshipFile);
	}
	

}
