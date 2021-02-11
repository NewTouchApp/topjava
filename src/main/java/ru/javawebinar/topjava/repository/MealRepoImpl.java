package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealRepoImpl implements MealRepo {
    private List<Meal> mealss = Arrays.asList(
            new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );
    private CopyOnWriteArrayList<Meal> meals;

    public MealRepoImpl() {
        meals = new CopyOnWriteArrayList<>(mealss);

    }

    @Override
    public List<Meal> getAllMeal() {
        return meals;
    }

    @Override
    public void deleteMealById(int id) {
        meals.removeIf(meal -> meal.getId() == id);
    }

    @Override
    public void addNewMeal(LocalDateTime date, String description, int calories) {
        int maxId = meals.get(meals.size() - 1).getId();
        Meal meal = new Meal(++maxId, date, description, calories);
        meals.add(meal);
    }

    @Override
    public Meal findById(int id) {
        for (Meal meal: meals) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public void updateMeal(MealTo mealTo) {
        Meal meal = findById(mealTo.getId());
        meal.setId(mealTo.getId());
        meal.setDateTime(mealTo.getDateTime());
        meal.setDescription(mealTo.getDescription());
        meal.setCalories(mealTo.getCalories());
    }
}
