package de.c1bergh0st.timerc;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TimerSaveLoader {
    public static final char ARGUMENT_SEPARATOR = ',';
    public static final char ARGSEP = ARGUMENT_SEPARATOR;
    public static final char OBJECT_SEPARATOR = '#';

    public static void saveToFile(Collection<Timer> timers, File f) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        for(Timer timer : timers){
            sb.append(convertToString(timer));
            sb.append(OBJECT_SEPARATOR);
        }
        sb.deleteCharAt(sb.lastIndexOf(Character.toString(OBJECT_SEPARATOR)));
        PrintWriter pw = new PrintWriter(f);
        pw.print(sb.toString());
        pw.flush();
        pw.close();
    }

    public static List<Timer> readFromFile(File f) throws FileNotFoundException {
        Scanner scanner = new Scanner(f);
        String data = scanner.nextLine();
        scanner.close();
        String[] dataobjects = data.split(Character.toString(OBJECT_SEPARATOR));
        List<Timer> list = new LinkedList<>();
        for(String timerString : dataobjects){
            try{
                list.add(convertFromString(timerString));
            } catch (IllegalArgumentException e){
                System.err.println("Error while parsing");
                throw e;
            }
        }
        return list;
    }

    public static String convertToString(Timer timer){
        String name = timer.getName().replaceAll(OBJECT_SEPARATOR + "|" + ARGUMENT_SEPARATOR, "");
        String message = timer.getMessage().replaceAll(OBJECT_SEPARATOR + "|" + ARGUMENT_SEPARATOR, "");
        long duration;
        if(!timer.isLooping()){
            duration = timer.getRemaining();
        } else {
            duration = timer.getCycleDuration();
        }
        boolean looping = timer.isLooping();
        boolean soundsOnEnd = timer.shouldSound();
        return name +
                ARGUMENT_SEPARATOR +
                message +
                ARGUMENT_SEPARATOR +
                duration +
                ARGUMENT_SEPARATOR +
                looping +
                ARGUMENT_SEPARATOR +
                soundsOnEnd;
    }

    public static Timer convertFromString(String s) throws IllegalArgumentException{
        if(!s.matches(".*" + ARGSEP + ".*" + ARGSEP + "[0-9]*" + ARGSEP + "(true|false)" + ARGSEP + "(true|false)")){
            throw new IllegalArgumentException("Could not parse :" + s);
        }
        String[] components = s.split(Character.toString(ARGSEP));
        String name = components[0];
        String message = components[1];
        try{
            long duration = Long.parseLong(components[2]);
            boolean looping = Boolean.parseBoolean(components[3]);
            boolean sound = Boolean.parseBoolean(components[4]);
            Timer timer = new Timer(name, message, duration, looping, sound);
            timer.pause(System.currentTimeMillis());
            return timer;
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Could not parse : " + s, e);
        }
    }



}
