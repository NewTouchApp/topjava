package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepo;
import ru.javawebinar.topjava.repository.MealRepoImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealToServiceImpl implements MealToService {
    private final static int CALORIE_PER_DAY_MAX = 2000;
    MealRepo mealRepository = new MealRepoImpl();
    private static MealToService mealToService;

    private MealToServiceImpl() {
    }

    public static MealToService getInstant() {
        if (mealToService == null) {
            mealToService = new MealToServiceImpl();
        }
        return mealToService;
    }

    @Override
    public List<MealTo> getAllMeal() {
        return MealsUtil.filteredByStreams(mealRepository.getAllMeal(), LocalTime.of(0, 0), LocalTime.of(23, 59), CALORIE_PER_DAY_MAX);
    }

    @Override
    public void deleteMealById(String id) {
        mealRepository.deleteMealById(Integer.parseInt(id));
    }

    @Override
    public void addNewMeal(LocalDateTime date, String description, int calories) {
        mealRepository.addNewMeal(date, description, calories);
    }

    @Override
    public MealTo findMealById(String id) {
        int idInt = Integer.parseInt(id);
        Meal meal = mealRepository.findById(idInt);
        return MealsUtil.createTo(meal,false);
    }

    @Override
    public void updateMeal(MealTo mealTo) {
        mealRepository.updateMeal(mealTo);
    }
}
