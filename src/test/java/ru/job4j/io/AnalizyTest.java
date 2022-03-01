package ru.job4j.io;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnalizyTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void whenOneShutdown() throws IOException {
        File source = folder.newFile("one_shutdown.csv");
        File target = folder.newFile("one_shutdown_trg.csv");
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(source)
                ))) {
            out.println("200 10:56:01");
            out.println("500 10:57:01");
            out.println("400 10:58:01");
            out.println("500 10:59:01");
            out.println("400 11:01:02");
            out.println("200 11:02:02");
        }
        Analizy analizy = new Analizy();
        analizy.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        ArrayList<String> rsl = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
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
    public void whenTwoShutdown() throws IOException {
        File source = folder.newFile("two_shutdown.csv");
        File target = folder.newFile("two_shutdown_trg.csv");
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(source)
                ))) {
            out.println("200 10:56:01");
            out.println("500 10:57:01");
            out.println("400 10:58:01");
            out.println("200 10:59:01");
            out.println("500 11:01:02");
            out.println("200 11:02:02");
        }
        Analizy analizy = new Analizy();
        analizy.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        ArrayList<String> rsl = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
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