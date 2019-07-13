package de.c1bergh0st.timerc.gui;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.c1bergh0st.timerc.Timer;
import de.c1bergh0st.timerc.TimerController;
import de.c1bergh0st.timerc.TimerSaveLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class MainFrame extends JFrame {
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final TimerController timerController;
    private final JPanel contentPanel;
    private final List<TimerPanel> panels;


    public MainFrame(TimerController timerController) {
        super("Timer");
        this.timerController = timerController;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Edit");
        bar.add(menu);
        JMenuItem pause = new JMenuItem("Pause");
        pause.addActionListener(actionEvent -> timerController.pauseAll());
        menu.add(pause);
        JMenuItem start = new JMenuItem("Start");
        start.addActionListener(actionEvent -> timerController.startAll());
        menu.add(start);

        JMenuItem create = new JMenuItem("Create");
        create.addActionListener(actionEvent -> new TimerCreator(timerController));
        menu.add(create);

        JMenu saveLoad = new JMenu("Save/Load");
        bar.add(saveLoad);
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(actionEvent -> new TimerSaveChooser(timerController));
        saveLoad.add(save);

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(actionEvent -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.setAcceptAllFileFilterUsed(false);
            jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Timer files", "timer"));
            if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = jFileChooser.getSelectedFile();
                try {
                    List<Timer> loadedTimers = TimerSaveLoader.readFromFile(f);
                    for(Timer timer: loadedTimers){
                        timerController.add(timer);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e){
                    JOptionPane.showMessageDialog(this, "ERROR", "This File seems to be corrupt", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        saveLoad.add(load);


        this.setLayout(new BorderLayout());
        super.add(bar, BorderLayout.NORTH);
        this.contentPanel = new JPanel();
        contentPanel.setBackground(new Color(128, 142, 255));
        panels = new ArrayList<>();
        super.add(contentPanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(800, 500));
        this.setVisible(true);
    }

    /**
     * Makes sure the Timers are displayed correctly
     */
    public void refresh() {
        Toolkit.getDefaultToolkit().sync();
        for (TimerPanel panel : panels) {
            panel.refresh();
        }
        this.revalidate();
        this.repaint();
    }

    public void add(JComponent c) {
        if (c instanceof TimerPanel) {
            panels.add((TimerPanel) c);
        }
        contentPanel.add(c);
        contentPanel.revalidate();
        this.revalidate();
        this.repaint();
    }

    @SuppressWarnings("unused")
    public void remove(JComponent c) {
        if (c instanceof TimerPanel) {
            panels.remove(c);
        }
        contentPanel.remove(c);
        contentPanel.revalidate();
        this.revalidate();
        this.repaint();
    }

}
