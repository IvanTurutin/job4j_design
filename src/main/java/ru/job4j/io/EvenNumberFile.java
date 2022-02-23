package ru.job4j.io;

import java.io.FileInputStream;

public class EvenNumberFile {
    public static void main(String[] args) {
        StringBuilder str = new StringBuilder();

        try (FileInputStream in = new FileInputStream("even.txt")) {
            int read;
            while ((read = in.read()) != -1) {
                str.append((char) read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] strings = str.toString().split(System.lineSeparator());

        int num;

        for (String s : strings) {
            num = Integer.parseInt(s);
            if (num % 2 == 0) {
                System.out.println(num);
            }
        }
    }
}