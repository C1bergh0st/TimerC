package de.c1bergh0st.timerc;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimerController {
    private List<Timer> timers;

    public TimerController(){
        timers = new ArrayList<Timer>();
    }


    public void update(){
        for (Timer timer : timers) {
            if(timer.hasEnded()){
                if(timer.shouldSound()){
                    Toolkit.getDefaultToolkit().beep();
                }
                if(timer.isLooping()){
                    timer.reset();
                } else {
                    //Might cause concurrentException, need to check if iterator supports removal
                    timers.remove(timer);
                    timer.invalidate();
                }
            }
        }
    }

    public void add(Timer timer){
        if(timers.contains(timer)){
            return;
        }
        timers.add(timer);
        timers.sort(new Comparator<Timer>() {
            @Override
            public int compare(Timer timer, Timer t1) {
                return (int)(timer.getEnd() - t1.getEnd());
            }
        });
    }


}
