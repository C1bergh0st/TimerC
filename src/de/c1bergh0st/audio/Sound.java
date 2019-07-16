package de.c1bergh0st.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

@SuppressWarnings({"FieldCanBeLocal", "unused", "WeakerAccess"})
public class Sound {
	private Clip clip;
	private AudioInputStream ais;
	private final int id;
	@SuppressWarnings("unused")
    private final String path;
	private boolean terminated;
	
	/**
	 * Constructs a new Sound
	 * @param path The path to the SoundFile
	 * @param id a (hopefully) unique id for this Sound
	 * @param looping whether this Sound should loop or not
	 */
	public Sound(String path, int id, boolean looping){
		this.path = path;
		this.id = id;
        terminated = false;
		try {
            clip = AudioSystem.getClip();
            long now = System.currentTimeMillis();
            System.out.println("attempting to load from " + path);
            BufferedInputStream buffer = new BufferedInputStream(Sound.class.getResourceAsStream(path));
            ais = AudioSystem.getAudioInputStream(buffer);
            System.out.println("Loading " + path + " took " + (System.currentTimeMillis() - now) + " ms");
            clip.open(ais);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException  e) {
            //TODO Exception-Handling
            throw new IllegalStateException(e.getMessage(), e);
        }
		if(looping){
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			clip.loop(0);
		}
	}
	
	/**
	 * @return the id assigned to this Sound
	 */
	public int getId(){
	    return id;
	}
	
	/**
	 * Starts playing the Sound
	 */
	public void play(){
	    if(terminated){
            throw new IllegalStateException("This Sound was already terminated");
        }
	    clip.start();
	}
	
	/**
	 * Stops playing the Sound
	 */
	public void stop(){
	    if(terminated){
	        throw new IllegalStateException("This Sound was already terminated");
	    }
	    clip.stop();
	}
	
	/**
	 * Determines whether this Sound has finished playback.
	 * !Will always return true if this Sound is set to loop!
	 * @return true if the sound has finished playback
	 */
	public boolean isFinished(){
		return !clip.isRunning();
	}
	
	/**
	 * Stops playing the sound and terminates all Resources associated with this Object
	 */
	public void terminate(){
		stop();
		try {
			ais.close();
			clip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		terminated = true;
	}
	
	/**
	 * @return whether this Sound has been Terminated
	 */
	public boolean isTerminated(){
	    return terminated;
	}
	
	
}
