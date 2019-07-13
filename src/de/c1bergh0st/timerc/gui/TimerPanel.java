package de.c1bergh0st.timerc.gui;

import de.c1bergh0st.timerc.Observer;
import de.c1bergh0st.timerc.Timer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TimerPanel extends JPanel implements Observer {
    private static final int BLINK_CYCLE_DUR = 250;
    private static final int BLINK_DUR = 6 * 1000;
    private static final Color LOOPING_COLOR_BACKGROUND = new Color(168, 240, 223);
    private static final Color alertColor = new Color(255, 92, 92);

    private final Timer timer;
    private final JLabel remainingTime;
    private final JButton toggle;
    private final Color normal;
    private long endBlink;
    private long lastBlink;
    private boolean red;

    /**
     * Creates a new TimerPanel from the given Timer
     * @param timer A JPanel which can be used in a swing application
     */
    public TimerPanel(Timer timer){
        super();
        //JPanel and initialisation
        this.timer = timer;
        this.setBorder(new LineBorder(Color.BLACK));
        timer.register(this);
        this.setLayout(new BorderLayout());

        //Remaining Time
        remainingTime = new JLabel("Initializing");
        remainingTime.setFont(remainingTime.getFont().deriveFont(22f));
        Box b = Box.createHorizontalBox();
        b.add(remainingTime);
        b.add(Box.createHorizontalStrut(50));
        this.add(b, BorderLayout.EAST);

        //Toggle Pause/Start Button
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

        //Timer Name
        JLabel name = new JLabel(timer.getName());
        name.setFont(name.getFont().deriveFont(15f));
        Box b3 = Box.createHorizontalBox();
        b3.setBorder(new EmptyBorder(20,20,10,20));
        b3.add(name);

        //Alignment
        Box left = Box.createVerticalBox();
        left.add(b3);
        left.add(b2);
        this.add(left, BorderLayout.WEST);

        //Separate looping and non-looping Timers by Color
        if(timer.isLooping()){
            this.setBackground(LOOPING_COLOR_BACKGROUND);
        }
        normal = this.getBackground();
    }

    /**
     * Formats an Amount of Milliseconds to mm:ss:zz where z is 1/100 of a second
     * @param millis the amount of Milliseconds
     * @return a String in the given format
     */
    @SuppressWarnings("SpellCheckingInspection")
    public static String format(long millis){
        int min = (int) (millis / (60 * 1000));
        int sec = (int) ((millis / 1000) % 60);
        int millisec = (int) (millis % 1000) / 10;
        return  String.format("%02d:%02d.%02d", min, sec, millisec);
    }

    /**
     * Keeps the UI up to date
     */
    public void refresh(){
        //Refresh remaining Time
        remainingTime.setText(format(timer.getRemaining()));
        //Make sure the Start/Pause Button is in the correct State
        if(!timer.hasEnded()){
            if(timer.isPaused()){
                toggle.setText("Start");
            } else {
                toggle.setText("Pause");
            }
        } else {
            //you cannot pause a finished Timer
            toggle.setEnabled(timer.isPaused());
        }
        //All this code for a bit of flash
        if (endBlink > System.currentTimeMillis()){
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
        //Swing magic
        this.revalidate();
        this.repaint();
    }

    @Override
    public void alert() {
        endBlink = System.currentTimeMillis() + BLINK_DUR;
        if(timer.isLooping()){
            red = true;
        }
    }
}
