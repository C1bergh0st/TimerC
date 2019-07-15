package de.c1bergh0st.timerc.sound;

import de.c1bergh0st.audio.Sound;
import de.c1bergh0st.timerc.Observer;
import de.c1bergh0st.timerc.Timer;

public class SoundObserver implements Observer {
    Sound sound;

    public SoundObserver(Timer timer){
        timer.register(this);
    }

    @Override
    public void alert() {
        if(sound != null) sound.terminate();
        sound = new Sound("/res/sounds/definite.wav", 1 ,false);
    }

    @Override
    public void terminate() {
        if(sound != null){
            sound.terminate();
        }
    }
}
