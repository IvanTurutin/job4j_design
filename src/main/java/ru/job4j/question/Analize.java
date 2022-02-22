package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int add = 0;
        int change = 0;
        int delete;
        int equal = 0;

        Map<Integer, User> map = previous.stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        for (User u : current) {
            User us = map.put(u.getId(), u);
            if (us == null) {
                add++;
            } else if (!us.equals(u)) {
                change++;
            } else {
                equal++;
            }

        }

        delete = previous.size() - (change + equal);

        return new Info(add, change, delete);
    }

}