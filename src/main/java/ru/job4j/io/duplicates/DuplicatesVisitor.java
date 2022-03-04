package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {

    private Map<FileProperty, List<Path>> files = new HashMap<>();
    private Set<FileProperty> doubles = new HashSet<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fileProp = new FileProperty(file.toFile().length(), file.toFile().getName());
        List<Path> path = new LinkedList<>();
        path.add(file.toAbsolutePath().normalize());
        List<Path> value = files.put(fileProp, path);

        if (value != null) {
            files.get(fileProp).addAll(value);
            doubles.add(fileProp);
        }

        return super.visitFile(file, attrs);
    }

    public Set<FileProperty> getDoubles() {
        return doubles;
    }

    public Map<FileProperty, List<Path>> getFiles() {
        return files;
    }
}