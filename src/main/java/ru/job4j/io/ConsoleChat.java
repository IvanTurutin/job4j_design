package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;
    private List<String> phrases;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        readPhrases();
        List<String> log = new LinkedList<>();
        String userString = CONTINUE;
        System.out.println("Welcome to console chat");
        while (!OUT.equals(userString)) {
            userString = terminalRead();
            log.add(userString);
            if (STOP.equals(userString)) {
                while (!CONTINUE.equals(userString)) {
                    userString = terminalRead();
                    log.add(userString);
                }
            }
            if (OUT.equals(userString)) {
                continue;
            }
            log.add(botAnswer());
        }
        System.out.println("Good buy!");
        saveLog(log);
    }

    private String botAnswer() {
        int rnd = (int) (Math.random() * phrases.size());
        System.out.println(phrases.get(rnd));
        return phrases.get(rnd);
    }

    private String terminalRead() {
        String string = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line = in.readLine();
            if (line.length() != 0) {
                string = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    private void readPhrases() {
        List<String> list = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(botAnswers))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                if (!line.isBlank()) {
                    list.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        phrases = list;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, Charset.forName("WINDOWS-1251"), true))) {
            log.forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("logChat.txt", "Answers.txt");
        cc.run();

    }
}