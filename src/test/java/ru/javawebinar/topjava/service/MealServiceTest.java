package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Autowired
    private MealRepository mealRepository;

    @Test
    public void get() {
        Meal mealExcepted = MealTestData.meals.get(0);
        Meal mealActual = mealService.get(mealExcepted.getId(), UserTestData.USER_ID);
        MealTestData.AssertMatch(mealActual, mealExcepted);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class,
                () -> mealService.get(MealTestData.NOT_FOUND_MEALS, UserTestData.USER_ID));
    }

    @Test
    public void getNotFoundWithOtherUser() {
        Meal mealExcepted = MealTestData.meals.get(0);
        assertThrows(NotFoundException.class,
                () -> mealService.get(mealExcepted.getId(), UserTestData.ADMIN_ID));
    }

    @Test
    public void delete() {
        Meal mealExcepted = MealTestData.meals.get(0);
        mealService.delete(mealExcepted.getId(), UserTestData.USER_ID);
        assertThrows(NotFoundException.class,
                () -> mealService.get(mealExcepted.getId(), UserTestData.USER_ID));
    }

    @Test
    public void deleteNotFoundMeal() {
        assertThrows(NotFoundException.class,
                () -> mealService.delete(MealTestData.NOT_FOUND_MEALS, UserTestData.USER_ID));
    }

    @Test
    public void deleteNotFoundMealWithUser() {
        assertThrows(NotFoundException.class,
                () -> mealService.delete( MealTestData.meals.get(0).getId(), UserTestData.ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> mealsActual =
                mealService.getBetweenInclusive(MealTestData.startDate, MealTestData.endDate, UserTestData.USER_ID);
        MealTestData.AssertMatch(mealsActual, MealTestData.getBetweenInclusive(MealTestData.startDate, MealTestData.endDate));
    }

    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(UserTestData.USER_ID);
        //assertThat(meals).isEqualTo(MealTestData.meals);
        MealTestData.AssertMatch(meals, MealTestData.meals);
    }

    @Test
    public void update() {
        Meal mealUpdate = MealTestData.getUpdated();
        mealService.update(mealUpdate, UserTestData.USER_ID);
        MealTestData.AssertMatch(mealService.get(mealUpdate.getId(), UserTestData.USER_ID), mealUpdate);
    }

    @Test
    public void updateForeignMeal() {
        Meal mealUpdate = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> mealService.update(mealUpdate, UserTestData.ADMIN_ID));
    }

    @Test
    public void create() {
        Meal exceptedMeal = MealTestData.getNew();
        Meal created = mealService.create(exceptedMeal, UserTestData.USER_ID);
        int id = created.getId();
        exceptedMeal.setId(id);
        MealTestData.AssertMatch(created, exceptedMeal);
    }
}