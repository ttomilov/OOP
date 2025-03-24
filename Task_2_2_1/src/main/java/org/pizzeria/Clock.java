package org.pizzeria;

import java.io.FileNotFoundException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

class Clock extends Thread {
    private final int workTime;
    private boolean isWorking = false;
    private boolean finished = false;
    private final Pizzeria pizzeria;
    private static final Logger logger = LogManager.getLogger(Clock.class);
    private final Object lock = new Object();

    Clock(int workTime, Pizzeria pizzeria) throws FileNotFoundException {
        this.workTime = workTime;
        this.pizzeria = pizzeria;
    }

    void startClock() {
        if (isWorking) {
            return;
        }
        isWorking = true;
        synchronized (lock) {
            lock.notify();
            start();
        }
    }

    void endClock() {
        synchronized (lock) {
            finished = true;
            isWorking = false;
            LoggerConsole.write("Clock ended");
            logger.info("Clock ended");
            lock.notify();
        }
    }

    @Override
    public void run() {
        while (!finished) {
            try {
                if (!isWorking) {
                    synchronized (lock) {
                        LoggerConsole.write("Waiting the new day...");
                        logger.info("Waiting the new day...");
                        lock.wait();
                    }
                }
                sleep(workTime);
            } catch (InterruptedException ignored) {
            }

            isWorking = false;
            try {
                pizzeria.endWorkDay();
            } catch (InterruptedException ignored) {
            }
        }
        synchronized (lock) {
            lock.notify();
        }
    }
}
