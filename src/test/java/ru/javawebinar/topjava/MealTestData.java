package ru.javawebinar.topjava;

import org.junit.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryBaseRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfNextDayOrMax;

public class MealTestData {

    public final static int NOT_FOUND_MEALS = -1;

    public final static LocalDate startDate = LocalDate.of(2020, Month.JANUARY, 25);
    public final static LocalDate endDate = LocalDate.of(2020, Month.JANUARY, 30);

    public static List<Meal> meals = Arrays.asList(
            new Meal(UserTestData.ADMIN_ID+1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(UserTestData.ADMIN_ID+2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(UserTestData.ADMIN_ID+3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(UserTestData.ADMIN_ID+4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(UserTestData.ADMIN_ID+5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(UserTestData.ADMIN_ID+6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(UserTestData.ADMIN_ID+7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    static {
        meals = meals.stream().
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                collect(Collectors.toList());
    }

    public static void AssertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void AssertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }


    public static List<Meal> getBetweenInclusive(LocalDate startDateTime, LocalDate endDateTime) {
        LocalDate nextEndDate = endDateTime.plusDays(1);
        return meals.stream()
                .filter(meal -> Util.isBetweenHalfOpen(meal.getDate(), startDateTime, nextEndDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meals.get(0));
        updated.setDescription("new Description");
        updated.setCalories(1111);
        updated.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return updated;
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2222,2,22,22,22), "new meal", 2222);
    }

}
