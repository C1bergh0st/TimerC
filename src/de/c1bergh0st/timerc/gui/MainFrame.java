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
        this.timerController = timerController;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Edit");
        bar.add(menu);
        JMenuItem pause = new JMenuItem("Pause");
        pause.addActionListener(actionEvent -> {
            timerController.pauseAll();
        });
        menu.add(pause);
        JMenuItem start = new JMenuItem("Start");
        start.addActionListener(actionEvent -> {
            timerController.startAll();
        });
        menu.add(start);

        JMenuItem create = new JMenuItem("Create");
        create.addActionListener(actionEvent -> {
            new TimerCreateor().generateTimer(timerController);
        });
        menu.add(create);

        this.setLayout(new BorderLayout());
        super.add(bar, BorderLayout.NORTH);
        this.contentPanel = new JPanel();
        contentPanel.setBackground(new Color(128, 142, 255));
        panels = new ArrayList<>();
        super.add(contentPanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(800,500));
        this.setVisible(true);
    }

    public void refresh(){
        Toolkit.getDefaultToolkit().sync();
        for(TimerPanel panel : panels){
            panel.refresh();
        }
        this.revalidate();
        this.repaint();
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
