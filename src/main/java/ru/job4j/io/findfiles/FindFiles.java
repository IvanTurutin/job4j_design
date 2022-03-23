package ru.job4j.io.findfiles;

import ru.job4j.io.ArgsName;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Программа осуществляет поиск файлов по маске и выводит результат поиска в текстовый файл
 * @author Ivan Turutin
 * @version 1.0
 */

public class FindFiles {

    /**
     *
     * @param args Программа принимает параметры в виде -d=PATH -n=MASK -t=TYPE -o=LOG
     *             где:
     *             -d - папка, в которой осуществляется поиск;
     *             -n - имя файла, маска, либо регулярное выражение;
     *             -t - тип поиска: mask искать по маске, name по полному совпадение имени,
     *             regex по регулярному выражению;
     *             -o - результат записать в файл.
     */

    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            throw new IllegalArgumentException("Input arguments -d=PATH -n=MASK -t=TYPE -o=OUT");
        }
        FindFiles.run(ArgsName.of(args));
    }

    static void run(ArgsName args) throws IOException {
        Path path = validatePath(args.get("d")).toPath();
        Path log = validateOut(args.get("o")).toPath();
        Pattern pattern = getPattern(args.get("t"), args.get("n"));
        List<Path> files = getFiles(pattern, path);
        writeLog(files, log);
    }

    private static File validatePath(String path) {
        File file;
        try {
            file = new File(path);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("PATH argument must be File format. For example c:/projects");
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("Entered arguments are incorrect. PATH not found.");
        }
        if (file.isFile()) {
            throw new IllegalArgumentException("Entered arguments are incorrect. PATH is a file, instead a directory.");
        }
        return file;
    }

    private static File validateOut(String out) {
        File file;
        try {
            file = new File(out);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("LOG argument must be File format. For example out.txt");
        }
        return file;
    }

    private static Pattern getPattern(String type, String mask) {
        String regex = switch (type) {
            case "mask" -> getRegexFromMask(mask);
            case "name" -> getRegexFromMask(mask);
            case "regex" -> mask;
            default -> throw new IllegalArgumentException(
                    "TYPE argument should be \"name\", \"mask\" or \"regex\""
            );
        };

        Pattern pattern = null;
        try {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            throw new IllegalArgumentException("MASK should be filemask, filename or regex format.");
        }
        return pattern;
    }

    private static String getRegexFromMask(String mask) {
        char[] cMask = mask.toCharArray();
        String regex = "";
        char tmp;
        for (char c : cMask) {
            tmp = c;
            regex = regex + switch (tmp) {
                case '?' -> ".";
                case '*' -> ".*";
                case '.' -> "\\.";
                default -> tmp;
            };
        }
        return regex;
    }

    private static List<Path> getFiles(Pattern pattern, Path path) throws IOException {
        FindVisitor fv = new FindVisitor(pattern);
        Files.walkFileTree(path, fv);
        return fv.getFiles();
    }

    private static void writeLog(List<Path> files, Path log) {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(log.toFile(), Charset.forName("WINDOWS-1251"), true)
        )) {
            files.stream()
                    .map(p -> String.format(
                            "Filename: %s Path: %s", p.toFile().getName(), p.toFile().getAbsolutePath()
                    ))
                    .forEach(pw :: println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
