package de.c1bergh0st.main;

import de.c1bergh0st.timerc.Timer;
import de.c1bergh0st.timerc.TimerController;
import de.c1bergh0st.timerc.gui.MainFrame;

/**
 * This Class represents the App as a whole
 */
@SuppressWarnings("WeakerAccess")
public class App {
    private final TimerController timerController;
    private final MainFrame mainFrame;
    private long last;

    /**
     * Creates a new Timer App complete with a new JFrame
     */
    public App(){
        timerController = new TimerController();
        mainFrame = new MainFrame(timerController);
        timerController.setMainFrame(mainFrame);
        mainFrame.revalidate();
        /*timerController.add(new Timer("Name wowo", 40000));
        timerController.add(new Timer("Second", 11000));
        timerController.add(new Timer("Name 2", 32000));
        timerController.add(new Timer("Fourth", 13000));
        timerController.add(new Timer("Name wowo", 24000));
        timerController.add(new Timer("SIXS", 15000));
        timerController.add(new Timer("Mista is an idiot", 16000));*/
        javax.swing.Timer t = new javax.swing.Timer(1000 / 30, actionEvent -> {
            last = System.currentTimeMillis();
            timerController.update();
            mainFrame.refresh();
            int tickTime = (int)(System.currentTimeMillis() - last);
            if(tickTime > 20){
                System.out.println("Last tick took " + tickTime + "ms");
                System.out.println("The System will lag with Ticks longer than 33ms");
            }
        });
        t.start();
        System.out.println("Setup complete");
    }


}
