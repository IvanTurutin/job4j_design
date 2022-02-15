package ru.job4j.map;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class UserTest {
    public static void main(String[] args) {
        Calendar birthday = new GregorianCalendar(1988, 1, 15);
        String name = "Ivan";
        int children = 21;

        System.out.println(birthday.getTime());
        User user1 = new User(name, children, birthday);
        User user12 = new User(name, children, birthday);
        User user2 = new User(name, children, birthday);
        Map<User, Object> map = new HashMap<>();
        map.put(user1, new Object());
        map.put(user2, new Object());
        map.keySet().stream().forEach(System.out::println);

        System.out.println(user1.hashCode());
        System.out.println(user2.hashCode());

        System.out.println(hash(user1));
        System.out.println(hash(user2));

    }

    static int hash(User key) {
        int h = key.hashCode();
        return (key == null) ? 0 : ((h) ^ (h >>> 16)) & ((1 << 4) - 1);
    }

}
