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

        User userPut;
        for (User u : current) {
            userPut = map.put(u.getId(), u);
            if (userPut == null) {
                add++;
            } else if (!userPut.equals(u)) {
                change++;
            } else {
                equal++;
            }

        }

        delete = previous.size() - (change + equal);

        return new Info(add, change, delete);
    }

}