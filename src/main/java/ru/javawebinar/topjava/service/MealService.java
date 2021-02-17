package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;
    private UserRepository userRepository;

    @Autowired
    public MealService(MealRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), userRepository.get(userId).getCaloriesPerDay());
    }

    public Meal create(Meal meal, int userId) {
        String userName = userRepository.get(userId).getName();
        if (meal.getUserId() != userId) {
            throw new NotFoundException(String.format("User %s don't allow change this meal", userName));
        }
        return repository.save(meal);
    }

    public void delete(int id, int userId) {
        String userName = userRepository.get(userId).getName();
        Meal meal = checkNotFoundWithId(repository.get(id), id);
        checkNotFoundWithId(meal, id);
        if (meal.getUserId() != userId) {
            throw new NotFoundException(String.format("User %s don't allow change this meal", userName));
        }
        repository.delete(id);
    }

    public Meal get(int id, int userId) {
        String userName = userRepository.get(userId).getName();
        Meal meal = checkNotFoundWithId(repository.get(id), id);
        checkNotFoundWithId(meal, id);
        if (meal.getUserId() != userId) {
            throw new NotFoundException(String.format("User %s don't allow change this meal", userName));
        }
        return meal;
    }

    public void update(Meal meal, int userId) {
        String userName = userRepository.get(userId).getName();
        if (meal.getUserId() != userId) {
            throw new NotFoundException(String.format("User %s don't allow change this meal", userName));
        }
        //checkNotFoundWithId(repository.save(meal), meal.getId());
        repository.save(meal);
    }

}