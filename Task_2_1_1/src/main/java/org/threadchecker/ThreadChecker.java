    package org.threadchecker;

    import java.util.ArrayList;
    import java.util.List;

    import static java.util.Arrays.copyOfRange;

    public class ThreadChecker {
        private int[] array;
        private static final Object lock = new Object();

        public static Object getLock() {
            return lock;
        }

        public ThreadChecker(int[] array) {
            this.array = array;
        }

        public boolean findNotPrime(int numOfThreads){
            Threads[] threads = new Threads[numOfThreads];
            ArrayList<Integer> helper = new ArrayList<>();
            int end = 0;
            int step = array.length / numOfThreads;
            if (step == 0){
                step = 1;
            }

            for (int i = 0; i < numOfThreads; i++) {
                int start = i * step;
                end += step;
                if (end > array.length){
                    end = array.length;
                }
                int[] newArray = copyOfRange(array, start, end + 1);
                threads[i] = new Threads(newArray);
                helper.add(i);
                threads[i].start();
            }

            while (!helper.isEmpty()){
                synchronized (lock){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) { }
                }
                for (int i = 0; i < numOfThreads; i++) {
                    if (threads[i] != null) {
                        if (threads[i].isDone()) {
                            if (threads[i].getResult()) {
                                for (int j = 0; j < numOfThreads; j++) {
                                    if (threads[j] != null) {
                                        threads[j].interrupt();
                                    }
                                }
                                return true;
                            } else {
                                threads[i] = null;
                                helper.remove(helper.size() - 1);
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
