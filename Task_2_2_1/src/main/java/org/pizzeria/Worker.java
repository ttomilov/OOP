package org.pizzeria;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

abstract class Worker extends Thread {
    private int workerId;
    private int speed;
    private volatile boolean isFinished = false;
    private static Logger logger = LogManager.getLogger(Worker.class);

    @Override
    public void run() {
        while (!isFinished) {
            try {
                waitNewDay();
                work();
            } catch (InterruptedException ignored) {
            }
        }
    }

    abstract void work() throws InterruptedException;

    synchronized void waitNewDay() throws InterruptedException {
        if (isFinished) {
            return;
        }
        if (Thread.currentThread().isInterrupted()) {
            LoggerConsole.write("Worker " + workerId + " waiting new day...");
            logger.info("Worker {} waiting new day...", workerId);
            wait();
        }
    }

    abstract void finishWork();

    abstract void log(String msg);

    void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

    boolean getIsFinished() {
        return isFinished;
    }

    int getSpeed() {
        return speed;
    }

    int getWorkerId() {
        return workerId;
    }

    void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }
}
