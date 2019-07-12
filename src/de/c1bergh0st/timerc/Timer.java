package de.c1bergh0st.timerc;

import de.c1bergh0st.timerc.gui.MainFrame;
import de.c1bergh0st.timerc.gui.TimerPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Timer implements Comparable{
    private String name;
    private String message;
    private boolean soundsOnEnd;
    private boolean looping;
    private long duration;
    private long start;
    private long end;
    private boolean once;
    private Set<Observer> observers;
    private TimerController timerController;

    public Timer(String name, String message, long duration, boolean looping, boolean soundsOnEnd) {
        this.name = name;
        this.message = message;
        this.soundsOnEnd = soundsOnEnd;
        this.looping = looping;
        this.duration = duration;
        this.once = true;
        this.observers = new HashSet<>();
        reset();
    }

    public Timer(String name, String message, long duration){
        this(name, message, duration, false, true);
    }

    public Timer(String name, long duration){
        this(name, "", duration);
    }

    public void update(){
        if(this.hasEnded() && once){
            once = false;
            if(this.shouldSound()){
                Toolkit.getDefaultToolkit().beep();
            }

            if(this.isLooping()){
                this.reset();
            } else {
                timerController.queueRemoval(this);
            }
            observers.forEach(Observer::noteEnd);
            timerController.queRefresh();
        }
    }

    public Set<Observer> getObservers(){
        return observers;
    }

    public void register(Observer observer){
        observers.add(observer);
    }


    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public boolean isLooping(){
        return looping;
    }

    public boolean shouldSound(){
        return soundsOnEnd;
    }

    public boolean hasEnded(){
        return System.currentTimeMillis() > end;
    }

    public long getRemaining(){
        if(this.hasEnded()) return  0;
        return end - System.currentTimeMillis();
    }

    public long getEnd() {
        return end;
    }

    public void reset(){
        this.start = System.currentTimeMillis();
        this.end = start + duration;
        this.once = true;
    }

    @Override
    public int compareTo(Object o) {
        Timer other = (Timer) o;
        return (int)(this.getEnd() - other.getEnd());
    }

    public void setController(TimerController timerController) {
        this.timerController = timerController;
    }
}
