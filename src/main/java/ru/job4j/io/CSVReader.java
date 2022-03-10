package ru.job4j.io;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.lang.IllegalArgumentException;
import java.nio.charset.Charset;

public class CSVReader {

    public static void main(String[] args) throws Exception {
        CSVReader.handle(ArgsName.of(args));
    }

    public static void handle(ArgsName argsName) throws Exception {
        File path = CSVReader.validatePath(argsName.get("path"));
        String delimiter = argsName.get("delimiter");
        String out = argsName.get("out");
        String[] filters = argsName.get("filter").split(",");

        List<String[]> tableData = readTable(path, delimiter);

        List<String> rslTable = getResultTable(tableData, filters);

        if ("stdout".equals(out)) {
            consoleOut(rslTable);
        } else {
            fileOut(rslTable, out);
        }
    }

    private static void consoleOut(List<String> rslTable) {
        rslTable.forEach(System.out::println);
    }

    private static void fileOut(List<String> rslTable, String out) {
        File file;
        try {
            file = new File(out);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("OUT argument must be File format. For example out.txt");
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, Charset.forName("WINDOWS-1251"), true))) {
            rslTable.forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<String> getResultTable(List<String[]> tableData, String[] filters) {
        String[] title = tableData.get(0);
        List<Integer> indexes = new ArrayList<>();
        for (String filter : filters) {
            for (int j = 0; j < title.length; j++) {
                if (Objects.equals(filter, title[j])) {
                    indexes.add(j);
                    break;
                }
            }
        }

        if (indexes.size() == 0) {
            throw new IllegalArgumentException(
                    "The entered column names do not exist in the source table."
            );
        }

        List<String> rslTable = new LinkedList<>();
        String str;
        for (String[] line : tableData) {
            str = line[indexes.get(0)];
            for (int j = 1; j < indexes.size(); j++) {
                str = str.concat(";").concat(line[indexes.get(j)]);
            }
            rslTable.add(str);
        }
        return rslTable;
    }

    private static List<String[]> readTable(File path, String delimiter) {
        List<String[]> table = new LinkedList<>();
        try (var scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                table.add(scanner.nextLine().split(delimiter));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    private static File validatePath(String path) {
        File file;
        try {
            file = new File(path);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("First argument must be Path format. For example c:/projects");
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("Entered arguments are incorrect. PATH not found.");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("Entered arguments are incorrect. PATH is not a file");
        }
        return file;
    }

}
