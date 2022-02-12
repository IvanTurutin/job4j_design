package ru.job4j.collection.set;

import ru.job4j.collection.SimpleArrayList;
import ru.job4j.collection.set.Set;
import java.util.Iterator;
import java.util.Objects;

public class SimpleSet<T> implements Set<T> {
    private int capacity;

    public SimpleSet(int capacity) {
        this.capacity = capacity;
    }

    public SimpleSet() {
        this.capacity = 16;
    }

    private SimpleArrayList<T> set = new SimpleArrayList<>(capacity);

    @Override
    public boolean add(T value) {
        boolean rsl = contains(value);
        if (!rsl) {
            set.add(value);
        }
        return !rsl;
    }

    @Override
    public boolean contains(T value) {
        boolean rsl = false;
        for (T el : set) {
            if (Objects.equals(el, value)) {
                rsl = true;
                break;
            }
        }
        return rsl;
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }
}