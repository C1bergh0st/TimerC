package de.c1bergh0st.timerc;

import de.c1bergh0st.timerc.gui.MainFrame;
import de.c1bergh0st.timerc.gui.TimerPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TimerController {
    private List<Timer> timers;
    private List<Timer> removalQueue;
    private MainFrame frame;
    private boolean refresh;

    public TimerController(){
        timers = new CopyOnWriteArrayList<>();
        Thread t = new Thread() {

            @Override
            public void run() {
                while(true){
                    update();
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        };
        t.start();
        System.out.println("Thread started");
    }

    public void setMainFrame(MainFrame frame){
        this.frame = frame;
    }


    public void update(){
        removalQueue = new LinkedList<>();
        refresh = false;
        for (Timer timer : timers) {
            timer.update();
            timer.getObservers().forEach(Observer::refresh);
        }
        if(removalQueue.size() > 0){
            //timers.removeAll(removalQueue);
            queRefresh();
        }
        if (refresh){
            frame.forceRefresh();
        }
    }

    public void queRefresh(){
        refresh = true;
    }

    public void queueRemoval(Timer timer){
        removalQueue.add(timer);
    }

    public synchronized void add(Timer timer){
        timer.setController(this);
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
