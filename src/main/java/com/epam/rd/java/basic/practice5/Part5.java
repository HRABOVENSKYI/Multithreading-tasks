package com.epam.rd.java.basic.practice5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

import static com.epam.rd.java.basic.practice5.Demo.logger;

public class Part5 {

    private static final String FILE_NAME = "part5.txt";

    private static final Thread[] threads;

    static {
        threads = new Thread[10];
    }

    public static void main(String[] args) {
        deleteFileIfExists();
        writeNumsToTheNewFile();
        System.out.print(readFromFile());
    }

    private static void deleteFileIfExists() {
        try {
            Files.deleteIfExists(Paths.get(FILE_NAME));
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private static void writeNumsToTheNewFile() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            for (int i = 0; i < 10; i++) {
                final int line = i;
                threads[i] = new Thread(() -> fillLine(file, line));
                threads[i].start();
            }
            for (int i = 0; i < threads.length; i++) {
                threads[i].join();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    private static void fillLine(RandomAccessFile file, int line) {
        for (int j = 0; j < 21; j++) {
            try {
                insertValue(file, line, j);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private static synchronized void insertValue(RandomAccessFile file, int line, int j) throws IOException {
        file.seek((long) line * 21 + j);
        if (j != 20) {
            file.write('0' + line);
        } else {
            file.write('\n');
        }
    }

    private static String readFromFile() {

        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Part5.FILE_NAME))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return resultStringBuilder.toString();
    }
}