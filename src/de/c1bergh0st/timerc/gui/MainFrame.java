package de.c1bergh0st.timerc.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import de.c1bergh0st.timerc.Timer;
import de.c1bergh0st.timerc.TimerController;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class MainFrame extends JFrame {
    private TimerController timerController;
    private JPanel contentPanel;
    private List<TimerPanel> panels;


    public MainFrame(TimerController timerController){
        super("Timer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        this.contentPanel = new JPanel();
        contentPanel.setBackground(new Color(128, 142, 255));
        panels = new ArrayList<>();
        super.add(contentPanel);
        this.timerController = timerController;
        this.setPreferredSize(new Dimension(800,500));
        this.setVisible(true);
    }

    public void add(JComponent c){
        if( c instanceof TimerPanel){
            panels.add((TimerPanel) c);
        }
        contentPanel.add(c);
        contentPanel.revalidate();
        this.revalidate();
        this.repaint();
    }


    public void removeAll(Collection<JComponent> components){
        for(JComponent component: components){
            remove(component);
        }
    }

    public void forceRefresh(){
        panels.sort(new Comparator<TimerPanel>() {
            @Override
            public int compare(TimerPanel timerPanel, TimerPanel t1) {
                return (int) (timerPanel.getTimer().getEnd() - t1.getTimer().getEnd());
            }
        });
        for(TimerPanel p : panels){
            contentPanel.remove(p);
        }

        for(TimerPanel p : panels){
            contentPanel.add(p);
        }
        this.revalidate();
        this.repaint();
    }

    public void remove(JComponent c){
        if(c instanceof TimerPanel){
            panels.remove(c);
        }
        contentPanel.remove(c);
        contentPanel.revalidate();
        this.revalidate();
        this.repaint();
    }

}
