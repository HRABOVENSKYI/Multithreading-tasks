package com.epam.rd.java.basic.practice5;

import java.util.Scanner;

public class Spam {

    private final Thread[] threads;

    public Spam(final String[] messages, final int[] delays) {
        int count = messages.length;
        threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Worker(messages[i], delays[i]);
        }
    }

    public static void main(final String[] args) {
        Spam sp = new Spam(new String[] {"@@@", "bbb"}, new int[] {333, 222});
        sp.start();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while(true) {
            if(input.isEmpty()) {
                sp.stop();
                break;
            }
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
            }
        }
    }

    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }

    }

    public void stop() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private static class Worker extends Thread {
        private final String message;
        private final int delay;

        public Worker(String message, int delay) {
            this.message = message;
            this.delay = delay;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(message);
                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

}
