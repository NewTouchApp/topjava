package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealToService;
import ru.javawebinar.topjava.service.MealToServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    MealToService serviceMeal = MealToServiceImpl.getInstant();

    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meal");
        String action = request.getParameter("action");
        if (action != null) {
            if (action.contains("delete")) {
                serviceMeal.deleteMealById(request.getParameter("id"));
                response.sendRedirect("meals");
                return;
            }
            else if (action.contains("add")) {
                LocalDateTime date = LocalDateTime.parse(request.getParameter("datetime"));
                String description = request.getParameter("description");
                int calories = Integer.parseInt(request.getParameter("calories"));
                serviceMeal.addNewMeal(date,description,calories);
                response.sendRedirect("meals");
                return;
            }
            else if (action.contains("update")) {
                LocalDateTime date = LocalDateTime.parse(request.getParameter("datetime"));
                String description = request.getParameter("description");
                int calories = Integer.parseInt(request.getParameter("calories"));
                int id = Integer.parseInt(request.getParameter("id"));
                MealTo mealTo = new MealTo(id, date, description, calories, false);

                serviceMeal.updateMeal(mealTo);
                response.sendRedirect("meals");

                return;
            }
        }
        request.setAttribute("meals", serviceMeal.getAllMeal());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
