package de.c1bergh0st.timerc.gui;

import de.c1bergh0st.timerc.TimerController;
import de.c1bergh0st.timerc.Timer;
import de.c1bergh0st.timerc.TimerSaveLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.BufferPoolMXBean;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.WindowEvent;

@SuppressWarnings({"SpellCheckingInspection", "WeakerAccess"})
public class TimerSaveChooser {
    private TimerController controller;
    private JFrame frame;
    private JList<Timer> jList;
    private JButton buttonSave;
    private JButton buttonCancel;

    /*
            THIS IS GUI CODE, IT IS SUPPOSED TO LOOK LIKE A**
     */


    /**
     * Creates a new Timer from user input
     *
     * @param contr the TimerController to register the new Timer to
     */
    public TimerSaveChooser(TimerController contr) {
        controller = contr;
        frame = new JFrame("Save Timers");

        frame.setPreferredSize(new Dimension(400, 300));
        frame.setSize(new Dimension(400, 300));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setAutoRequestFocus(true);
        frame.setLayout(new BorderLayout());

        List<Timer> list = controller.getAllTimers();
        Timer[] timerArray = new Timer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            timerArray[i] = list.get(i);
        }
        frame.add(new JLabel("Hold CTRL to select more than 1 Timer"), BorderLayout.NORTH);
        //frame.add(Box.createRigidArea(new Dimension(20,20)));
        buttonSave = new JButton("Save");
        buttonSave.setEnabled(false);
        jList = new JList<>(timerArray);
        jList.setBorder(new LineBorder(Color.BLACK, 1));
        jList.addListSelectionListener(event -> {
            if (jList.getSelectedValuesList().size() > 0) {
                buttonSave.setEnabled(true);
            } else {
                buttonSave.setEnabled(false);
            }
        });
        frame.add(jList, BorderLayout.CENTER);

        buttonSave.setPreferredSize(new Dimension(80, 20));
        buttonSave.addActionListener(actionEvent -> {
            List<Timer> selectedList = jList.getSelectedValuesList();
            JFileChooser jFileChooser= new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("."));
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if(jFileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION){
                File f = jFileChooser.getSelectedFile();
                if(!f.getName().endsWith(".timer")){
                    f = new File(f.getPath() + ".timer");
                }
                try {
                    if(!f.createNewFile()){
                        if(JOptionPane.showConfirmDialog(
                                frame,
                                "This File already exists. Overwrite?",
                                "Overwrite File?",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            TimerSaveLoader.saveToFile(selectedList, f);
                        }
                    } else {
                        TimerSaveLoader.saveToFile(selectedList, f);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, "Error while attempting to write to File " + f.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            //destroy JFrame and its components
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            frame.dispose();
        });

        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(actionEvent -> {
            //destroy JFrame and its components
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            frame.dispose();
        });
        Box b = Box.createHorizontalBox();
        b.setBorder(new EmptyBorder(10, 10, 10, 10));
        b.add(buttonSave);
        b.add(Box.createHorizontalStrut(50));
        b.add(buttonCancel);
        frame.add(b, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
