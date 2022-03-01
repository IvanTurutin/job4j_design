package ru.job4j.io;

import java.io.*;
import java.util.*;

public class Analizy {
    private List<String[]> trgList = new LinkedList<>();

    public void unavailable(String source, String target) {
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            String[] strings;
            String startTime = null;
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                if (!line.isBlank()) {
                    strings = line.split(" ", 2);
                    if (startTime == null
                            && ("400".equals(strings[0])
                            || "500".equals(strings[0]))) {
                        startTime = strings[1];
                    } else if (startTime != null
                            && !"400".equals(strings[0])
                            && !"500".equals(strings[0])) {
                        trgList.add(new String[]{startTime, strings[1]});
                        startTime = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            for (String[] data : trgList) {
                out.println(data[0] + ";" + data[1] + ";");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}