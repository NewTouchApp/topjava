package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealToService;
import ru.javawebinar.topjava.service.MealToServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class EditMealServlet extends HttpServlet {
    MealToService serviceMeal = MealToServiceImpl.getInstant();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MealTo mealTo = null;
        System.out.println("i'm here");

        String action = request.getParameter("action");
        if (action != null) {
            mealTo = serviceMeal.findMealById(request.getParameter("id"));
            //response.sendRedirect("editMeal");
            //return;
        }
        request.setAttribute("meal", mealTo);
        request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
    }

}
