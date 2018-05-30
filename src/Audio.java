import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
	private boolean playBackgroundMusic = false;
	private Clip clip = null;

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
	public void makeBackgroundMusic70s() {
		clip = null;
		AudioInputStream audioStream = null;
		try {
			audioStream = AudioSystem.getAudioInputStream(this.getClass().getResource(backgroundMusic));
		} catch (UnsupportedAudioFileException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
			
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        	try {
    			clip.open(audioStream);
    		} catch (LineUnavailableException | IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        
        
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        
//        try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
	public boolean isPlayBackgroundMusic() {
		return playBackgroundMusic;
	}
	public void setPlayBackgroundMusic(boolean playBackgroundMusic) {
		this.playBackgroundMusic = playBackgroundMusic;
		if(playBackgroundMusic) {
			makeBackgroundMusic70s();
		}
		else {
			System.out.println("closing");
			clip.close();
		}
	}
	
}
