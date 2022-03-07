package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        String value = values.get(key);
        if (value == null) {
            throw new IllegalArgumentException("There is no such parameter.");
        }
        return value;
    }

    private void parse(String[] args) {
        for (String string : args) {
            String[] splitted = string.split("=", 2);
            if (splitted.length < 2 || splitted[0].isEmpty()
                    || splitted[0].charAt(0) != '-' || splitted[1].isEmpty()) {
                throw new IllegalArgumentException("Incorrect arguments entered.");
            }
            StringBuilder sb = new StringBuilder(splitted[0]);
            sb.deleteCharAt(0);
            values.put(sb.toString(), splitted[1]);
        }
    }

    public static ArgsName of(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments are entered.");
        }
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}