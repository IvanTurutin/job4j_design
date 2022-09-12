package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String[] strings;
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                if (!line.isBlank() && line.charAt(0) != '#') {
                    strings = line.split("=", 2);
                    if (strings.length < 2 || strings[0].isBlank() || strings[1].isBlank()) {
                        throw new IllegalArgumentException();
                    }
                    values.put(strings[0], strings[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getValues() {
        return values;
    }

    public Map<String, String> loadAndGetValues() {
        load();
        return getValues();
    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        Config config = new Config("app.properties");
        config.load();
        Map<String, String> map = config.getValues();
        map.entrySet().stream().forEach(System.out::println);
    }
}