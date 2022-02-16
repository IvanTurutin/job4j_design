package ru.job4j.map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = (MapEntry<K, V>[]) new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean rsl = false;
        expand();
        int hash = hash(key);
        int index = indexFor(hash);
        if (table[index] == null) {
            table[index] = new MapEntry<K, V>(key, value);
            modCount++;
            count++;
            rsl = true;
        }
        return rsl;
    }

    private int hash(K key) {
        return (key == null) ? 0 : key.hashCode() ^ (key.hashCode() >>> 16);
    }

    private int indexFor(int hash) {
        return hash % capacity;
    }

    private void expand() {
        if ((float) count / capacity >= LOAD_FACTOR) {
            capacity = capacity << 1;
            MapEntry<K, V>[] newTable = (MapEntry<K, V>[]) new MapEntry[capacity];
            for (MapEntry<K, V> entry : table) {
                if (entry != null) {
                    newTable[indexFor(hash(entry.key))] = entry;
                }
            }
            table = newTable;
        }
    }

    private int contains(K key) {
        int rsl = -1;
        int hashKey = hash(key);
        int index = indexFor(hashKey);
        if (table[index] != null
                && hash(table[index].key) == hashKey
                && Objects.equals(table[index].key, key)) {
            rsl = index;
        }
        return rsl;
    }

    @Override
    public V get(K key) {
        V value = null;
        int index = contains(key);
        if (index >= 0) {
            value = table[index].value;
        }
        return value;
    }

    @Override
    public boolean remove(K key) {
        boolean rsl = false;
        int index = contains(key);
        if (index >= 0) {
            table[index] = null;
            modCount++;
            count--;
            rsl = true;
        }
        return rsl;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int index = 0;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < capacity && table[index] == null) {
                    index++;
                }
                return index < capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}