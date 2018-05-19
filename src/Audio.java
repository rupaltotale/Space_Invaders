import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Audio {
	static File hardKillSound = new File("src/Audio/HardKillSound.wav");
	static File whooshFile = new File("src/Audio/Whoosh.wav");
	static File softKillSound = new File("src/Audio/SoftKillSound.wav");
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
	
	public static void makeHardKillingSoundForEnemy() {
		makeSound(hardKillSound);
	}
	
	public static void makeKillingSoundForSpaceship() {
		makeSound(whooshFile);
	}

	public static void makeSoftKillingSoundForEnemy() {
		makeSound(softKillSound);		
	}
	

}
