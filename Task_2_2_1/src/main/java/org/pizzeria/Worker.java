package org.pizzeria;

abstract class Worker extends Thread {
    private int workerId;
    private int speed;
    private volatile boolean finished = false;

    synchronized void finish() {
        finished = true;
        notify();
    }

    void setWorkerId(int workerId){
        this.workerId = workerId;
    }

    void setSpeed(int speed){
        this.speed = speed;
    }

    int getWorkerId(){
        return workerId;
    }

    int getSpeed(){
        return speed;
    }

    abstract void endWork();

    abstract void takeOrder();

    abstract void work();

    void waitNewDay(){
        synchronized (this){
            if (finished){
                return;
            }
        }
        if (interrupted()) {
            try {
                Logger.write(workerId + ": Waiting for new day");
                wait();
            } catch (InterruptedException ignored) {
            }
        }
    }

    @Override
    public void run() {
        while(true) {
            synchronized (this) {
                if (finished) {
                    Logger.write(workerId + ": Finished");
                    return;
                }
            }
            waitNewDay();
            takeOrder();
            work();
        }
    }
}
