package music;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music_Player {

	private static Clip clip;
	private static long clipTime = 0;
	
	public Music_Player() {
	}
	
	public void playSound(String file) {
		if (clip != null) clipTime = clip.getMicrosecondPosition();
		stopSound(); 
		try {
			URL defaultSound = getClass().getResource(file);
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
		    clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    clip.start();
		    clip.loop(Clip.LOOP_CONTINUOUSLY); 
		} catch(Exception e) {
			System.out.println("Error with playing sound.");
	        e.printStackTrace();
		}
	}
	
	public void differentSound(String file) {
		stopSound(); 
		try {
			URL defaultSound = getClass().getResource(file);
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
		    clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    clip.start();
		    clip.loop(Clip.LOOP_CONTINUOUSLY); 
		} catch(Exception e) {
			System.out.println("Error with playing sound.");
	        e.printStackTrace();
		}
	}
	
	public void resumeSound(String file) {
		stopSound();
		try {
			URL defaultSound = getClass().getResource(file);
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
		    clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    clip.setMicrosecondPosition(clipTime);
		    clip.start();
		    clip.loop(Clip.LOOP_CONTINUOUSLY); 
		    clipTime = 0;
		} catch(Exception e) {
			System.out.println("Error with playing sound.");
	        e.printStackTrace();
		}
	}
	
	public static void stopSound() {
		if (clip != null) {
			clip.stop(); 
			clip.close();
		}
	}
	
	public static void setClipTime() {
		clipTime = clip.getMicrosecondPosition();
	}
	
}
