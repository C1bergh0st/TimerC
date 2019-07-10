package de.c1bergh0st.timerc;

public class Timer {
    private String name;
    private String message;
    private boolean soundsOnEnd;
    private boolean looping;
    private long duration;
    private long start;
    private long end;
    private boolean valid;

    public Timer(String name, String message, long duration, boolean looping, boolean soundsOnEnd) {
        this.name = name;
        this.message = message;
        this.soundsOnEnd = soundsOnEnd;
        this.looping = looping;
        this.duration = duration;
        this.valid = true;
        reset();
    }

    public Timer(String name, String message, long duration){
        this(name, message, duration, false, true);
    }

    public Timer(String name, long duration){
        this(name, "", duration);
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
        this.valid = true;
    }

    public void invalidate(){
        valid = false;
    }

    public boolean isValid(){
        return valid;
    }

}
