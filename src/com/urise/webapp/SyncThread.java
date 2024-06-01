package com.urise.webapp;

import static java.lang.Thread.sleep;

class SyncThread implements Runnable {
    private final Object lock1;
    private final Object lock2;

    public SyncThread(Object o1, Object o2) {
        this.lock1 = o1;
        this.lock2 = o2;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        synchronized (lock1) {
            System.out.println(name + " has blocked lock1");
            work();
            synchronized (lock2) {
                System.out.println(name + " has blocked lock2");
                work();
            }
            System.out.println(name + " has finished");
        }
    }

    private void work() {
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
