package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        Path start = validate(args);
        String extension = args[1];
        search(start, p -> p.toFile().getName().endsWith(extension)).forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private static Path validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Arguments entered incompletely. Usage java -jar Search.jar ROOT_FOLDER FILE_EXTENSION."
            );
        }
        Path start;
        try {
            start = Paths.get(args[0]);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("First argument must be Path format. For example c:/projects");
        }
        if (args[1].charAt(0) != '.') {
            throw new IllegalArgumentException(
                    "Entered arguments are incorrect. FILE_EXTENSION should start with \".\""
            );
        }
        if (!start.toFile().exists()) {
            throw new IllegalArgumentException("Entered arguments are incorrect. ROOT_FOLDER not found.");
        }
        if (start.toFile().isFile()) {
            throw new IllegalArgumentException("Entered arguments are incorrect. ROOT_FOLDER is a file");
        }
        return start;
    }
}