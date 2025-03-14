package org.pizzeria;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

class Queue<E> {
    private final LinkedList<E> queue = new LinkedList<>();
    private final Semaphore semaphorePut;
    private final Semaphore semaphoreGet;

    Queue(int capacity) {
        semaphoreGet = new Semaphore(0, true);
        semaphorePut = new Semaphore(capacity, true);
    }

    void add(E object) {
        try {
            semaphorePut.acquire();
            synchronized (queue) {
                queue.add(object);
            }
            semaphoreGet.release();
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    E poll() {
        E object = null;
        try {
            semaphoreGet.acquire();
            synchronized (queue) {
                object = queue.poll();
            }
            semaphorePut.release();
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        return object;
    }
}