package de.c1bergh0st.main;

import de.c1bergh0st.timerc.Timer;
import de.c1bergh0st.timerc.TimerController;
import de.c1bergh0st.timerc.gui.MainFrame;
import de.c1bergh0st.timerc.gui.TimerPanel;

public class App {
    private TimerController timerController;
    private MainFrame mainFrame;

    public App(){
        timerController = new TimerController();
        mainFrame = new MainFrame(timerController);
        timerController.setMainFrame(mainFrame);
        createTimer(new Timer("Test - 1", "Message 1",10 * 1 * 1000));
        createTimer(new Timer("Test - 2", "Message 2",15 * 1 * 1000));
        createTimer(new Timer("Loop Test", "Message loops",30 * 1 * 1000, true, true));
        createTimer(new Timer("Test - 4", "Message 4",80 * 1 * 1000));
        mainFrame.revalidate();
        System.out.println("Setup complete");
    }

    public void createTimer(Timer t){
        timerController.add(t);
        mainFrame.add(new TimerPanel(t));
    }

}
