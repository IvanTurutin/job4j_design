package ru.job4j.io.findfiles;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.job4j.io.ArgsName;

import java.io.*;
import java.util.List;

public class FindFilesTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void whenFindByMask() throws IOException {
        File file = folder.newFile("fileToFind.txt");
        File target = folder.newFile("findFile.txt");
        /*args: -d=tempFolder -n=*tofin*.?xt -t=mask -o=findFile.txt*/
        String[] input = new String[] {
                "-d=" + folder.getRoot().toString(),
                "-n=*tofin*.?xt",
                "-t=mask",
                "-o=" + target.getAbsolutePath()
        };
        ArgsName args = ArgsName.of(input);
        FindFiles.run(args);

        String rsl;
        try (BufferedReader in = new BufferedReader(new FileReader(target.getAbsolutePath()))) {
            rsl = in.readLine().split(" ")[1];
        }
        assertThat(rsl, is(file.getName()));
    }

    @Test
    public void whenFindByFileName() throws IOException {
        File file = folder.newFile("fileToFind.txt");
        File target = folder.newFile("findFile.txt");
        /*args: -d=tempFolder -n=fileToFind.txt -t=name -o=findFile.txt*/
        String[] input = new String[] {
                "-d=" + folder.getRoot().toString(),
                "-n=" + file.getName(),
                "-t=name",
                "-o=" + target.getAbsolutePath()
        };
        ArgsName args = ArgsName.of(input);
        FindFiles.run(args);

        String rsl;
        try (BufferedReader in = new BufferedReader(new FileReader(target.getAbsolutePath()))) {
            rsl = in.readLine().split(" ")[1];
        }
        assertThat(rsl, is(file.getName()));
    }

    @Test
    public void whenFindByRegex() throws IOException {
        File file = folder.newFile("fileToFind.txt");
        File target = folder.newFile("findFile.txt");
        /*args: -d=tempFolder -n=[a-zA-Z]+find\.txt -t=regex -o=findFile.txt*/
        String[] input = new String[] {
                "-d=" + folder.getRoot().toString(),
                "-n=[a-zA-Z]+find\\.txt",
                "-t=regex",
                "-o=" + target.getAbsolutePath()
        };
        ArgsName args = ArgsName.of(input);
        FindFiles.run(args);

        String rsl;
        try (BufferedReader in = new BufferedReader(new FileReader(target.getAbsolutePath()))) {
            rsl = in.readLine().split(" ")[1];
        }
        assertThat(rsl, is(file.getName()));
    }
}