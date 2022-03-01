package ru.job4j.io;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnalizyTest {

    @Test
    public void whenOneShutdown() {
        String srcPath = "./data/one_shutdown.csv";
        String trgPath = "./data/one_shutdown_trg.csv";
        Analizy analizy = new Analizy();
        analizy.unavailable(srcPath, trgPath);
        ArrayList<String> rsl = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(trgPath))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                rsl.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(rsl.get(0), is("10:57:01;11:02:02;"));
        assertThat(rsl.size(), is(1));
    }

    @Test
    public void whenTwoShutdown() {
        String srcPath = "./data/two_shutdown.csv";
        String trgPath = "./data/two_shutdown_trg.csv";
        Analizy analizy = new Analizy();
        analizy.unavailable(srcPath, trgPath);
        ArrayList<String> rsl = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(trgPath))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                rsl.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(rsl.get(0), is("10:57:01;10:59:01;"));
        assertThat(rsl.get(1), is("11:01:02;11:02:02;"));
        assertThat(rsl.size(), is(2));
    }

    @Test
    public void whenZeroShutdown() {
        String srcPath = "./data/zero_shutdown.csv";
        String trgPath = "./data/zero_shutdown_trg.csv";
        Analizy analizy = new Analizy();
        analizy.unavailable(srcPath, trgPath);
        File file = new File(trgPath);
        assertFalse(file.exists());
    }
}