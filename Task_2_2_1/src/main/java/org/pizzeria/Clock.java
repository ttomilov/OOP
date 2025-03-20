package org.pizzeria;

import java.io.FileNotFoundException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

class Clock extends Thread{
    private final int workTime;
    private boolean isWorking = false;
    private boolean finished = false;
    private final Pizzeria pizzeria;
    private Object lock = null;
    private static final Logger logger = LogManager.getLogger(Clock.class);

    Clock(int workTime, Pizzeria pizzeria) throws FileNotFoundException {
        this.workTime = workTime;
        this.pizzeria = pizzeria;
    }

    void startClock(){
        if (isWorking){
            return;
        }
        isWorking = true;
        if (lock == null){
            lock = new Object();
            start();
            return;
        }
        synchronized (lock){
            lock.notify();
        }
    }

    synchronized void endClock(){
        finished = true;
        LoggerConsole.write("Clock ended");
        logger.info("Clock ended");
        notify();
    }

    @Override
    public void run() {
        isWorking = true;
        while (!finished){
            if (!isWorking){
                synchronized (lock){
                    try {
                        LoggerConsole.write("Waiting the new day...");
                        logger.info("Waiting the new day...");
                        lock.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            try {
                sleep(workTime);
            } catch (InterruptedException ignored) {
            }
            isWorking = false;
            try {
                pizzeria.endWorkDay();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        synchronized (lock){
            lock.notify();
        }
    }
}
