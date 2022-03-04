package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class DuplicatesFinder {
    public static void main(String[] args) throws IOException {
        DuplicatesVisitor doubles = new DuplicatesVisitor();
        try {
            Files.walkFileTree(Path.of(args[0]), doubles);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter path to search doubles.");
        } catch (InvalidPathException e) {
            System.out.println("First argument must be Path format.");
        }
        doubles.getFiles().entrySet()
                .stream()
                .filter(e -> doubles.getDoubles().contains(e.getKey()))
                .forEach(System.out::println);
    }
}