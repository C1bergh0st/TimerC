package de.c1bergh0st.timerc;

import de.c1bergh0st.timerc.gui.MainFrame;
import de.c1bergh0st.timerc.gui.TimerPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TimerController {
    private List<Timer> timers;
    private MainFrame frame;

    public TimerController(){
        timers = new CopyOnWriteArrayList<>();
    }


    public void alert(){
        System.out.println("Alerted");
        //new Sound("/res/sounds/definite.wav", 0, false);
        //audioController.playBackground("definite.wav");
        //audioController.playSound("definite.wav");
    }

    public void pauseAll(){
        long pauseTime = System.currentTimeMillis();
        for(Timer timer: timers){
            timer.pause(pauseTime);
        }
    }

    public void startAll(){
        long startTime = System.currentTimeMillis();
        for(Timer timer: timers){
            timer.start(startTime);
        }
    }


    public void update(){
        for(Timer timer: timers){
            timer.update();
        }
    }

    public synchronized void add(Timer timer){
        if(timers.contains(timer)){
            return;
        }
        timers.add(timer);
        frame.add(new TimerPanel(timer));
    }

    public void setMainFrame(MainFrame frame){
        this.frame = frame;
    }

}
