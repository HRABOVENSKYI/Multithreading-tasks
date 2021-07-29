package com.epam.rd.java.basic.practice5;

import java.util.logging.Level;

import static com.epam.rd.java.basic.practice5.Demo.logger;

public class Part1 {

    static class Thread1 extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                System.out.println(getName());
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                    interrupt();
                }
            }
        }
    }

    static class Thread2 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread1();
        Thread t2 = new Thread(new Thread2());

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

}
