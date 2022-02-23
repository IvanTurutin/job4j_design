package ru.job4j.io;

import java.io.FileOutputStream;

public class Multiple {
    public static void main(String[] args) {
        try (FileOutputStream out = new FileOutputStream("multiple.txt")) {
            String str;
            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 10; j++) {
                    str = i + " x " + j + " = " + i * j + "  ";
                    out.write(str.getBytes());
                }
                out.write(System.lineSeparator().getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
