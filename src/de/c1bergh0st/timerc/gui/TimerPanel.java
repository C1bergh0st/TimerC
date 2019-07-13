package de.c1bergh0st.timerc.gui;

import de.c1bergh0st.timerc.Observer;
import de.c1bergh0st.timerc.Timer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import java.awt.*;

public class TimerPanel extends JPanel implements Observer {
    private static final int BLINK_CYCLE_DUR = 250;
    private static final int BLINK_DUR = 6 * 1000;
    private Timer timer;
    private JLabel remainingTime;
    private JButton toggle;
    private Color normal;
    private Color alertColor = new Color(255, 92, 92);
    private long endblink;
    private long lastBlink;
    private boolean red;

    public TimerPanel(Timer timer){
        super();
        if(timer.isLooping()){
            this.setBackground(new Color(168, 240, 223));
        }
        this.timer = timer;
        this.setBorder(new LineBorder(Color.BLACK));
        timer.register(this);
        normal = this.getBackground();
        this.setLayout(new BorderLayout());
        JLabel name = new JLabel(timer.getName());
        name.setFont(name.getFont().deriveFont(15f));
        Box b3 = Box.createHorizontalBox();
        b3.setBorder(new EmptyBorder(20,20,10,20));
        b3.add(name);
        //name.setBorder(new EmptyBorder(20,20,10,20));
        //this.add(name, BorderLayout.NORTH);
        remainingTime = new JLabel("Initializing");
        remainingTime.setFont(remainingTime.getFont().deriveFont(22f));
        Box b = Box.createHorizontalBox();
        b.add(remainingTime);
        b.add(Box.createHorizontalStrut(50));
        this.add(b, BorderLayout.EAST);
        toggle = new JButton("");
        toggle.setPreferredSize(new Dimension(80,20));
        toggle.addActionListener(e ->{
            if(timer.isPaused()){
                timer.start(System.currentTimeMillis());
            } else {
                timer.pause(System.currentTimeMillis());
            }
        });
        Box b2 = Box.createHorizontalBox();
        b2.setBorder(new EmptyBorder(5,20,5,20));
        b2.add(toggle);
        Box left = Box.createVerticalBox();
        left.add(b3);
        left.add(b2);
        this.add(left, BorderLayout.WEST);
        //this.setPreferredSize(new Dimension(700, 100));
    }

    private String format(long millis){
        int min = (int) (millis / (60 * 1000));
        int sec = (int) ((millis / 1000) % 60);
        int millisec = (int) (millis % 1000) / 10;
        return  String.format("%02d:%02d.%02d", min, sec, millisec);
    }

    public void refresh(){
        remainingTime.setText(format(timer.getRemaining()));
        if(!timer.hasEnded()){
            if(timer.isPaused()){
                toggle.setText("Start");
            } else {
                toggle.setText("Pause");
            }
        } else {
            toggle.setEnabled(false);
        }
        if (endblink > System.currentTimeMillis()){
            if(lastBlink + BLINK_CYCLE_DUR < System.currentTimeMillis()){
                red = !red;
                if(red){
                    setBackground(alertColor);
                } else {
                    setBackground(normal);
                }
                lastBlink = System.currentTimeMillis();
            }
        } else {
            if(timer.isLooping()){
                setBackground(normal);
            }
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void alert() {
        endblink = System.currentTimeMillis() + BLINK_DUR;
        if(timer.isLooping()){
            red = true;
        }
    }
}
