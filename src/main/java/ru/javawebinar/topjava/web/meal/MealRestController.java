package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("get all");
        return service.getAll(SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

    public List<MealTo> getAllWithFiltered(Map<String, String[]> parameterMap) {
        log.info("get all with filtered");
        List<MealTo> meals = service.getAll(SecurityUtil.authUserId());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String paramForFiltered;
        if (parameterMap.containsKey("dateFrom") && !parameterMap.get("dateFrom")[0].isEmpty()) {
            paramForFiltered = parameterMap.get("dateFrom")[0];
            LocalDate date = LocalDate.parse(paramForFiltered, dateFormatter);
            meals = meals.stream().filter(meal -> meal.getDate().compareTo(date) >= 0).collect(Collectors.toList());
        }
        if (parameterMap.containsKey("dateTo") && !parameterMap.get("dateTo")[0].isEmpty()) {
            paramForFiltered = parameterMap.get("dateTo")[0];
            LocalDate date = LocalDate.parse(paramForFiltered, dateFormatter);
            meals = meals.stream().filter(meal -> meal.getDate().compareTo(date) <= 0).collect(Collectors.toList());
        }
        if (parameterMap.containsKey("timeFrom") && !parameterMap.get("timeFrom")[0].isEmpty()) {
            paramForFiltered = parameterMap.get("timeFrom")[0];
            LocalTime time = LocalTime.parse(paramForFiltered, timeFormatter);
            meals = meals.stream().filter(meal -> meal.getTime().compareTo(time) >= 0).collect(Collectors.toList());
        }
        if (parameterMap.containsKey("timeTo") && !parameterMap.get("timeTo")[0].isEmpty()) {
            paramForFiltered = parameterMap.get("timeTo")[0];
            LocalTime time = LocalTime.parse(paramForFiltered, timeFormatter);
            meals = meals.stream().filter(meal -> meal.getTime().compareTo(time) <= 0).collect(Collectors.toList());
        }
        return meals;
    }
}