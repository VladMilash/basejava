package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File file = new File("./src/com/urise/webapp");
        printDirectory(file, 0);
    }

    public static void printDirectory(File file, int level) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File value : files) {
                String indent = " ".repeat(level * 3);
                if (value.isFile()) {
                    System.out.println(indent + "File: " + value.getName());
                } else if (value.isDirectory()) {
                    System.out.println(indent + "Directory: " + value.getName());
                    printDirectory(value, level + 1);
                }
            }
        }
    }
}
