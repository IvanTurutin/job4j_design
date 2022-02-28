package ru.job4j.io;

import java.io.*;
import java.util.*;

public class Analizy {
    private List<Data<String>> srsList = new LinkedList<>();
    private List<Data<String>> trgList = new LinkedList<>();

    public void unavailable(String source, String target) {
        read(source);
        String startTime = null;
        for (Data<String> entry : srsList) {
            if (startTime == null
                    && (Objects.equals(entry.one, "400")
                    || Objects.equals(entry.one, "500"))) {
                startTime = entry.two;
            } else if (startTime != null
                    && !Objects.equals(entry.one, "400")
                    && !Objects.equals(entry.one, "500")) {
                trgList.add(new Data<>(startTime, entry.two));
                startTime = null;
            }
        }
        if (!trgList.isEmpty()) {
            write(target);
        }
    }

    private void write(String target) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(target)
                ))) {
            for (Data<String> data : trgList) {
                out.println(data.one + ";" + data.two + ";");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read(String source) {
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            String[] strings;
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                if (!line.isBlank()) {
                    strings = line.split(" ", 2);
                    if (strings.length == 2 && !strings[0].isBlank() && !strings[1].isBlank()) {
                        srsList.add(new Data<>(strings[0], strings[1]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Data<T> {
        T one;
        T two;

        public Data(T code, T time) {
            this.one = code;
            this.two = time;
        }
    }
}