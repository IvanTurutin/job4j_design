package ru.job4j.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.collection.SimpleArrayList;
import ru.job4j.list.List;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SimpleMapTest {

    @Test
    public void whenPutThenTrue() {
        Map<Integer, String> map = new SimpleMap<>();
        Assert.assertTrue(map.put(1, "one"));
        Assert.assertEquals("one", map.get(1));
    }

    @Test
    public void whenPutEqualHashNotEqualKeyThenFalse() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(1, "one");
        Assert.assertFalse(map.put(9, "nine"));
        Assert.assertEquals("one", map.get(1));
        Assert.assertEquals(null, map.get(9));
    }

    @Test
    public void whenRemoveNullThenTrue() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(null, "null value");
        Assert.assertTrue(map.remove(null));
        Assert.assertEquals(null, map.get(null));
        Assert.assertFalse(map.remove(null));
    }

    @Test
    public void whenPutTenEntryThenExpandSize() {
        Map<Integer, String> map = new SimpleMap<>();
        IntStream.range(1, 10).forEach(v -> map.put(v, Integer.toString(v)));
        Assert.assertEquals("9", map.get(9));
    }

    @Test
    public void whenGetIteratorTwiceThenStartAlwaysFromBeginning() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(1, "one");
        Assert.assertEquals(Integer.valueOf(1), map.iterator().next());
        Assert.assertEquals(Integer.valueOf(1), map.iterator().next());
    }

    @Test
    public void whenCheckIterator() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(null, "null value");
        map.put(3, "three");
        map.put(5, "five");
        Iterator<Integer> iterator = map.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(null, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(3), iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(5), iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenAddAfterGetIteratorThenMustBeException() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(3, "three");
        Iterator<Integer> iterator = map.iterator();
        map.put(5, "five");
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNoSuchElementException() {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(3, "three");
        Iterator<Integer> iterator = map.iterator();
        iterator.next();
        iterator.next();
    }
}