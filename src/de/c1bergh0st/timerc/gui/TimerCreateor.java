package de.c1bergh0st.timerc.gui;

import de.c1bergh0st.timerc.TimerController;
import de.c1bergh0st.timerc.Timer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;

public class TimerCreateor {
    private TimerController controller;
    private JFrame frame;
    private JTextField name;
    private JCheckBox looping;
    private JTextField minutes;
    private JTextField seconds;
    private JTextField miliseconds;
    private JCheckBox startEnabled;
    private JButton submit;

    private static Box wrap(String name, Component c){
        Box b = Box.createHorizontalBox();
        b.add(new JLabel(name));
        b.add(Box.createHorizontalStrut(30));
        b.add(c);
        b.setBorder(new EmptyBorder(10,10,10,10));
        return b;
    }

    public void generateTimer(TimerController timerController){
        submit = new JButton("Create");
        controller = timerController;
        frame = new JFrame("Timer Erstellen");

        frame.setPreferredSize(new Dimension(400,300));
        frame.setSize(new Dimension(400,300));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setAutoRequestFocus(true);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        name = new JTextField("Timer");
        Color normal = name.getBackground();
        name.addCaretListener(caretEvent -> {
            if(name.getText().length() >= 1){
                name.setBackground(normal);
            } else {
                name.setBackground(new Color(255,170,170));
            }
            validate();
        });
        frame.add(wrap("Name", name));

        minutes = new JTextField("1");
        minutes.addCaretListener(caretEvent -> {
            try{
                long millis = (long)(Double.parseDouble(minutes.getText()) * 60 * 1000);
                minutes.setBackground(normal);
            } catch (NumberFormatException e){
                minutes.setBackground(new Color(255,170,170));
            }
            validate();
        });
        frame.add(wrap("Minutes", minutes));

        seconds = new JTextField("0");
        seconds.addCaretListener(caretEvent -> {
            try{
                long millis = (long)(Double.parseDouble(seconds.getText())  * 1000);
                seconds.setBackground(normal);
            } catch (NumberFormatException e){
                seconds.setBackground(new Color(255,170,170));
            }
            validate();
        });
        frame.add(wrap("Seconds", seconds));

        miliseconds = new JTextField("0");
        miliseconds.addCaretListener(caretEvent -> {
            try{
                long millis = (long)(Double.parseDouble(miliseconds.getText()));
                miliseconds.setBackground(normal);
            } catch (NumberFormatException e){
                miliseconds.setBackground(new Color(255,170,170));
            }
            validate();
        });
        frame.add(wrap("Miliseconds", miliseconds));

        Box b = Box.createHorizontalBox();
        b.add(new JLabel("Loop:"));
        looping = new JCheckBox();
        b.add(looping);
        b.add(new JLabel("Sofort Starten:"));
        startEnabled = new JCheckBox();
        b.add(startEnabled);
        frame.add(b);

        submit.addActionListener(e -> {
            create();
        });
        frame.add(submit);
        frame.setVisible(true);
        validate();

    }

    private void create(){
        long min = (long)(Double.parseDouble(minutes.getText()) * 60 * 1000);
        long sec = (long)(Double.parseDouble(seconds.getText())  * 1000);
        long mil = (long)(Double.parseDouble(miliseconds.getText()));
        long duration = min + sec + mil;
        boolean loop = looping.isSelected();
        boolean startNow = startEnabled.isSelected();
        String nameStr = name.getText();
        Timer t = new Timer(nameStr, "", duration, loop, true);
        if(!startNow){
            t.pause(System.currentTimeMillis());
        }
        controller.add(t);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.dispose();
        System.out.println("Disposed");
    }

    private void validate(){
        boolean shouldValidate = true;

        try{
            long millis = (long)(Double.parseDouble(minutes.getText()));
            millis = (long)(Double.parseDouble(seconds.getText()));
            millis = (long)(Double.parseDouble(miliseconds.getText()));
        } catch (NumberFormatException e){
            shouldValidate = false;
        }
        if(name.getText().length() < 1){
            shouldValidate = false;
        }
        submit.setEnabled(shouldValidate);
    }
}
