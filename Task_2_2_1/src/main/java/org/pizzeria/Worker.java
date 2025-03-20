package org.pizzeria;

abstract class Worker extends Thread {
    private int workerId;
    private int speed;
    private volatile boolean finished = false;

    @Override
    public void run() {
        while(!finished) {
            waitNewDay();
            takeOrder();
            work();
        }
        LoggerConsole.write("Worker " + workerId + " finished");
    }

    abstract void print(String msg);

    void setWorkerId(int workerId){
        this.workerId = workerId;
    }

    void setSpeed(int speed){
        this.speed = speed;
    }

    int getWorkerId(){
        return workerId;
    }

    void setFinished(boolean finished){
        this.finished = finished;
    }

    boolean getFinished(){
        return finished;
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
                synchronized (this){
                    LoggerConsole.write("Worker " + workerId + " waiting for new day");
                    wait();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }
}
