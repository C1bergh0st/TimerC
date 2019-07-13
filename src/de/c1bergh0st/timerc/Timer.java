package de.c1bergh0st.timerc;

import java.util.ArrayList;
import java.util.List;

public class Timer implements Comparable{
    private String name;
    private String message;
    private boolean soundsOnEnd;
    private boolean looping;
    private long duration;
    private long start;
    private long end;
    private List<Observer> observers;
    private boolean notifiedObservers;
    private boolean paused;
    private long pauseRemaining;


    public Timer(String name, String message, long duration, boolean looping, boolean soundsOnEnd) {
        this.name = name;
        this.message = message;
        this.soundsOnEnd = soundsOnEnd;
        this.looping = looping;
        this.duration = duration;
        this.observers = new ArrayList<>();
        paused = false;
        reset();
    }

    public Timer(String name, String message, long duration){
        this(name, message, duration, false, true);
    }

    public Timer(String name, long duration){
        this(name, "", duration);
    }


    public void update() {
        if(this.hasEnded() && !notifiedObservers && !paused){
            notifiedObservers = true;
            notifyObservers();
            if(looping){
                reset();
            }
        }
    }

    public void register(Observer observer){
        observers.add(observer);
    }

    private void notifyObservers(){
        observers.forEach(Observer::alert);
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

    public boolean isPaused(){
        return paused;
    }

    public boolean shouldSound(){
        return soundsOnEnd;
    }

    public boolean hasEnded(){
        return System.currentTimeMillis() > end;
    }

    public long getRemaining(){
        if(this.paused) return pauseRemaining;
        if(this.hasEnded()) return  0;
        return end - System.currentTimeMillis();
    }

    public long getEnd() {
        return end;
    }

    public void reset(){
        this.start = System.currentTimeMillis();
        this.end = start + duration;
        notifiedObservers = false;
    }

    @Override
    public int compareTo(Object o) {
        Timer other = (Timer) o;
        return (int)(this.getEnd() - other.getEnd());
    }

    public void pause(long pauseTime) {
        this.paused = true;
        this.pauseRemaining = end - pauseTime;
        if(this.hasEnded()) this.pauseRemaining = 0;
    }

    public void start(long startTime) {
        this.paused = false;
        this.end = startTime + pauseRemaining;
    }
}
