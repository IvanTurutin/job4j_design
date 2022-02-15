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
        int hash = hash(key);
        int index = indexFor(hash);
        if (table[index] == null) {
            expand();
            table[index] = new MapEntry<K, V>(key, value);
            table[index].hash = hash;
            modCount++;
            count++;
            rsl = true;
        } else if (contains(key) >= 0) {
            table[index].value = value;
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
                    newTable[indexFor(entry.hash)] = entry;
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
                && table[index].hash == hashKey
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
        int hash;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}