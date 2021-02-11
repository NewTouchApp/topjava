package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepo {
    List<Meal> getAllMeal();

    void deleteMealById(int id);

    void addNewMeal(LocalDateTime date, String description, int calories);

    Meal findById(int idInt);

    void updateMeal(MealTo mealTo);
}
