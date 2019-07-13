package de.c1bergh0st.timerc;

import de.c1bergh0st.timerc.gui.MainFrame;
import de.c1bergh0st.timerc.gui.TimerPanel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TimerController {
    private final List<Timer> timers;
    private MainFrame frame;

    /**
     * Creates an empty TimerController
     */
    public TimerController(){
        timers = new CopyOnWriteArrayList<>();
    }

    /**
     * Pauses all Timers who are registered in this TimerController
     */
    public void pauseAll(){
        long pauseTime = System.currentTimeMillis();
        for(Timer timer: timers){
            timer.pause(pauseTime);
        }
    }


    /**
     * Starts all Timers who are registered in this TimerController
     */
    public void startAll(){
        long startTime = System.currentTimeMillis();
        for(Timer timer: timers){
            timer.start(startTime);
        }
    }

    /**
     * updates all Timers who are registered in this TimerController
     */
    public void update(){
        for(Timer timer: timers){
            timer.update();
        }
    }

    /**
     * Adds a Timer to this TimerController and registers a new TimerPanel in the MainFrame
     * @param timer the Timer to add
     */
    public void add(Timer timer){
        if(timers.contains(timer)){
            return;
        }
        timers.add(timer);
        frame.add(new TimerPanel(timer));
    }

    /**
     * Sets this Timers MainFrame
     * @param frame the MainFrame to set
     */
    public void setMainFrame(MainFrame frame){
        this.frame = frame;
    }

    public List<Timer> getAllTimers() {
        List<Timer> list = new LinkedList<>();
        list.addAll(timers);
        return list;
    }
}
