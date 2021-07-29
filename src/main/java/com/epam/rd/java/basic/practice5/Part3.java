package com.epam.rd.java.basic.practice5;

import java.util.logging.Level;

import static com.epam.rd.java.basic.practice5.Demo.logger;

public class Part3 {

    private int counter;

    private int counter2;

    private final Thread[] threads;

    private final int numberOfIterations;

    public Part3(int numberOfThreads, int numberOfIterations) {
        this.threads = new Thread[numberOfThreads];
        this.numberOfIterations = numberOfIterations;
    }

    private void doStuff() {
        System.out.println(Thread.currentThread().getName() + " " + (counter == counter2) + " counter:" + counter + " counter2:" + counter2  + " time:" + System.currentTimeMillis() % 10000);
        counter++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        counter2++;
    }

    public void compare() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numberOfIterations; j++) {
                    doStuff();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    public void compareSync() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numberOfIterations; j++) {
                    synchronized (this){
                        doStuff();
                    }
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(final String[] args) {

        Part3 part3 = new Part3(2, 5);
        part3.compare();
        part3.compareSync();


    }
}
