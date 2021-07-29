package com.epam.rd.java.basic.practice5;

import java.io.*;
import java.util.logging.Level;

import static com.epam.rd.java.basic.practice5.Demo.logger;

public class Part4 {

    private static final String FILENAME = "part4.txt";

    private static final int NUM_OF_LINES;

    private static final Thread[] threads;

    static {
        NUM_OF_LINES = linesCount(FILENAME);
        threads = new Thread[NUM_OF_LINES];
    }

    private static void maxWithMultithreading() {

        final long startTime = System.currentTimeMillis();

        int[] nums = new int[NUM_OF_LINES];

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                final String fLine = line;
                final int pos = i;
                threads[i] = new Thread(() -> lineMax(nums, pos, fLine));
                threads[i].start();
                i++;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            } catch (NullPointerException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        int max = max(nums);

        final long endTime = System.currentTimeMillis();

        System.out.println(max);
        System.out.println(endTime - startTime);

    }

    private static void maxWithoutMultithreading() {

        final long startTime = System.currentTimeMillis();

        int[] nums = new int[NUM_OF_LINES];

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                lineMax(nums, i, line);
                i++;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        int max = max(nums);

        final long endTime = System.currentTimeMillis();

        System.out.println(max);
        System.out.println(endTime - startTime);
    }

    public static int linesCount(String filename) {
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] c = new byte[1024];
            int count = 1;
            int readChars;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0) ? 1 : count;
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    private static void lineMax(int[] nums, int pos, String localLine) {
        int localMax = 0;
        for (String number : localLine.split(" ")) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
            int num = Integer.parseInt(number);
            if (num > localMax) {
                localMax = num;
            }
        }
        nums[pos] = localMax;
    }

    private static int max(int[] arr) {
        int i;
        int max = arr[0];
        for (i = 1; i < Part4.NUM_OF_LINES; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        return max;
    }

    public static void main(final String[] args) {
        maxWithMultithreading();
        maxWithoutMultithreading();
    }
}
