package de.c1bergh0st.timerc;

/**
 * This interface is used by Timers to propagate their end to gui or other elements
 */
public interface Observer {

    void alert();

    void terminate();

}
