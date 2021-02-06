package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        //List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExcess> mealsTo = filteredByStreamsOptional2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSum = new HashMap<>();
        for (UserMeal userMeal : meals) {
            caloriesSum.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcesses.add(createFromUserMealWithExceed(userMeal, new AtomicBoolean(caloriesSum.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSum = meals.stream().
                collect(Collectors.groupingBy(s -> s.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream().
                filter(s -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime)).
                map(s -> createFromUserMealWithExceed(s, new AtomicBoolean(caloriesSum.get(s.getDateTime().toLocalDate()) > caloriesPerDay))).
                collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByStreamsOptional2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate, Integer> caloriesSumByDay = new HashMap<>();
        final Map<LocalDate, AtomicBoolean> excessByDay = new HashMap<>();

        final List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();

        meals.forEach(
                meal -> {
                    LocalDate currentDay = meal.getDateTime().toLocalDate();

                    AtomicBoolean wrapExcess = excessByDay.computeIfAbsent(currentDay, value -> new AtomicBoolean());
                    Integer sumCalories =  caloriesSumByDay.merge(currentDay, meal.getCalories(), Integer::sum);

                    if (sumCalories > caloriesPerDay) {
                        wrapExcess.set(true);
                    }

                    if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                        userMealWithExcesses.add(createFromUserMealWithExceed(meal, wrapExcess));
                    }
                }
        );

        return userMealWithExcesses;

    }

    public static UserMealWithExcess createFromUserMealWithExceed(UserMeal um, AtomicBoolean exceed) {
        return new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(), exceed);
    }
}
