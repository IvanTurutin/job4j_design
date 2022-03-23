package ru.job4j.io.findfiles;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Pattern;

public class FindVisitor extends SimpleFileVisitor<Path> {

    private List<Path> files = new LinkedList<>();
    private Pattern pattern;

    public FindVisitor(Pattern pattern) {

        this.pattern = pattern;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (pattern.matcher(file.toFile().getName()).find()) {
            files.add(file);
        }
        return super.visitFile(file, attrs);
    }

    public List<Path> getFiles() {
        return files;
    }
}
