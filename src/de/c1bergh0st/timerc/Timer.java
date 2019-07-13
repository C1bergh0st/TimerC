package de.c1bergh0st.timerc;

import de.c1bergh0st.timerc.gui.TimerPanel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess", "CanBeFinal"})
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

    /**
     * Creates a new Timer
     * @param name The name of the Timer
     * @param message Its message (currently unused)
     * @param duration The duration in milliseconds
     * @param looping Whether it should loop
     * @param soundsOnEnd Whether is should play a sound on end TODO: implement
     */
    public Timer(String name, String message, long duration, boolean looping, boolean soundsOnEnd) {
        this.name = name;
        this.message = message;
        this.soundsOnEnd = soundsOnEnd;
        this.looping = looping;
        this.duration = duration;
        this.observers = new ArrayList<>();
        reset();
    }

    /**
     * Creates a new non-looping Timer which sounds on end
     * @param name The name of the Timer
     * @param message Its message (currently unused)
     * @param duration The duration in milliseconds
     */
    @SuppressWarnings("WeakerAccess")
    public Timer(String name, String message, long duration){
        this(name, message, duration, false, true);
    }

    /**
     * Creates a new non-looping Timer which sounds on end and has an empty Message
     * @param name The name of the Timer
     * @param duration The duration in milliseconds
     */
    @SuppressWarnings("unused")
    public Timer(String name, long duration){
        this(name, "", duration);
    }

    /**
     * Checks if this Timer has ended and notifies all observers if it has ended
     */
    public void update() {
        if(this.hasEnded() && !notifiedObservers && !paused){
            notifiedObservers = true;
            notifyObservers();
            if(looping){
                reset();
            }
        }
    }

    private void notifyObservers(){
        observers.forEach(Observer::alert);
    }

    /**
     * Registers a new Observer to be notified on timer end
     * @param observer the Observer to be added to this Subject
     */
    public void register(Observer observer){
        observers.add(observer);
    }

    /**
     * @return the name of this Timer
     */
    public String getName() {
        return name;
    }

    /**
     * @return the message of this Timer
     */
    @SuppressWarnings("unused")
    public String getMessage() {
        return message;
    }

    /**
     * @return true if this Timer is looping
     */
    public boolean isLooping(){
        return looping;
    }

    /**
     * @return true if this Timer is currently paused
     */
    public boolean isPaused(){
        return paused;
    }

    /**
     * @return true if this Timer should play a sound on end
     */
    @SuppressWarnings("unused")
    public boolean shouldSound(){
        return soundsOnEnd;
    }

    /**
     * @return true if this Timer has ended
     */
    public boolean hasEnded(){
        return System.currentTimeMillis() > end;
    }

    /**
     * @return the remaining time of this Timer in milliseconds
     */
    public long getRemaining(){
        if(this.paused) return pauseRemaining;
        if(this.hasEnded()) return  0;
        return end - System.currentTimeMillis();
    }

    public long getCycleDuration(){
        return this.duration;
    }

    /**
     * @return the local timestamp of when this timer will end NOTE: will return wrong results while this Timer is paused
     */
    public long getEnd() {
        return end;
    }

    /**
     * Resets this Timer to its full duration and starts it
     */
    public void reset(){
        this.start = System.currentTimeMillis();
        this.end = start + duration;
        this.paused = false;
        notifiedObservers = false;
    }

    /**
     * Used for Sorting
     * @param o the Object to compare this Timer to
     * @return this.end - other.end
     */
    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Timer)) return Integer.MIN_VALUE;
        Timer other = (Timer) o;
        return (int)(this.getEnd() - other.getEnd());
    }

    /**
     * Pauses this Timer
     * @param pauseTime the timestamp of when the Timer should have been paused (useful for synchronous pauses)
     */
    public void pause(long pauseTime) {
        this.paused = true;
        this.pauseRemaining = end - pauseTime;
        if(this.hasEnded()) this.pauseRemaining = 0;
    }

    /**
     * Starts this Timer
     * @param startTime the timestamp of when the Timer should have been started (useful for synchronous starts)
     */
    public void start(long startTime) {
        this.paused = false;
        this.end = startTime + pauseRemaining;
    }

    public String toString(){
        return name + "(" + TimerPanel.format(duration) + ")";
    }
}
