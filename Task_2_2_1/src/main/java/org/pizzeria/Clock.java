package org.pizzeria;

public class Clock extends Thread{
    private final int workTime;
    private boolean isWorking = false;
    private boolean finished = false;
    private final Pizzeria pizzeria;
    private Object lock = null;

    public Clock(int workTime, Pizzeria pizzeria){
        this.workTime = workTime;
        this.pizzeria = pizzeria;
    }

    public void startClock(){
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

    public synchronized void endClock(){
        finished = true;
    }

    @Override
    public void run() {
        isWorking = true;
        while (!finished){
            if (!isWorking){
                synchronized (lock){
                    try {
                        Logger.write("Waiting the new day...");
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
            pizzeria.endWorkDay();
        }
    }

    public boolean isWorking(){
        return isWorking;
    }

    public boolean isFinished() {
        return finished;
    }
}
