package de.c1bergh0st.audio;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class AudioController {
	private static final int MAX_ACTIVE_SOUNDS = 32;
	private int activeSounds;
	private Set<Integer> availableIds;
	private LinkedList<Sound> sounds;
	private LinkedList<Sound> removeables;
	private Sound loopingSound;
	
	/**
	 * creates a new AudioController
	 */
	public AudioController(){
	    sounds = new LinkedList<>();
	    removeables = new LinkedList<>();
	    availableIds = new HashSet<>();
	    populateIds();
	}
	
	
	
	private int getFirstFreeId(){
	    if(availableIds.isEmpty()){
	        //Error handling
	    }
	    for(int i = 0; i < MAX_ACTIVE_SOUNDS; i++){
	        if(availableIds.contains(i)){
	            return i;
	        }
	    }
	    return -1;
	}
	
	private void populateIds(){
	    for(int i = 0; i < MAX_ACTIVE_SOUNDS; i++){
	        availableIds.add(i);
	    }
	}
	
	/**
	 * stops and terminates the current Background Sound and starts a new one based on the given path
	 * @param path inside the sound folder (adds /res/sounds/ to the path
	 */
	public void playBackground(String path){
	    stopBackground();
		loopingSound = new Sound("/res/sounds/" + path,-1,true);
		activeSounds++;
	}
	
	/**
	 * Stops any playing background sound
	 */
	public void stopBackground(){
	    if(loopingSound != null){
	        loopingSound.terminate();
	    }
		loopingSound = null;
		activeSounds--;
	}
	
	/**
	 * @return the number of currently playing Sounds
	 */
	public int getPlayingSoundsCount(){
		return activeSounds;
	}
	
	/**
	 * does internal housekeeping like terminating and destroying finished Sounds
	 */
	public void update(){
	    Sound s;
		for (Sound sound : sounds) {
			s = sound;
			if (s.isFinished() && !removeables.contains(s)) {
				removeables.add(s);
			}
		}
		for(Sound temp : removeables){
		    temp.terminate();
			sounds.remove(temp);
			availableIds.add(temp.getId());
			activeSounds--;
		}
        removeables = new LinkedList<>();
	}
	
	
	/**
	 * Starts playing a 
	 * @param path the path to the Sound inside the sounds folder
	 * @return the assigned id of the Sound or -1 if no new Sounds could be created
	 */
	public int playSound(String path){
		if(activeSounds < MAX_ACTIVE_SOUNDS+1){
			activeSounds++;
			int id = getFirstFreeId();
			sounds.add(new Sound("/res/sounds/" + path, id ,false));
			availableIds.remove(id);
		}
		else{
			System.err.println("No more than " + MAX_ACTIVE_SOUNDS + " supported");
		}
		return -1;
	}
	
	
	/**
	 * Stops, terminates and removes the sound with the given id
	 * @param id the id of the Sound to stop Playing
	 */
	public void stopSound(int id){
	    for(Sound s: sounds){
	        if(s.getId() == id){
	            s.terminate();
	        }
	        removeables.add(s);
	    }
	}
}
