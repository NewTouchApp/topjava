package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealToService {

    List<MealTo> getAllMeal();

    void deleteMealById(String id);

    void addNewMeal(LocalDateTime date, String description, int calories);

    MealTo findMealById(String id);

    void updateMeal(MealTo mealTo);
}
