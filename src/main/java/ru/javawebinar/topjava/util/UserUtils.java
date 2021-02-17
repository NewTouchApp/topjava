package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class UserUtils {

    public static final List<User> users = Arrays.asList(
        new User(null, "USER1", "USER1@MAIL.RU", "password", Role.USER, Role.ADMIN),
        new User(null, "USER2", "USER2@MAIL.RU", "password", Role.USER),
        new User(null, "USER3", "USER3@MAIL.RU", "password", Role.USER)
    );

    public static Comparator<User> comparator = (u1, u2) -> {
        int resultComparing = u1.getName().compareTo(u2.getName());
        if (resultComparing == -1) return 1;
        else if (resultComparing == 1) return -1;
        else return 0;
    };

    {
        comparator = comparator.thenComparing(user -> user.getEmail());
    }
}
