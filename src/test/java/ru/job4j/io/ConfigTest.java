package ru.job4j.io;

//import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.lang.IllegalArgumentException;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ConfigTest {

    @Test
    public void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Ivan Turutin"));
        assertThat(config.value("surname"), is(nullValue()));
    }

    @Test
    public void whenPairWithComment() {
        String path = "./data/pair_with_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("#name"), is(nullValue()));
    }

    @Test
    public void whenPairWithEmptyLine() {
        String path = "./data/pair_with_empty_line.properties";
        Config config = new Config(path);
        config.load();
        Map<String, String> map = config.getValues();
        assertThat(config.getValues().size(), is(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenKeyIsAbsent() {
        String path = "./data/key_is_absent.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenValueIsAbsent() {
        String path = "./data/value_is_absent.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenKeyAndValueIsAbsent() {
        String path = "./data/key_and_value_is_absent.properties";
        Config config = new Config(path);
        config.load();
    }
}
