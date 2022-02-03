package ru.job4j.it;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIt implements Iterator<Integer> {
    private final int[][] data;
    private int row = 0;
    private int column = 0;
    private int r = 0;
    private int c = 0;

    public MatrixIt(int[][] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        for (int i = row; i < data.length - 1; i++) {
            if (data[row].length != 0) {
                break;
            }
            row++;
        }
        r = row;
        c = column;
        return column < data[row].length
                || row != (data.length - 1);

    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        } else if (column == data[row].length - 1
                && row < data.length - 1) {
                column = 0;
                row++;
        } else {
            column++;
        }
        return data[r][c];
    }
}