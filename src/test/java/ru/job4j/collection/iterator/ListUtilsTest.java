package ru.job4j.collection.iterator;

import org.hamcrest.core.Is;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

public class ListUtilsTest {

    @Test
    public void whenAddBefore() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 1, 2);

        assertThat(input, is(Arrays.asList(1, 2, 3)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddBeforeWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 3, 2);
    }

    @Test
    public void whenAddAfterLast() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2));
        ListUtils.addAfter(input, 2, 3);
        assertThat(input, is(Arrays.asList(0, 1, 2, 3)));
    }

    @Test
    public void whenRemoveIf() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        ListUtils.removeIf(input, x -> x > 2);
        assertThat(input, is(Arrays.asList(0, 1, 2)));
    }

    @Test
    public void whenRemoveIfWithNullValue() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, null, 5));
        ListUtils.removeIf(input, x -> x != null ? x > 2 : true);
        assertThat(input, is(Arrays.asList(0, 1, 2)));
    }

    @Test
    public void whenRemoveIfThenRemoveAll() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        ListUtils.removeIf(input, x -> x > -1);
        assertThat(input, is(Arrays.asList()));
    }

    @Test
    public void whenReplaceIfThenReplaceNull() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, null, 5, null));
        ListUtils.replaceIf(input, x -> x == null, 0);
        assertThat(input, is(Arrays.asList(0, 1, 2, 3, 0, 5, 0)));
    }

    @Test
    public void whenReplaceIfThenReplaceNone() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        ListUtils.replaceIf(input, x -> x == 6, 0);
        assertThat(input, is(Arrays.asList(0, 1, 2, 3, 4, 5)));
    }

    @Test
    public void whenRemoveAll() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, null, 5, null));
        List<Integer> remove = new ArrayList<>(Arrays.asList(0, null));
        ListUtils.removeAll(input, remove);
        assertThat(input, is(Arrays.asList(1, 2, 3, 5)));
    }

    @Test
    public void whenRemoveAllThenRemoveNone() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, null, 5, null));
        List<Integer> remove = new ArrayList<>(Arrays.asList(6, 8));
        ListUtils.removeAll(input, remove);
        assertThat(input, is(Arrays.asList(0, 1, 2, 3, null, 5, null)));
    }

}