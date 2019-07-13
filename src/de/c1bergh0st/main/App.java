package de.c1bergh0st.main;

import de.c1bergh0st.timerc.Timer;
import de.c1bergh0st.timerc.TimerController;
import de.c1bergh0st.timerc.gui.MainFrame;
import de.c1bergh0st.timerc.gui.TimerPanel;

public class App {
    private TimerController timerController;
    private MainFrame mainFrame;
    private long last;

    public App(){
        timerController = new TimerController();
        mainFrame = new MainFrame(timerController);
        timerController.setMainFrame(mainFrame);
        timerController.add(new Timer("Test - -2", "Message 1",10 * 1 * 1000));
        timerController.add(new Timer("Test - -1", "Message 2",15 * 1 * 1000));
        timerController.add(new Timer("Loop Test", "Message loops",30 * 1 * 1000, true, true));
        timerController.add(new Timer("Test - 0", "Message 4",80 * 1 * 1000));
        for(int i = 1; i <= 50; i++){
            timerController.add(new Timer("Test - " + i, "Message " + i,i * 2 * 1 * 1000, true, false));
        }
        mainFrame.revalidate();
        javax.swing.Timer t = new javax.swing.Timer(1000 / 30, actionEvent -> {
            last = System.currentTimeMillis();
            timerController.update();
            mainFrame.refresh();
            System.out.println("Last tick took " + (System.currentTimeMillis() - last));
        });
        t.start();
        System.out.println("Setup complete");
    }


}
