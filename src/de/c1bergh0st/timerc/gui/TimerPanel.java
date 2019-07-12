package de.c1bergh0st.timerc.gui;

import de.c1bergh0st.timerc.Observer;
import de.c1bergh0st.timerc.Timer;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

public class TimerPanel extends JPanel implements Observer {
    public static final int BLINK_DURATION = 300;
    public static final int BLINK_TIME = 10 * 1000;
    private Timer timer;
    private JLabel remainingTime;
    private boolean colorChange;
    private long endBlink;
    private long lastBlink;
    private boolean red;
    private Color normal;

    public TimerPanel(Timer timer){
        super();
        this.timer = timer;
        timer.register(this);
        this.setLayout(new BorderLayout());
        this.add(new JLabel(timer.getName()), BorderLayout.NORTH);
        remainingTime = new JLabel(format(timer.getRemaining()));
        this.add(remainingTime);
        this.setPreferredSize(new Dimension(750, 80));
        colorChange = false;
    }

    public Timer getTimer(){
        return timer;
    }


    public void refresh() {
        if(remainingTime != null){
            remainingTime.setText(format(timer.getRemaining()));
        }
        if(colorChange && endBlink > System.currentTimeMillis()){
            if(lastBlink + BLINK_DURATION < System.currentTimeMillis()){
                lastBlink = System.currentTimeMillis();
                red = !red;
                if(red){
                    setBackground(Color.RED);
                } else {
                    setBackground(normal);
                }
                revalidate();
                repaint();
            }
        }
    }

    private String format(long millis){
        int min = (int) (millis / (60 * 1000));
        int sec = (int) ((millis / 1000) % 60);
        int millisec = (int) (millis % 1000) / 10;
        return  String.format("%02d:%02d.%02d", min, sec, millisec);
    }

    @Override
    public void noteEnd() {
        colorChange = true;
        endBlink = System.currentTimeMillis() + BLINK_TIME;
        lastBlink = 0;
        red = false;
        normal = getBackground();
    }
}
