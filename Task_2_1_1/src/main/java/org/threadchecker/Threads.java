package org.threadchecker;

public class Threads extends Thread {
    private int[] array;
    private boolean res = false;
    private boolean done = false;


    Threads(int[] array) {
        this.array = array;
    }

    public boolean isDone() {
        return done;
    }

    public boolean getResult() {
        return res;
    }

    @Override
    public void run() {
        Object lock = ThreadChecker.getLock();
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 2) {
                done = true;
                res = true;
                synchronized (lock) {
                    lock.notify();
                }
                return;
            }
            for (int j = 2; j * j < array[i]; j++) {
                if (array[i] % j == 0) {
                    break;
                }
            }
        }
        done = true;
        synchronized (lock) {
            lock.notify();
        }
    }
}